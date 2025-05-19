import { Module } from '@nestjs/common';
import { ConfigModule as NestConfigModule } from '@nestjs/config';
import * as path from 'path';
import databaseConfig from './database.config';
import { validationSchema } from './validation.schema';

@Module({
  imports: [
    NestConfigModule.forRoot({
      envFilePath: [path.resolve(process.cwd(), '.env')],
      isGlobal: true,
      ignoreEnvFile: false,
      load: [databaseConfig],
      validationSchema: validationSchema,
    }),
  ],
})
export class ConfigModule {}
