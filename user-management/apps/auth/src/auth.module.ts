import { Module } from '@nestjs/common';
import { AuthController } from './auth.controller';
import { AuthService } from './auth.service';
import { UsersModule } from './users/users.module';
import { JwtModule } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { LoggerModule } from 'nestjs-pino';
import { APP_GUARD, APP_INTERCEPTOR } from '@nestjs/core';
import { JwtAuthGuard, RolesGuard } from '@app/common/auth';
import { ClientsModule, Transport } from '@nestjs/microservices';
import { SERVICE } from '@app/common/constants/services';
import { HttpModule } from '@nestjs/axios';
import { PrometheusModule } from '@willsoto/nestjs-prometheus';
import { LoggingInterceptor } from './Logging/logging.interceptor';
import { EurekaModule } from 'ms-nestjs-eureka';

@Module({
  imports: [
    UsersModule,
    JwtModule.registerAsync({
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => ({
        secret: configService.get<string>('JWT_SECRET'),
        signOptions: {
          expiresIn: Number(configService.get<number>('JWT_EXPIRATION')),
        },
      }),
    }),

    LoggerModule.forRoot({
      pinoHttp: {
        transport: {
          target: 'pino-pretty',
          options: {
            singleLine: true,
            autoLogging: false,
          },
        },
      },
    }),
    ClientsModule.register([
      {
        name: SERVICE.AUTH_SERVICE,
        transport: Transport.TCP,
        options: { host: 'localhost', port: 8001 },
      },
    ]),

    HttpModule.register({
      timeout: 5000,
      maxRedirects: 5,
    }),

    PrometheusModule.register({
      path: '/metrics',
      defaultMetrics: {
        enabled: true,
      },
    }),

    EurekaModule.forRoot({
      eureka: {
        host: 'localhost',
        port: 8761,
        registryFetchInterval: 1000,
        servicePath: '/eureka/apps/',
        maxRetries: 3,
        //debug: true
      },
      service: {
        name: 'user_management',
        port: 4000,
      },
    }),
  ],
  providers: [
    AuthService,
    {
      provide: APP_GUARD,
      useClass: JwtAuthGuard, // globally enforce JWT
    },
    {
      provide: APP_GUARD,
      useClass: RolesGuard, // globally enforce roles when @Roles() used
    },
    {
      provide: APP_INTERCEPTOR,
      useClass: LoggingInterceptor,
    },
  ],
  controllers: [AuthController],
})
export class AuthModule {}
