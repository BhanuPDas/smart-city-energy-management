import {
  Body,
  Controller,
  Get,
  Inject,
  Param,
  Post,
  UseGuards,
} from '@nestjs/common';
import { CreateUserDTO } from '../applications/DTO/create-user.dto';
import { Long } from 'typeorm';
import {
  Public,
  SwaggerCreateUSER,
  SwaggerGetAllUsers,
  SwaggerGetUserByEmail,
  SwaggerGetUserById,
} from '@app/infrastructure';
import { JwtAuthGuard } from '../../auth/guards/jwt.-auth.guard';
import { Token } from '../../../injection-tokens';
import { ICreateUserApplication } from '../interfaces/applications/create.user-application.interface';
import { IGetUserApplication } from '../interfaces/applications/get.user-appication.interface';
import { UserDomain } from '../domain/entities/User';

@Controller({ path: 'users', version: '1' })
export class UsersController {
  constructor(
    @Inject(Token.APPLICATIONS.CREATE_USER)
    private readonly createUserApp: ICreateUserApplication,

    @Inject(Token.APPLICATIONS.GET_USER)
    private readonly getUserApp: IGetUserApplication,
  ) {}

  @Post('register')
  @Public()
  @SwaggerCreateUSER(UserDomain)
  async createUser(@Body() newUser: CreateUserDTO): Promise<UserDomain> {
    return await this.createUserApp.create(newUser);
  }

  @Get()
  @UseGuards(JwtAuthGuard)
  @SwaggerGetAllUsers(UserDomain)
  async findAll(): Promise<UserDomain[]> {
    return await this.getUserApp.findAll();
  }

  @Get('/email/:email')
  // @UseGuards(JwtAuthGuard)
  @SwaggerGetUserByEmail(UserDomain)
  async findOne(@Param('email') email: string): Promise<UserDomain> {
    return await this.getUserApp.findOne(email);
  }

  @Get('/id/:id')
  // @UseGuards(JwtAuthGuard)
  @SwaggerGetUserById(UserDomain)
  async findOneById(@Param('id') id: Long): Promise<UserDomain> {
    return await this.getUserApp.findOneById(id);
  }

  // @Put('/id/:id')
  // @UseGuards(JwtAuthGuard)
  // @SwaggerUpdateUser(User)
  // async findOneAndUpdate(
  //   @Param('id') id: Long,
  //   @Body() updateUserDto: UpdateUserDTO,
  // ): Promise<User> {
  //   return await this.usersService.findOneAndUpdate(id, updateUserDto);
  // }

  // @Delete('/id/:id')
  // @UseGuards(JwtAuthGuard)
  // @SwaggerDeleteUser(User)
  // async findOneAndDelete(@Param('id') id: Long): Promise<string> {
  //   return await this.usersService.findOneAndDelete(id);
  // }
}
