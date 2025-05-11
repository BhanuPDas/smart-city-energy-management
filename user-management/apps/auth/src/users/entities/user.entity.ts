import { AbstractEntity, Role } from '@app/common';
import { Column, Entity } from 'typeorm';

@Entity()
export class User extends AbstractEntity<User> {
  @Column()
  name: string;

  @Column()
  email: string;

  @Column()
  telephoneNo: string;

  @Column()
  password: string;

  @Column({ type: 'enum' })
  role: Role;
}
