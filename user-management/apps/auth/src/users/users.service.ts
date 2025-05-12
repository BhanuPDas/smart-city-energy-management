import {
  ConflictException,
  Injectable,
  InternalServerErrorException,
} from '@nestjs/common';
import { UsersRepository } from './users.repository';
import { User } from './entities/user.entity';
import { CreateUserDTO } from './DTO/create-user.dto';
import { Long, QueryFailedError } from 'typeorm';
import { UpdateUserDTO } from './DTO/update-user.dto';
import * as bcrypt from 'bcryptjs';

@Injectable()
export class UsersService {
  constructor(private readonly userRepository: UsersRepository) {}

    async hashPassword(password: string): Promise<string> {
    const hashedPassword = await bcrypt.hash(password, 10);
    return hashedPassword;
  }

  async createUser(createUserDto: CreateUserDTO): Promise<User> {
    try {
      return await this.userRepository.create({
        ...createUserDto,
        password: await this.hashPassword(createUserDto.password),
      });
    } catch (err) {
      if (err instanceof QueryFailedError && (err as any).code === '23505') {
        throw new ConflictException('Email is already in use');
      }
      throw new InternalServerErrorException();
    }
  }

  async findAll(): Promise<User[]> {
    return await this.userRepository.find({});
  }

  async findOne(email: string): Promise<User> {
    return await this.userRepository.findOne({ email });
  }

  async findOneById(userId: Long): Promise<User> {
    return await this.userRepository.findOne({ userId });
  }

  async findOneAndUpdate(
    userId: Long,
    updateUserDto: UpdateUserDTO,
  ): Promise<User> {
    return await this.userRepository.findOneAndUpdate({userId}, updateUserDto);
  }

  async findOneAndDelete(userId: Long): Promise<string> {
    return await this.userRepository.findOneAndDelete({ userId });
  }
}
