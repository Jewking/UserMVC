package com.tasks.test.user.service;

import com.tasks.test.user.dto.UserDto;
import com.tasks.test.user.entity.User;
import com.tasks.test.user.exceptions.UserNotFoundException;
import com.tasks.test.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса подписчика сообщений
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService<UserDto>
{
    private static final Logger LOG = Logger.getLogger(UserService.class);
    private final UserRepository repository;

    @Override
    public ResponseEntity getAll() throws Exception
    {
        Iterable<User> userList = repository.findAll();

        if (!userList.iterator().hasNext())
        {
            throw new UserNotFoundException("В базе данных не найдено ни одной записи!");
        }

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity get(Long id) throws Exception
    {
        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Не получилось найти пользователя."));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity add(UserDto userDto) throws Exception
    {
        repository.save(convertToUser(userDto));
        return new ResponseEntity(HttpStatus.CREATED);
}

    @Override
    public ResponseEntity update(UserDto userDto) throws Exception
    {
        checkIfUserExists(userDto);
        User updatedUser = repository.save(convertToUser(userDto));
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity delete(UserDto userDto) throws Exception
    {
        checkIfUserExists(userDto);
        repository.delete(convertToUser(userDto));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private User convertToUser(UserDto userDto)
    {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setMail(userDto.getMail());
        return user;
    }

    private void checkIfUserExists(UserDto userDto)
    {
        if (userDto.getId() == null)
        {
            throw new UserNotFoundException("Не передан идентификатор пользователя.");
        }

        repository.findById(userDto.getId()).orElseThrow(
                () -> new UserNotFoundException("Не получилось найти пользователя."));
    }
}