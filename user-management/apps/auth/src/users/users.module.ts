import { Module } from '@nestjs/common';
import { UsersController } from './users.controller';
import { UsersService } from './users.service';
import { ConfigModule, DatabaseModule } from '@app/common';
import { User } from './entities/user.entity';
import { UsersRepository } from './users.repository';
import { LocalStrategy } from '../strategies/local.strategy';

@Module({
  imports: [ConfigModule, DatabaseModule, DatabaseModule.forFeature([User])],
  controllers: [UsersController],
  providers: [UsersService, UsersRepository, LocalStrategy],
})
export class UsersModule {}
