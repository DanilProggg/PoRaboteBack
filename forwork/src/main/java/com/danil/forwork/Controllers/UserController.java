package com.danil.forwork.Controllers;

import com.danil.forwork.Entities.User;
import com.danil.forwork.Services.UserService;
import com.danil.forwork.dtos.UserDto;
import com.danil.forwork.dtos.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public User getUser(Principal user){
       return userService.getUserService(user);
    }

    @PostMapping("/user")
    public ResponseEntity<?> putUserInfo(Principal principal, @RequestBody UserInfoDto userInfoDto){
        return userService.putUserInfoService(principal, userInfoDto);
    }

}
