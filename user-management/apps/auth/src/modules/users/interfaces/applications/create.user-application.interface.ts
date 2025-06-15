import { CreateUserDTO } from '../../applications/DTO/create-user.dto';
import { UserDomain } from '../../domain/entities/User';

export interface ICreateUserApplication {
  create(user: CreateUserDTO): Promise<UserDomain>;
}
