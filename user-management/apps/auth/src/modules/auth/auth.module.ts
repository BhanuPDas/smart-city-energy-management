import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { ClientsModule, Transport } from '@nestjs/microservices';
import { SERVICE } from 'infrastructure/src/constants/services';
import { DatabaseModule } from '@app/infrastructure';
import { UsersModule } from '../users/users.module';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';

@Module({
  imports: [
    UsersModule,
    DatabaseModule,
    JwtModule.registerAsync({
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => ({
        secret: configService.get<string>('JWT_SECRET'),
        signOptions: {
          expiresIn: Number(configService.get<number>('JWT_EXPIRATION')),
        },
      }),
    }),

    ClientsModule.register([
      {
        name: SERVICE.AUTH_SERVICE,
        transport: Transport.TCP,
        options: { host: 'localhost', port: 8001 },
      },
    ]),
  ],
  providers: [AuthService],
  controllers: [AuthController],
})
export class AuthModule {}
