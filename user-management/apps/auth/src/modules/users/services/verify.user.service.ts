import { Injectable, UnauthorizedException } from '@nestjs/common';
import { IVerifyUserService } from '../interfaces/services/verify.user-service.interface';
import { User } from '../repository/entities/user.entity';
import { UsersRepository } from '../repository/users.repository';
import * as bcrypt from 'bcryptjs';
import { UserDomain } from '../domain/entities/User';
import { Mapper } from '../applications/mappers/mapper';

@Injectable()
export class VerifyUserService implements IVerifyUserService {
  constructor(private readonly userRepository: UsersRepository) {}

  async verifyUser(email: string, password: string): Promise<UserDomain> {
    const user: User = await this.userRepository.findOne({ email });

    const passwordIsValid = await bcrypt.compare(password, user.password);
    if (!passwordIsValid) {
      throw new UnauthorizedException('Invalid credentials');
    }

    return Mapper.toUserDomain(user);
  }
}
