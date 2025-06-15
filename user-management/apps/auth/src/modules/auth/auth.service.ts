import { HttpException, HttpStatus, Injectable, UnauthorizedException } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { JwtService } from '@nestjs/jwt';
import { Request, Response } from 'express';
import { JwtPayload } from './jwt-payload.interface';
import { UserDomain } from '../users/domain/entities/User';

@Injectable()
export class AuthService {
  constructor(
    private readonly configService: ConfigService,
    private readonly jwtService: JwtService,
  ) {}

  login(user: UserDomain, res: Response) {
    const expiresIn = new Date();
    expiresIn.setSeconds(
      Number(
        expiresIn.getSeconds() +
          this.configService.getOrThrow('JWT_EXPIRATION'),
      ),
    );

    const tokenPayload: JwtPayload = {
      userId: user.userId.toString(),
      email: user.email,
      role: user.role,
    };

    const accessToken = this.jwtService.sign(tokenPayload);
    res.cookie('Authentication', accessToken, {
      httpOnly: true,
      expires: expiresIn,
    });

    const { password, ...safeUser } = user;
    return {
      access_token: accessToken,
      user: safeUser,
    };
  }

  verify(req: Request) {
    const authHeader = req.headers['authorization'];

    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      throw new HttpException({ status: 'expired' }, HttpStatus.UNAUTHORIZED);
    }
    
    const token = authHeader.substring(7);
    try {
      this.jwtService.verify(token, {
        secret: this.configService.getOrThrow('JWT_SECRET'),
      });
      return { status: 'alive' };
    } catch (err) {
      if (err.name === 'TokenExpiredError') {
      throw new HttpException({ status: 'expired' }, HttpStatus.UNAUTHORIZED);
      }
      throw new UnauthorizedException(err.message);
    }
  }

  // logout(response: Response) {
  //   response.cookie('Authentication', '', {
  //     httpOnly: true,
  //     expires: new Date(),
  //   });
  // }
}
