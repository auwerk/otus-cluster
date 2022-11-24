package org.auwerk.otus.arch.demoservice.repository;

import org.auwerk.otus.arch.demoservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
