import { Role } from '@app/common';

export interface JwtPayload {
  userId: string;
  email: string;
  role: Role;
}
