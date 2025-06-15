import { Controller, Post, Req, Res, UseGuards } from '@nestjs/common';
import { AuthService } from './auth.service';
import { LocalAuthGuard } from './guards/local-auth.guard';
import { CurrentUser } from './current-user.decorator';
import { Request, Response } from 'express';
import { MessagePattern, Payload } from '@nestjs/microservices';
import { JwtAuthGuard } from './guards/jwt.-auth.guard';
import {
  Public,
  SwaggerLogin,
  SwaggerVerifyUser,
} from '@app/infrastructure';
import { UserDomain } from '../users/domain/entities/User';

@Controller({ path: 'auth', version: '1' })
export class AuthController {
  constructor(private readonly authService: AuthService) {}

  @Public()
  @Post('login')
  @UseGuards(LocalAuthGuard)
  @SwaggerLogin(UserDomain)
  login(
    @CurrentUser() user: UserDomain,
    @Res({ passthrough: true }) response: Response,
  ) {
    return this.authService.login(user, response);
  }

  @UseGuards(JwtAuthGuard)
  @MessagePattern('authenticate')
  async authenticate(@Payload() data: any) {
    return data.user;
  }

  @Post('verify')
  @SwaggerVerifyUser(UserDomain)
  verify(@Req() req: Request) {
    // JwtAuthGuard has already attached the payload on req.user
    return this.authService.verify(req);
  }

  // @Post('logout')
  // // @SwaggerLogout(User)
  // logout(@Res({ passthrough: true }) response: Response) {
  //   // response.clearCookie('access_token');
  //   // return response.sendStatus(200);
  //   return this.authService.logout(response);
  // }
}
