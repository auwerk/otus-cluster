package org.auwerk.otus.arch.demoservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.auwerk.otus.arch.demoservice.api.dto.User;
import org.auwerk.otus.arch.demoservice.service.UserService;
import org.auwerk.otus.arch.demoservice.service.exception.UserNotFoundException;
import org.auwerk.otus.arch.demoservice.service.exception.UserServiceException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (UserServiceException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok(user);
        } catch (UserServiceException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (UserServiceException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserServiceException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
