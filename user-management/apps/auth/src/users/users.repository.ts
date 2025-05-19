import { AbstractRepository } from '@app/common';
import { Injectable, Logger } from '@nestjs/common';
import { User } from './entities/user.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { EntityManager, Repository } from 'typeorm';

@Injectable()
export class UsersRepository extends AbstractRepository<User> {
  protected readonly logger = new Logger(UsersRepository.name);

  constructor(
    @InjectRepository(User)
    protected readonly usersRepository: Repository<User>,
    protected readonly entityManager: EntityManager,
  ) {
    super(usersRepository, entityManager);
  }
}
