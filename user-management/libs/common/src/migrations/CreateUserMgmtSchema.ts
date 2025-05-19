import { MigrationInterface, QueryRunner } from 'typeorm';

export class CreateUserMgmtSchema0001 implements MigrationInterface {
  public async up(q: QueryRunner): Promise<void> {
    await q.query(`CREATE SCHEMA IF NOT EXISTS "user_mgmt"`);
  }
  public async down(q: QueryRunner): Promise<void> {
    await q.query(`DROP SCHEMA IF EXISTS "user_mgmt" CASCADE`);
  }
}
