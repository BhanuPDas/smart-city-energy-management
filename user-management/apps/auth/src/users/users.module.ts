import { Module } from '@nestjs/common';
import { UsersController } from './users.controller';
import { UsersService } from './users.service';
import { ConfigModule, DatabaseModule } from '@app/common';
import { User } from './entities/user.entity';

@Module({
  imports: [ConfigModule, DatabaseModule, DatabaseModule.forFeature([User])],
  controllers: [UsersController],
  providers: [UsersService],
})
export class UsersModule {}
