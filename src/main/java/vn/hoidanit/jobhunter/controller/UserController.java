package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // fetch user
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User fetchUser = this.userService.handleFindUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetchUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = this.userService.handleGetAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // create user
    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        User userCreated = this.userService.handleSaveUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    // update user
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User reqUser) {
        User user = this.userService.handleUpdateUser(reqUser);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") long id)
            throws IdInvalidException {
        if (id > 1500) {
            throw new IdInvalidException("invalid user id");
        }
        this.userService.handleDeleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body("delete user");
    }
}
