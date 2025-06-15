import { Email } from "../users/domain/value-objects/email";

export interface JwtPayload {
  userId: string;
  email: Email;
  role: string;
}
