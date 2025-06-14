import { Injectable, Logger, OnModuleInit } from '@nestjs/common';
import { Eureka, EurekaClient } from 'eureka-js-client';

@Injectable()
export class EurekaClientService {
  private client: Eureka;
  private readonly logger = new Logger(EurekaClientService.name);

  constructor(private readonly options: EurekaClient.EurekaConfig) {
    options.shouldUseDelta = false;
    options.logger = {
      info: this.logger.log.bind(this.logger),
      warn: this.logger.warn.bind(this.logger),
      error: this.logger.error.bind(this.logger),
      debug: this.logger.debug.bind(this.logger),
    };

    this.client = new Eureka(options);
    this.startClient();
  }

  // onModuleInit() {
  //this.startClient();
  // }

  private startClient() {
    this.client.start((error) => {
      this.logger.log('Attempting to register with Eureka Server...');

      if (!error) {
        this.logger.log('Successfully registered with Eureka Server!');
      }

      if (error) {
        switch (error.name) {
          case 'AggregateError':
            if ((error as any)?.code === 'ECONNREFUSED') {
              this.logger.error(
                '[ECONNREFUSED]: connection refused by Eureka server',
              );
            } else {
              this.logger.error({ AggregateError: error });
            }
            break;

          default:
            this.logger.error(`${error.name}: ${error.message}`);
            break;
        }
      }
    });
  }

  getClient(): Eureka {
    return this.client;
  }
}
