import { Module } from '@nestjs/common';
import { UsersController } from './controllers/users.controller';
// import { UsersService } from './users.service';
import { DatabaseModule } from 'infrastructure/src';
import { User } from './repository/entities/user.entity';
import { UsersRepository } from './repository/users.repository';
import { JwtStrategy } from '../auth/strategies/jwt.strategy';
import { LocalStrategy } from '../auth/strategies/local.strategy';
import { Token } from '../../injection-tokens';
import { CreateUserApplication } from './applications/create.user-application';
import { CreateUserService } from './services/create.user.service';
import { GetUserApplication } from './applications/get.user-application';
import { GetUserService } from './services/get.user.service';
import { VerifyUserApplication } from './applications/verify.user-application';
import { VerifyUserService } from './services/verify.user.service';

const createUserApp = {
  provide: Token.APPLICATIONS.CREATE_USER,
  useClass: CreateUserApplication,
};

const getUserApp = {
  provide: Token.APPLICATIONS.GET_USER,
  useClass: GetUserApplication,
};

const createUserService = {
  provide: Token.SERVICES.CREATE_USER,
  useClass: CreateUserService,
};

const getUserService = {
  provide: Token.SERVICES.GET_USER,
  useClass: GetUserService,
};

const verifyUserApp = {
  provide: Token.APPLICATIONS.VERIFY_USER,
  useClass: VerifyUserApplication,
};

const verifyUserService = {
    provide: Token.SERVICES.VERIFY_USER,
    useClass: VerifyUserService,
  };

@Module({
  imports: [DatabaseModule.forFeature([User])],
  controllers: [UsersController],
  providers: [
    // UsersService,
    UsersRepository,
    LocalStrategy,
    JwtStrategy,
    createUserApp,
    createUserService,
    getUserApp,
    getUserService,
    verifyUserApp,
    verifyUserService
  ],
})
export class UsersModule {}
