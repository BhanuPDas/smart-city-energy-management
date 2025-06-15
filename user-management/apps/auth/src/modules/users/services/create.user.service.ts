import {
  ConflictException,
  Injectable,
  InternalServerErrorException,
} from '@nestjs/common';
import { ICreateUserService } from '../interfaces/services/create.user-service.interface';
import { CreateUserDTO } from '../applications/DTO/create-user.dto';
import { UsersRepository } from '../repository/users.repository';
import { QueryFailedError } from 'typeorm';
import * as bcrypt from 'bcryptjs';
import { UserDomain } from '../domain/entities/User';
import { Mapper } from '../applications/mappers/mapper';
import { User } from '../repository/entities/user.entity';

@Injectable()
export class CreateUserService implements ICreateUserService {
  constructor(private readonly userRepository: UsersRepository) {}

  async hashPassword(password: string): Promise<string> {
    const hashedPassword = await bcrypt.hash(password, 10);
    return hashedPassword;
  }

  async create(createUserDto: CreateUserDTO): Promise<UserDomain> {
    try {
      const newUser: User = await this.userRepository.create({
        ...createUserDto,
        password: await this.hashPassword(createUserDto.password),
      });

      return Mapper.toUserDomain(newUser);
    } catch (err) {
      if (err instanceof QueryFailedError && (err as any).code === '23505') {
        throw new ConflictException('Email is already in use');
      }
      throw new InternalServerErrorException(err);
    }
  }
}
