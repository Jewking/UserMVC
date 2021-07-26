package com.tasks.test.user.repository;

import com.tasks.test.user.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий подписчика сообщений
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
}