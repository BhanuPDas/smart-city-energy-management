import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Post,
  Put,
  UseGuards,
} from '@nestjs/common';
import { UsersService } from './users.service';
import { CreateUserDTO } from './DTO/create-user.dto';
import { User } from './entities/user.entity';
import { Long } from 'typeorm';
import { UpdateUserDTO } from './DTO/update-user.dto';
import { JwtAuthGuard } from '../guards/jwt.-auth.guard';
import { Roles, RolesGuard } from '@app/common/auth';
import {
  Public,
  Role,
  SwaggerCreateUSER,
  SwaggerDeleteUser,
  SwaggerGetAllUsers,
  SwaggerGetUserByEmail,
  SwaggerGetUserById,
  SwaggerUpdateUser,
} from '@app/common';

@Controller({ path: 'users', version: '1' })
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  @Post('register')
  @Public()
  @SwaggerCreateUSER(User)
  async createUser(@Body() createUserDto: CreateUserDTO): Promise<User> {
    return await this.usersService.createUser(createUserDto);
  }

  @Get()
  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.CITY_PLANNER)
  @SwaggerGetAllUsers(User)
  async findAll(): Promise<User[]> {
    return await this.usersService.findAll();
  }

  @Get('/email/:email')
  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.CITY_PLANNER)
  @SwaggerGetUserByEmail(User)
  async findOne(@Param('email') email: string): Promise<User> {
    return await this.usersService.findOne(email);
  }

  @Get('/id/:id')
  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.CITY_PLANNER)
  @SwaggerGetUserById(User)
  async findOneById(@Param('id') id: Long): Promise<User> {
    return await this.usersService.findOneById(id);
  }

  @Put('/id/:id')
  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.CITY_PLANNER)
  @SwaggerUpdateUser(User)
  async findOneAndUpdate(
    @Param('id') id: Long,
    @Body() updateUserDto: UpdateUserDTO,
  ): Promise<User> {
    return await this.usersService.findOneAndUpdate(id, updateUserDto);
  }

  @Delete('/id/:id')
  @UseGuards(JwtAuthGuard, RolesGuard)
  @Roles(Role.CITY_PLANNER)
  @SwaggerDeleteUser(User)
  async findOneAndDelete(@Param('id') id: Long): Promise<string> {
    return await this.usersService.findOneAndDelete(id);
  }
}
