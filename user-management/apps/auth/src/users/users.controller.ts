import { Body, Controller, Delete, Get, Param, Post, Put } from '@nestjs/common';
import { UsersService } from './users.service';
import { CreateUserDTO } from './DTO/create-user.dto';
import { User } from './entities/user.entity';
import { Long } from 'typeorm';
import { UpdateUserDTO } from './DTO/update-user.dto';

@Controller('users')
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  @Post('register')
  async createUser(@Body() createUserDto: CreateUserDTO): Promise<User> {
    return await this.usersService.createUser(createUserDto);
  }

  @Get()
  async findAll(): Promise<User[]> {
    return await this.usersService.findAll();
  }

  @Get('/email/:email')
  async findOne(@Param('email') email: string): Promise<User> {
    return await this.usersService.findOne(email);
  }

  @Get('/id/:id')
  async findOneById(@Param('id') id: Long): Promise<User> {
    return await this.usersService.findOneById(id);
  }

  @Put('/id/:id')
  async findOneAndUpdate(
    @Param('id') id: Long,
    @Body() updateUserDto: UpdateUserDTO,
  ): Promise<User> {
    return await this.usersService.findOneAndUpdate(id, updateUserDto);
  }

  @Delete('/id/:id')
  async findOneAndDelete(@Param('id') id: Long): Promise<string> {
    return await this.usersService.findOneAndDelete(id);
  }
}
