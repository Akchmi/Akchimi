package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.UserRegistDto;
import com.quokka.classmusic.api.service.UserService;
import com.quokka.classmusic.db.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping
//    public ResponseEntity<Void> regist(@RequestBody UserRegistDto userRegistDto) {
//        try {
//            int res = userService.regist(userRegistDto);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
