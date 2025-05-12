import { AbstractEntity, Role } from '@app/common';
import { Column, Entity } from 'typeorm';

@Entity()
export class User extends AbstractEntity<User> {
  @Column({ nullable: false })
  name: string;

  @Column({ unique: true, nullable: false })
  email: string;

  @Column()
  telephoneNo: string;

  @Column()
  password: string;

  @Column({ type: 'enum', enum: Role, enumName: 'user_role_enum' })
  role: Role;

  constructor(user: Partial<User>) {
    super(user);
  }
}
