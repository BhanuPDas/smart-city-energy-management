import {
  Injectable,
  NestInterceptor,
  ExecutionContext,
  CallHandler,
  Logger,
} from '@nestjs/common';
import { Observable, firstValueFrom } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { v4 as uuidv4 } from 'uuid';
import { HttpService } from '@nestjs/axios';
import { AxiosError } from 'axios';
import { Config } from './config';

@Injectable()
export class LoggingInterceptor implements NestInterceptor {
  constructor(private readonly httpService: HttpService) {}
  private readonly logger = new Logger(LoggingInterceptor.name);

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    if (context.getType() === 'http') {
      return this.logHttpCall(context, next);
    }
    return next.handle();
  }

  private async pushToGrafana(body: string) {
    const logs = {
      streams: [
        {
          stream: {
            env: 'development',
          },
          values: [[(Date.now() * 1e6).toString(), body]],
        },
      ],
    };
    try {
      await firstValueFrom(
        this.httpService
          .post(Config.grafana.url, logs)
          .pipe(
            catchError((error: AxiosError) => {
              this.logger.error(error?.response?.data);
              throw error;
            }),
          ),
      );
    } catch (error) {
      this.logger.error(error.toString());
    }
  }

  private logHttpCall(context: ExecutionContext, next: CallHandler) {
    const request = context.switchToHttp().getRequest();
    const userAgent = request.get('user-agent') || '';
    const { ip, method, path: url } = request;
    const correlationKey = uuidv4();
    const userId = request.user?.userId;

    this.logger.log(
      `[${correlationKey}] ${method} ${url} ${userId} ${userAgent} ${ip}: ${
        context.getClass().name
      } ${context.getHandler().name}`,
    );

    const now = Date.now();
    return next.handle().pipe(
      tap(async () => {
        const response = context.switchToHttp().getResponse();

        const { statusCode } = response;
        const contentLength = response.get('content-length');
        const logData = `[${correlationKey}] ${method} ${url} ${statusCode} ${contentLength}: ${
          Date.now() - now
        }ms`;
        this.logger.log(logData);
        await this.pushToGrafana(logData);
      }),
    );
  }
}