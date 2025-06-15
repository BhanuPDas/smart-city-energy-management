import { AbstractEntity } from '@app/infrastructure';
import { Email } from '../value-objects/email';

export class UserDomain extends AbstractEntity<UserDomain> {
  public readonly name: string;
  public readonly email: Email;
  public readonly phone?: string;
  public readonly role: string;
  public readonly password: string;

  constructor(input: Partial<UserDomain>) {
    super(input);
    if (!input.name) throw new Error('User name is required');
    if (!input.email) throw new Error('User email is required');
    if (!input.role) throw new Error('User role is required');
    if (!input.password) throw new Error('User password is required');

    this.name = input.name;
    this.email =
      input.email instanceof Email ? input.email : new Email(input.email);
    this.phone = input.phone;
    this.role = input.role;
    this.password = input.password;
  }
}
