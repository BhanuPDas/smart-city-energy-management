import { Long } from 'typeorm';
import { User } from '../repository/entities/user.entity';
import { IGetUserServiceInterface } from '../interfaces/services/get.user-service.interface';
import { UsersRepository } from '../repository/users.repository';
import { Injectable } from '@nestjs/common';
import { UserDomain } from '../domain/entities/User';
import { Mapper } from '../applications/mappers/mapper';

@Injectable()
export class GetUserService implements IGetUserServiceInterface {
  constructor(private readonly userRepository: UsersRepository) {}

  async findAll(): Promise<UserDomain[]> {
    const user: User[] = await this.userRepository.find({});
    return Mapper.arrayToUserDomain(user);
  }

  async findOne(email: string): Promise<UserDomain> {
    const user: User = await this.userRepository.findOne({ email });
    return Mapper.toUserDomain(user);
  }

  async findOneById(userId: Long): Promise<UserDomain> {
    const user: User = await this.userRepository.findOne({ userId });
    return Mapper.toUserDomain(user);
  }
}
