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
      description: `success`,
      type: model,
      example: {
        access_token:
          'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI0IiwiZW1haWwiOiJtNWVlZkBnbWFpbC5jb20iLCJyb2xlIjoiY2l0eV9wbGFubmVyIiwiaWF0IjoxNzQ3NjAwMTIxLCJleHAiOjE3NDc2MDEwMjF9.lxoVC_kBQWWXPM8ogHqBO72QY68llhy_sz1OIaVi9wQ',
        user: {
          userId: 1,
          name: 'John Doe',
          email: 'John_Doe@gmail.com',
          phone: '124',
          role: 'city_planner',
        },
      },
    }),
    ApiResponse({
      status: 404,
      description: 'NotFoundException',
      example: {
        response: {
          message: 'Entity Not Found',
          error: 'Not Found',
          statusCode: 404,
        },
        status: 404,
        options: {},
        message: 'Entity Not Found',
        name: 'NotFoundException',
      },
    }),
    ApiBody({
      schema: {
        type: 'object',
        properties: {
          email: { type: 'string' },
          password: { type: 'string' },
        },
        required: ['email', 'password'],
      },
    }),
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
      description: `success`,
      type: model,
      example: {
        status: 'alive',
      },
    }),

    ApiResponse({
      status: 401,
      description: 'Unauthorized',
      type: model,
      example: {
        status: 'expired',
      },
    }),
  );
}
