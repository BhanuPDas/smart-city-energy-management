import {
  CanActivate,
  ExecutionContext,
  Inject,
  Injectable,
  UnauthorizedException,
} from '@nestjs/common';
import { ClientProxy } from '@nestjs/microservices';
import { map, Observable, tap } from 'rxjs';
import { SERVICE } from '../constants/services';
import { Reflector } from '@nestjs/core';
import { IS_PUBLIC_KEY } from '../public/public.decorator';

@Injectable()
export class JwtAuthGuard implements CanActivate {
  constructor(
    @Inject(SERVICE.AUTH_SERVICE) private readonly authClientProxy: ClientProxy,
    private readonly reflector: Reflector,
  ) {}

  canActivate(
    context: ExecutionContext,
  ): boolean | Promise<boolean> | Observable<boolean> {
    // 1) if @Public() is on this route or controller, allow through
   const isPublic = this.reflector.getAllAndOverride<boolean>(IS_PUBLIC_KEY, [
      context.getHandler(),
      context.getClass(),
    ]);
    console.log(isPublic)
    if (isPublic) {
      return true;
    }

    // 2) otherwise enforce JWT
    const request = context.switchToHttp().getRequest();
    const token = request.cookies?.Authentication;

    if (!token) {
      throw new UnauthorizedException('Token not found');
    }

    return this.authClientProxy
      .send('authenticate', {
        Authentication: token,
      })
      .pipe(
        tap((res) => {
          context.switchToHttp().getRequest().user = res;
        }),
        map(() => true),
      );
  }
}
