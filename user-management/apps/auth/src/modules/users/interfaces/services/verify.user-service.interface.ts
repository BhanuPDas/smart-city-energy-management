import { UserDomain } from "../../domain/entities/User";
import { User } from "../../repository/entities/user.entity";

export interface IVerifyUserService {
  verifyUser(email: string, password: string): Promise<UserDomain>;
}
