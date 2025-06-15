import { Module } from '@nestjs/common';
import { AuthController } from './modules/auth/auth.controller';
import { AuthService } from './modules/auth/auth.service';
import { JwtModule } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { LoggerModule } from 'nestjs-pino';
import { ClientsModule, Transport } from '@nestjs/microservices';
import { SERVICE } from 'infrastructure/src/constants/services';
import { HttpModule } from '@nestjs/axios';
import { PrometheusModule } from '@willsoto/nestjs-prometheus';
import { EurekaClientModule } from './eureka-client/eureka-client.module';
import { DatabaseModule } from 'infrastructure/src';
import { AuthModule } from './modules/auth/auth.module';

@Module({
  imports: [
    AuthModule,
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

    LoggerModule.forRootAsync({
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => ({
        pinoHttp: {
          transport: {
            targets: [
              // send logs to Loki
              {
                target: 'pino-loki',
                options: {
                  host: configService.getOrThrow<string>('LOKI_URL'),
                  labels: { app: 'auth-service', env: 'development' },
                  batching: true,
                  interval: 5,
                },
              },
              // keeping pretty-printing for local development
              {
                target: 'pino-pretty',
                options: { singleLine: true, autoLogging: false },
              },
            ],
          },
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
          instanceId: configService.getOrThrow<string>('SERVICE_NAME'),
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
  ],
  controllers: [AuthController],
})
export class AppModule {}
