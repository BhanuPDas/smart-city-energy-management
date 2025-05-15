import { NestFactory } from '@nestjs/core';
import { AuthModule } from './auth.module';
import { ValidationPipe, VersioningType } from '@nestjs/common';
import { Logger } from 'nestjs-pino';
import * as cookieParser from 'cookie-parser';
import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { ConfigService } from '@nestjs/config';
import { setupSwagger } from './swagger.config';

async function bootstrap() {
  const app = await NestFactory.create(AuthModule, {
    bufferLogs: true,
  });
  const conifgService = app.get(ConfigService);

  app.connectMicroservice<MicroserviceOptions>({
    transport: Transport.TCP,
    options: {
      port: conifgService.getOrThrow('TCP_PORT'),
      host: 'localhost',
    },
  });
  app.useGlobalPipes(new ValidationPipe({ whitelist: true, transform: true }));

  app.useLogger(app.get(Logger));
  app.use(cookieParser());

  app.enableVersioning({
    type: VersioningType.URI,
  });

  setupSwagger(app);

  await app.startAllMicroservices();

  await app.listen(conifgService.getOrThrow('HTTP_PORT'));
}
bootstrap();
