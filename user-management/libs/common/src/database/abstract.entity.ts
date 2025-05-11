import { Long, PrimaryGeneratedColumn } from 'typeorm';

export class AbstractEntity<T> {
  @PrimaryGeneratedColumn()
  id: Long;

  constructor(entity: Partial<T>) {
    Object.assign(this, entity);
  }
}
