import { Inject, Injectable } from '@nestjs/common';
import { ICreateUserApplication } from '../interfaces/applications/create.user-application.interface';
import { CreateUserDTO } from './DTO/create-user.dto';
import { Token } from '../../../injection-tokens';
import { ICreateUserService } from '../interfaces/services/create.user-service.interface';
import { UserDomain } from '../domain/entities/User';

@Injectable()
export class CreateUserApplication implements ICreateUserApplication {
  constructor(
    @Inject(Token.SERVICES.CREATE_USER)
    private readonly createUserService: ICreateUserService,
  ) {}

  async create(user: CreateUserDTO): Promise<UserDomain> {
    return await this.createUserService.create(user);
  }
}
