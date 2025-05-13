// src/common/decorators/api-standard-responses.decorator.ts
import { applyDecorators, Type } from '@nestjs/common';
import { ApiBody, ApiOperation, ApiResponse } from '@nestjs/swagger';

export function SwaggerLogin<TModel extends Type<any>>(
  model: TModel,
  summary = 'User Login',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 201,
      description: `${model.name} Logged in successfully`,
      type: model,
    }),
    ApiResponse({ status: 400, description: 'Bad Request' }),
    ApiBody({
      schema: {
        type: 'object',
        properties: {
          email: { type: 'string' },
          password: { type: 'string' },
        },
        required: ['email', 'password'],
      },
    })
  );
}


export function SwaggerLogout<TModel extends Type<any>>(
  model: TModel,
  summary = 'User Logout',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 201,
      description: `${model.name} Logged out successfully`,
      type: model,
    }),
    ApiResponse({ status: 400, description: 'Bad Request' }),
  );
}

export function SwaggerVerifyUser<TModel extends Type<any>>(
  model: TModel,
  summary = 'For Spring Gateway to verify incoming Req',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 201,
      description: `${model.name} User verified successfully`,
      type: model,
    }),
    ApiResponse({ status: 400, description: 'Bad Request' }),
  );
}
