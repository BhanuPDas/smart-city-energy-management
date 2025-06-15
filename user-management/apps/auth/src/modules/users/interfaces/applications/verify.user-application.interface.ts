import { UserDomain } from '../../domain/entities/User';

export interface IVerifyUserApplication {
  verifyUser(email: string, password: string): Promise<UserDomain>;
}
