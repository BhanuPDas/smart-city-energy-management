import { Long, PrimaryGeneratedColumn } from 'typeorm';

export class AbstractEntity<T> {
  @PrimaryGeneratedColumn('increment')
  userId: Long;

  constructor(entity: Partial<T>) {
    Object.assign(this, entity);
  }
}
