package ru.abtank.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.abtank.controller.NotFoundException;
import ru.abtank.persist.entity.User;
import ru.abtank.persist.repo.UserRepository;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Tag (name = "User resource API", description = "API to operate User resource ...") // тег для Swagger описание
@CrossOrigin(origins = "http://localhost:63342") // разрешаем получать и обрабатывать запросы с данного адреса
@RequestMapping("/api/v1/user")   //
@RestController
public class UserResource {

    private UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    produces - указывает в каком формате отправляем данные
    //    consumes - указывает в каком формате получаем данные
    @GetMapping(path = "/all", produces = "application/json")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public User findById(@PathVariable("id") Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(User.class.getSimpleName(), id, " Rest не нашел"));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user) {
        if (user.getId() != null) { // так как мы гарантированно должны создать а не изменить существующего
            throw new IllegalArgumentException("Id found in the create request");
        }
        userRepository.save(user);
        return user;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User user) {
        if (user.getId() == null) { // так как мы гарантированно должны изменить существующего
            throw new IllegalArgumentException("Id not found in the update request");
        }
        userRepository.save(user);
        return user;
    }

    @DeleteMapping(path = "/{id}/id", produces = "application/json")
    public void deleteById(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
