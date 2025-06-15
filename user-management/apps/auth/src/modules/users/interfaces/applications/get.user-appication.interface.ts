import { Long } from 'typeorm';
import { UserDomain } from '../../domain/entities/User';

export interface IGetUserApplication {
  findAll(): Promise<UserDomain[]>;
  findOne(email: string): Promise<UserDomain>;
  findOneById(id: Long): Promise<UserDomain>;
}
