package com.danil.forwork.Controllers;

import com.danil.forwork.Services.UserService;
import com.danil.forwork.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reg")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        return userService.createUserService(userDto);
    }
}
