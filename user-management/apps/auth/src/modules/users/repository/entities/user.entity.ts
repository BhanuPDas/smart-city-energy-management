import { AbstractEntity } from '@app/infrastructure';
import { Column, Entity, Unique } from 'typeorm';

@Entity({ name: 'users' })
@Unique(['email'])
export class User extends AbstractEntity<User> {
  @Column({ nullable: false, length: 20 })
  name: string;

  @Column({ unique: true, nullable: false, length: 35 })
  email: string;

  @Column({ nullable: true, length: 15 })
  phone: string;

  @Column({ nullable: false, length: 14 })
  role: string;

  @Column({ nullable: false, length: 150 })
  password: string;

  constructor(user: Partial<User>) {
    super(user);
  }
}
