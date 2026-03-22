package com.swapnil.bookstore.app.controller;


import com.swapnil.bookstore.app.dto.UserDto;
import com.swapnil.bookstore.app.entity.User;
import com.swapnil.bookstore.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    // needs admin level authorization

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        return new UserDto(user.getId(),user.getEmail(),user.getRole());
    }

}
