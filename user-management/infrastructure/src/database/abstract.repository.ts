import { Logger, NotFoundException } from '@nestjs/common';
import { AbstractEntity } from './abstract.entity';
import { DeepPartial, EntityManager, FindOptionsWhere, Repository } from 'typeorm';
import { QueryDeepPartialEntity } from 'typeorm/query-builder/QueryPartialEntity';

export abstract class AbstractRepository<T extends AbstractEntity<T>> {
  protected abstract readonly logger: Logger;

  constructor(
   protected readonly entityRepository: Repository<T>,
   protected readonly entityManager: EntityManager,
  ) {}

  async create(entity: DeepPartial<T>): Promise<T> {
    const e =  this.entityRepository.create(entity);
    return this.entityManager.save(e);
  }

  async findOne(where: FindOptionsWhere<T>): Promise<T> {
    const entity = await this.entityRepository.findOne({ where });
    if (!entity) {
      this.logger.warn(`Could not find entity with where condition ${where}`);

      throw new NotFoundException(`Entity Not Found`);
    }
    return entity as T;
  }

  async findOneById(where: FindOptionsWhere<T>): Promise<T>{
    const entity = await this.entityRepository.findOneBy(where);
    if (!entity) {
      this.logger.warn(`Could not find entity with where condition ${where}`);

      throw new NotFoundException(`Entity Not Found`);
    }
    return entity as T;
  }

  async findOneAndUpdate(
    where: FindOptionsWhere<T>,
    partialEntity: QueryDeepPartialEntity<T>,
  ): Promise<T> {
    const updatedEntity = await this.entityRepository.update(
      where,
      partialEntity,
    );

    if (!updatedEntity.affected) {
      this.logger.warn('Could not find updated entity', where);

      throw new NotFoundException(`Entity Not Found`);
    }

    return this.findOneById(where);
  }

  async find(where: FindOptionsWhere<T>): Promise<T[]> {
    return await this.entityRepository.findBy(where);
  }

  async findOneAndDelete(where: FindOptionsWhere<T>): Promise<string> {
    const entity = await this.findOneById(where);
    await this.entityRepository.delete(entity.userId.toString());

    return `Entity with ID ${entity.userId} has been deleted`;
  }
}
