package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.LikeVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
        int loginId = userDetailsVo.getUserVo().getUserId();
        UserVo userVo = userService.findUserById(id);
        log.debug("findUser : {} / {}",userVo.getId(), userVo.getName());
        return new ResponseEntity<>(userVo, HttpStatus.ACCEPTED);
    }


    // 회원정보 수정 (이름, 사진)
    @PutMapping("/{id}")
    public ResponseEntity<UserVo> modifyUser(@PathVariable String id, @RequestBody ModifyUserDto modifyUserDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        log.debug(userDetailsVo.getUserVo().getId());
        if(userDetailsVo.getUserVo().getId().equals(id)){
            UserVo userVo = userService.modifyUser(id, modifyUserDto);
            log.debug("modified info : {} {}",userVo.getName(),userVo.getUserProfileImage());
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
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
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
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
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // 즐겨찾기 추가
    @PostMapping("/{id}/like")
    public ResponseEntity<Integer> addLike(@PathVariable String id, @RequestBody Map<String, Integer> param, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)){
            LikeInsertDto likeInsertDto = new LikeInsertDto();
            int teacherId = param.get("teacherId");
            log.debug("{} / {}",userService.findUserById(id).getUserId(), teacherId);
            likeInsertDto.setTeacherId(teacherId);
            likeInsertDto.setStudentId(userService.findUserById(id).getUserId());
            LikeVo likeVo = userService.addLike(likeInsertDto);
            if(likeVo!=null) {
                log.debug("즐찾한 학생 : {} 즐찾당한 선생 : {}", likeVo.getStudent().getUserId(), likeVo.getTeacher().getTeacherId());
                return new ResponseEntity<>(likeVo.getLikeId(), HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // 즐겨찾기 목록
    @GetMapping("/{id}/like")
    public ResponseEntity<List<TeacherVo>> findAllLike(@PathVariable String id, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)){
            List<TeacherVo> likeList = userService.findAllLike(id);
            return new ResponseEntity<>(likeList, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{id}/like/{likeId}")
    public ResponseEntity deleteLike(@PathVariable String id, @PathVariable int likeId, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        if(userDetailsVo.getUserVo().getId().equals(id)){
            userService.deleteLike(likeId);
            for (TeacherVo teacherVo:userService.findAllLike(id)){
                log.debug("삭제후 {} {}",teacherVo.getTeacherId(), teacherVo.getName());
            }
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
