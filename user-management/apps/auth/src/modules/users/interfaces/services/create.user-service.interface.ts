import { CreateUserDTO } from '../../applications/DTO/create-user.dto';
import { UserDomain } from '../../domain/entities/User';

export interface ICreateUserService {
  create(user: CreateUserDTO): Promise<UserDomain>;
}
