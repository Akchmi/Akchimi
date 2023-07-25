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

@Slf4j
@RestController
@RequestMapping("/users")
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
//    회원탈퇴(지금은 delete했는데 type 바뀌는거?), 임시비번발급
//    프로필 상세조회, 아이디찾기, 회원정보수정, 비밀번호변경
    @GetMapping("/{id}")
    public ResponseEntity findUser(@PathVariable String id, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)) {
            UserVo userVo = userService.findUserById(id);
            log.debug("findUser : {} / {}",userVo.getId(), userVo.getName());
        }else{
            log.debug("아이디가 다릅니다.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/find-id")
    public ResponseEntity findId(@RequestBody FindIdDto findIdDto){
        UserVo userVo = userService.findId(findIdDto);
        log.debug("find-id : {}",userVo.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity modifyUser(@PathVariable String id, @RequestBody ModifyUserDto modifyUserDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        log.debug("##########3{} {}###########",modifyUserDto.getName(),modifyUserDto.getUserProfileImage());
        if(userDetailsVo.getUserVo().getId().equals(id)){
            UserVo userVo = userService.modifyUser(id, modifyUserDto);
            log.debug("modified info : {} {}",userVo.getName(),userVo.getUserProfileImage());
        }else{
            log.debug("아이디가 다릅니다.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)){
            userService.deleteUser(userDetailsVo.getUserVo().getUserId());
        }else{
            log.debug("아이디가 다릅니다.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity changePassword(@PathVariable String id, @RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)){
            userService.changePassword(id, changePasswordDto);
            log.debug("id : {} newPassword : {}",userDetailsVo.getUserVo().getId(), userDetailsVo.getUserVo().getPassword());
        }else{
            log.debug("아이디나가 다릅니다.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
