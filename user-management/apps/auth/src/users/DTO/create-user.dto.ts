import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString } from 'class-validator';

export class CreateUserDTO {
  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: 'Jon Doe',
    description: 'Name of the user',
  })
  name: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: 'Jon_Doe@Gmail.com',
    description: 'Unique email for login',
  })
  email: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: '123456789',
    description: 'Phone# of the user',
  })
  phone: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: 'citizen or city_planner',
    description: 'Role of user. make sure its lowercase',
  })
  role: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: '123',
    description: 'Secure password',
  })
  password: string;
}
