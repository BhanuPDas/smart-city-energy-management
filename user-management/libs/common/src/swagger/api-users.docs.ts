// src/common/decorators/api-standard-responses.decorator.ts
import { applyDecorators, Type } from '@nestjs/common';
import { ApiOperation, ApiParam, ApiResponse } from '@nestjs/swagger';
import { Long } from 'typeorm';

export function SwaggerCreateUSER<TModel extends Type<any>>(
  model: TModel,
  summary = 'User Registeration',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 201,
      description: `${model.name} created successfully`,
      type: model,
      example: {
        userId: 1,
        name: 'Jon Doe',
        email: 'Jon_Doe@gmail.com',
        telephoneNo: '124',
        password:
          '$2b$10$b8x3MmyF8UwXHQaqeEb2wORS3hfgHlaZqlTNVnk4WV7W0LCm9f6du',
        role: 'citizen',
      },
    }),
    ApiResponse({ status: 400, description: 'Bad Request' }),
  );
}

export function SwaggerGetAllUsers<TModel extends Type<any>>(
  model: TModel,
  summary = 'Get All Users',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 200,
      description: `${model.name} fetched`,
      type: model,

   example:[
    {
        userId: 1,
        name: 'Jon Doe',
        email: 'Jon_Doe@gmail.com',
        telephoneNo: '124',
        password:
          '$2b$10$b8x3MmyF8UwXHQaqeEb2wORS3hfgHlaZqlTNVnk4WV7W0LCm9f6du',
        role: 'citizen',
      },
      {
        userId: 2,
        name: 'Jon Doe 2',
        email: 'Jon_Doe2@gmail.com',
        telephoneNo: '124567',
        password:
          '$2b$10$b8x3MmyF8UwXHQaqeEb2wORS3hfgHlaZqlTNVnk4WV7W0LCm9f6du',
        role: 'citizen',
      },
   ]
    }),
    ApiResponse({ status: 404, description: 'Not Found' }),
  );
}

export function SwaggerGetUserById<TModel extends Type<any>>(
  model: TModel,
  summary = 'Get by ID',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 200,
      description: `${model.name} fetched`,
      type: model,
      example: {
        userId: 1,
        name: 'Jon Doe',
        email: 'Jon_Doe@gmail.com',
        telephoneNo: '124',
        password:
          '$2b$10$b8x3MmyF8UwXHQaqeEb2wORS3hfgHlaZqlTNVnk4WV7W0LCm9f6du',
        role: 'citizen',
      },
    }),
    ApiResponse({ status: 404, description: 'Not Found' }),
    ApiParam({ name: 'userId', description: 'Enter user ID' }),
  );
}

export function SwaggerGetUserByEmail<TModel extends Type<any>>(
  model: TModel,
  summary = 'Get by Email',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 200,
      description: `${model.name} fetched`,
      type: model,
      example: {
        userId: 1,
        name: 'Jon Doe',
        email: 'Jon_Doe@gmail.com',
        telephoneNo: '124',
        password:
          '$2b$10$b8x3MmyF8UwXHQaqeEb2wORS3hfgHlaZqlTNVnk4WV7W0LCm9f6du',
        role: 'citizen',
      },
    }),
    ApiResponse({ status: 404, description: 'Not Found' }),
  );
}

export function SwaggerUpdateUser<TModel extends Type<any>>(
  model: TModel,
  summary = 'Update User By ID',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 200,
      description: `${model.name} Updated`,
      type: model,
      example: {
        userId: 1,
        name: 'Updated Jon_Doe',
        email: 'Jon_Doe@gmail.com',
        telephoneNo: '124',
        password:
          '$2b$10$b8x3MmyF8UwXHQaqeEb2wORS3hfgHlaZqlTNVnk4WV7W0LCm9f6du',
        role: 'citizen',
      },
    }),
    ApiResponse({ status: 404, description: 'Not Found' }),
    ApiParam({ name: 'id', type: Number, description: 'Enter user ID' }),
  );
}

export function SwaggerDeleteUser<TModel extends Type<any>>(
  model: TModel,
  summary = 'Find and Delete User by ID ',
): MethodDecorator {
  return applyDecorators(
    ApiOperation({ summary }),
    ApiResponse({
      status: 200,
      description: `${model.name} Deleted`,
      type: model,
    }),
    ApiResponse({ status: 404, description: 'Not Found' }),
    ApiParam({ name: 'id', type: Number, description: 'Enter user ID' }),
  );
}
