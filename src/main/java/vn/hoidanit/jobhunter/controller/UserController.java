package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable long id) {
        User user = this.userService.handleFindUserById(id);
        return user;
    }

    @GetMapping("/user")
    public List<User> getAllUser() {
        List<User> users = this.userService.handleGetAllUsers();
        return users;
    }

    @PostMapping("/user")
    public User createNewUser(@RequestBody User postManUser) {
        User userCreated = this.userService.handleSaveUser(postManUser);
        return userCreated;
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User reqUser) {
        User user = this.userService.handleUpdateUser(reqUser);
        return user;
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        this.userService.handleDeleteUserById(id);
        return "delete user";
    }
}
