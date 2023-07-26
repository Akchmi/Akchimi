package com.quokka.classmusic.api.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.quokka.classmusic.api.request.ChangePasswordDto;
import com.quokka.classmusic.api.request.FindIdDto;
import com.quokka.classmusic.api.request.ModifyUserDto;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 정보보기
    @GetMapping("/{id}")
    public ResponseEntity<UserVo> findUser(@PathVariable String id, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)) {
            UserVo userVo = userService.findUserById(id);
            log.debug("findUser : {} / {}",userVo.getId(), userVo.getName());
            return new ResponseEntity<>(userVo, HttpStatus.ACCEPTED);
        }else{
            log.debug("아이디가 다릅니다.");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // 이름과 이메일로 아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<String> findId(@RequestBody FindIdDto findIdDto){
        try{
            UserVo userVo = userService.findId(findIdDto);
            log.debug("find-id : {}",userVo.getId());
            return new ResponseEntity<>(userVo.getId(), HttpStatus.ACCEPTED);
        }catch (NoSuchElementException e){
            log.debug("해당하는 아이디를 찾지 못했습니다.");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 회원정보 수정 (이름, 사진)
    @PutMapping("/{id}")
    public ResponseEntity<UserVo> modifyUser(@PathVariable String id, @RequestBody ModifyUserDto modifyUserDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        log.debug(userDetailsVo.getUserVo().getId());
        if(userDetailsVo.getUserVo().getId().equals(id)){
            UserVo userVo = userService.modifyUser(id, modifyUserDto);
            log.debug("modified info : {} {}",userVo.getName(),userVo.getUserProfileImage());
            return new ResponseEntity<>(userVo, HttpStatus.ACCEPTED);
        }else{
            log.debug("아이디가 다릅니다.");
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // 회원 탈퇴 (회원 type 3으로 변경 | 0:user, 1:teacher, 2:admin, 3:탈퇴유저)
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)){
            userService.deleteUser(userDetailsVo.getUserVo().getUserId());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else{
            log.debug("아이디가 다릅니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // 비밀번호 변경
    @PutMapping("/{id}/password")
    public ResponseEntity changePassword(@PathVariable String id, @RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)){
            userService.changePassword(id, changePasswordDto);
            log.debug("id : {} newPassword : {}",userDetailsVo.getUserVo().getId(), userDetailsVo.getUserVo().getPassword());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else{
            log.debug("아이디가 다릅니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
