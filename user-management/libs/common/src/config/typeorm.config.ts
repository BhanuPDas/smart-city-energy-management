import { ConfigService } from '@nestjs/config';
import { TypeOrmModuleOptions } from '@nestjs/typeorm';
import { join } from 'path';
import { Client } from 'pg';

export const typeOrmConfig = async (
  configService: ConfigService,
): Promise<TypeOrmModuleOptions> => {
  const dbConfig = configService.getOrThrow<{
    host: string;
    port: number;
    username: string;
    password: string;
    database: string;
    schema: string;
  }>('database');

  const pgClient = new Client({
    host: dbConfig.host,
    port: dbConfig.port,
    user: dbConfig.username,
    password: dbConfig.password,
    database: dbConfig.database,
  });
  await pgClient.connect();
  await pgClient.query(`CREATE SCHEMA IF NOT EXISTS "${dbConfig.schema}"`);
  await pgClient.end();

  return {
    type: 'postgres',
    host: dbConfig.host,
    port: dbConfig.port,
    username: dbConfig.username,
    password: dbConfig.password,
    database: dbConfig.database,
    schema: dbConfig.schema,
    synchronize: true,
    autoLoadEntities: true,
    migrationsRun: true,
    migrations: [join(__dirname, '../migrations/*{.js,.ts}')],
  };
};
