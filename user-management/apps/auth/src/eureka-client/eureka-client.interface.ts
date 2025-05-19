export interface EurekaClientOptions {
  instance: {
    app: string;
    hostName: string;
    ipAddr: string;
    port?: {
      $: number;
      '@enabled': boolean;
    };
    vipAddress: string;
    dataCenterInfo: {
      name: 'MyOwn' | 'Amazon';
      '@class'?: string;
      metadata?: Record<string, any>;
    };
    instanceId?: string | undefined;
  };
  eureka: {
    host: string;
    port: number;
    servicePath?: string;
    fetchRegistry?: boolean | undefined;
    registryFetchInterval?: number | undefined;
    maxRetries?: number | undefined;
    requestRetryDelay?: number | undefined;
    heartbeatInterval?: number | undefined;
  };
}
