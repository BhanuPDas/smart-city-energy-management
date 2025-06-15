import { Inject, Injectable, UnauthorizedException } from '@nestjs/common';
import { PassportStrategy } from '@nestjs/passport';
import { Strategy } from 'passport-local';
import { Token } from '../../../injection-tokens';
import { IVerifyUserApplication } from '../../users/interfaces/applications/verify.user-application.interface';

@Injectable()
export class LocalStrategy extends PassportStrategy(Strategy) {
  constructor(
    @Inject(Token.APPLICATIONS.VERIFY_USER)
    private readonly verifyUserApp: IVerifyUserApplication,
  ) {
    super({
      usernameField: 'email',
      passwordField: 'password',
      session: false,
    });
  }

  async validate(email: string, password: string) {
    try {
      return await this.verifyUserApp.verifyUser(email, password);
    } catch (error) {
      throw new UnauthorizedException(error);
    }
  }
}
