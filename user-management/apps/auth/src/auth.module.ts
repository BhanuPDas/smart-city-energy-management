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
import { EurekaClientModule } from './eureka-client/eureka-client.module';

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

    EurekaClientModule.forRootAsync({
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => ({
        instance: {
          app: configService.getOrThrow<string>('SERVICE_NAME'),
          hostName: configService.getOrThrow<string>('SERVICE_HOST'),
          instanceId:configService.getOrThrow<string>('SERVICE_NAME'),
          ipAddr: configService.getOrThrow<string>('SERVICE_ipAddr'),
          port: {
            $: Number(configService.getOrThrow<number>('SERVICE_PORT')),
            '@enabled': true,
          },
          vipAddress: configService.getOrThrow<string>('SERVICE_NAME'),
          dataCenterInfo: {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
          },
        },
        eureka: {
          host: configService.getOrThrow<string>('EUREKA_SERVER_HOST'),
          port: configService.getOrThrow<number>('EUREKA_SERVER_PORT'),
          fetchRegistry: true,
          registryFetchInterval: 10000,
          maxRetries: 5,
          requestRetryDelay: 10000,
          heartbeatInterval: 1000,
          servicePath: '/eureka/apps/',
        },
      }),
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
