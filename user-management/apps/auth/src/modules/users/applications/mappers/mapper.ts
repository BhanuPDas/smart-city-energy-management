import { UserDomain } from '../../domain/entities/User';
import { Email } from '../../domain/value-objects/email';
import { User } from '../../repository/entities/user.entity';

export class Mapper {
  public static toUserDomain(user: User): UserDomain {
    const { email: rawEmail, ...rest } = user;

    const domainProps = {
      ...rest,
      email: new Email(rawEmail),
    };

    return new UserDomain(domainProps);
  }

  public static arrayToUserDomain(users: User[]): UserDomain[] {
    return users.map((user) => {
      const { email: rawEmail, ...rest } = user;

      const domainProps = {
        ...rest,
        email: new Email(rawEmail),
      };

      return new UserDomain(domainProps);
    });
  }
}
