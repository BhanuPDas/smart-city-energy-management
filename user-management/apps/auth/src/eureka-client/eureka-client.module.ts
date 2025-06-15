import { DynamicModule, Module, Provider } from '@nestjs/common';
import { EurekaClientService } from './eureka-client.service';
import { EurekaClientOptions } from './eureka-client.interface';
import { Token } from '../injection-tokens';

@Module({
  providers: [EurekaClientService],
})
export class EurekaClientModule {
  static forRoot(options: EurekaClientOptions): DynamicModule {
    return {
      module: EurekaClientModule,
      providers: [
        {
          provide: Token.EUREKA_CLIENT_OPTIONS,
          useValue: options,
        },
        {
          provide: Token.EUREKA_CLIENT_OPTIONS,
          useFactory: (opts: EurekaClientOptions) =>
            new EurekaClientService(opts),
          inject: [Token.EUREKA_CLIENT_OPTIONS],
        },
      ],
      exports: [EurekaClientService],
    };
  }

  static forRootAsync(options: {
    useFactory: (
      ...args: any[]
    ) => Promise<EurekaClientOptions> | EurekaClientOptions;
    inject?: any[];
  }): DynamicModule {
    const asyncProviders: Provider[] = [
      {
        provide: Token.EUREKA_CLIENT_OPTIONS,
        useFactory: options.useFactory,
        inject: options.inject,
      },

      {
        provide: EurekaClientService,
        useFactory: (opts: EurekaClientOptions) =>
          new EurekaClientService(opts),
        inject: [Token.EUREKA_CLIENT_OPTIONS],
      },
    ];

    return {
      module: EurekaClientModule,
      providers: [...asyncProviders],
      exports: [EurekaClientService],
    };
  }
}
