package com.tasks.test.user.service;

import org.springframework.http.ResponseEntity;


/**
 * Сервис подписчика сообщений
 */
public interface UserService<T>
{
    ResponseEntity getAll() throws Exception;
    ResponseEntity get(Long id) throws Exception;
    ResponseEntity add(T entity) throws Exception;
    ResponseEntity update(T entity) throws Exception;
    ResponseEntity delete(T entity) throws Exception;
}
