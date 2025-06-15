import { Inject, Injectable } from '@nestjs/common';
import { IVerifyUserApplication } from '../interfaces/applications/verify.user-application.interface';
import { User } from '../repository/entities/user.entity';
import { Token } from '../../../injection-tokens';
import { IVerifyUserService } from '../interfaces/services/verify.user-service.interface';
import { UserDomain } from '../domain/entities/User';

@Injectable()
export class VerifyUserApplication implements IVerifyUserApplication {
  constructor(
    @Inject(Token.SERVICES.VERIFY_USER)
    private readonly verifyUserService: IVerifyUserService,
  ) {}

  async verifyUser(email: string, password: string): Promise<UserDomain> {
    return await this.verifyUserService.verifyUser(email, password);
  }
}
