import { Long } from 'typeorm';
import { IGetUserApplication } from '../interfaces/applications/get.user-appication.interface';
import { Inject, Injectable } from '@nestjs/common';
import { Token } from '../../../injection-tokens';
import { IGetUserServiceInterface } from '../interfaces/services/get.user-service.interface';
import { UserDomain } from '../domain/entities/User';

@Injectable()
export class GetUserApplication implements IGetUserApplication {
  constructor(
    @Inject(Token.SERVICES.GET_USER)
    private readonly getUserService: IGetUserServiceInterface,
  ) {}

  async findAll(): Promise<UserDomain[]> {
    return this.getUserService.findAll();
  }
  async findOne(email: string): Promise<UserDomain> {
    return this.getUserService.findOne(email);
  }
  async findOneById(id: Long): Promise<UserDomain> {
    return this.getUserService.findOneById(id);
  }
}
