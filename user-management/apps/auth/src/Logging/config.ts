export interface GrafanaConfig {
  url: string; // full write‐endpoint, e.g. 'https://my-grafana.com/loki/api/v1/push'
  userId: string; // Grafana user or service account ID
  api: string; // Grafana API key (at least “Loki Push” permission)
}

export const Config = {
  grafana: {
    url: 'http://loki:3100/loki/api/v1/push',
    userId: 'username',
    api: 'password',
  } as GrafanaConfig,
}; 