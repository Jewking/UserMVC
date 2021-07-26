package com.tasks.test.user.controller;

import com.tasks.test.user.dto.UserDto;
import com.tasks.test.user.exceptions.UserNotFoundException;
import com.tasks.test.user.response.ErrorResponse;
import com.tasks.test.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Рест-контроллер подписчика сообщений
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users", produces = APPLICATION_JSON_VALUE)
@Validated
public class UserController
{
    private static final Logger LOG = Logger.getLogger(UserController.class);
    private final UserServiceImpl service;

    @GetMapping("/list")
    public ResponseEntity getAll() throws Exception
    {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity get(@PathVariable @Min(1) Long id) throws Exception
    {
        return service.get(id);
    }

    @PostMapping("/add")
    public ResponseEntity add(@Valid @RequestBody UserDto action) throws Exception
    {
        return service.add(action);
    }

    @PutMapping("/update")
    public ResponseEntity update(@Valid @RequestBody UserDto action) throws Exception
    {
        return service.update(action);
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@Valid @RequestBody UserDto action) throws Exception
    {
        return service.delete(action);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(UserNotFoundException ex)
    {
        LOG.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ErrorResponse(
                "По запросу ничего не найдено!",
                Collections.singletonList(ex.getMessage()),
                HttpStatus.NOT_FOUND),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex)
    {
        LOG.error("Ошибка валидации идентификатора пользователя!", ex);
        return new ResponseEntity<>(new ErrorResponse(
                "Ошибка валидации идентификатора пользователя!",
                constraintViolationsToString(ex),
                HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        LOG.error("Ошибка валидации перевадаемого пользователя!", ex);
        return new ResponseEntity<>(new ErrorResponse(
                "Ошибка валидации перевадаемого пользователя!",
                methodArgumentNotValidtionString(ex),
                HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAllExceptions(Exception ex)
    {
        LOG.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }

    private static List<String> constraintViolationsToString(ConstraintViolationException ex)
    {
        return ex.getConstraintViolations().stream()
                .map(e -> e.getPropertyPath() + "принимает значение '" + e.getInvalidValue() + "'" + e.getMessage())
                .collect(Collectors.toList());
    }

    private static List<String> methodArgumentNotValidtionString(MethodArgumentNotValidException ex)
    {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getObjectName() + " принимает значение '" + e.getRejectedValue() + "' " + e.getDefaultMessage())
                .collect(Collectors.toList());
    }
}
