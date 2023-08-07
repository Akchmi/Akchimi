package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.LikeVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.response.UserVo;
import com.quokka.classmusic.api.service.UserService;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 정보보기
    @GetMapping("/{id}")
    public ResponseEntity<UserVo> findUser(@PathVariable String id) {
        UserVo userVo = userService.findUserById(id);
        log.debug("findUser : {} / {}", userVo.getId(), userVo.getName());
        return new ResponseEntity<>(userVo, HttpStatus.OK);
    }


    // 회원정보 수정 (이름, 사진)
    @PutMapping("/{id}")
    public ResponseEntity<UserVo> modifyUser(@PathVariable String id, @RequestBody ModifyUserDto modifyUserDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo) {
        log.debug(userDetailsVo.getUserVo().getId());
        String currentLoginId = userDetailsVo.getUserVo().getId();

        if (!currentLoginId.equals(id)) {
            log.debug("아이디가 다릅니다.");
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS);
        }

        UserVo userVo = userService.modifyUser(id, modifyUserDto);
        log.debug("modified info : {} {}", userVo.getName(), userVo.getUserProfileImage());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    // 회원 탈퇴 (회원 type 3으로 변경 | 0:user, 1:teacher, 2:admin, 3:탈퇴유저)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id, @AuthenticationPrincipal UserDetailsVo userDetailsVo) {
        log.debug(userDetailsVo.getUserVo().getId());
        String currentLoginId = userDetailsVo.getUserVo().getId();

        if (!currentLoginId.equals(id)) {
            log.debug("아이디가 다릅니다.");
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS);
        }

        userService.deleteUser(userDetailsVo.getUserVo().getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 비밀번호 변경
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable String id, @RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo) {
        log.debug(userDetailsVo.getUserVo().getId());
        String currentLoginId = userDetailsVo.getUserVo().getId();

        if (!currentLoginId.equals(id)) {
            log.debug("아이디가 다릅니다.");
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS);
        }

        userService.changePassword(id, changePasswordDto);
        log.debug("id : {} newPassword : {}", userDetailsVo.getUserVo().getId(), userDetailsVo.getUserVo().getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 즐겨찾기 추가
    @PostMapping("/{id}/like")
    public ResponseEntity<Integer> addLike(@PathVariable String id, @RequestBody Map<String, Integer> param, @AuthenticationPrincipal UserDetailsVo userDetailsVo) {
        log.debug(userDetailsVo.getUserVo().getId());
        String currentLoginId = userDetailsVo.getUserVo().getId();

        if (!currentLoginId.equals(id)) {
            log.debug("아이디가 다릅니다.");
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS);
        }

        LikeInsertDto likeInsertDto = new LikeInsertDto();
        int teacherId = param.get("teacherId");
        log.debug("{} / {}", userService.findUserById(id).getUserId(), teacherId);
        likeInsertDto.setTeacherId(teacherId);
        likeInsertDto.setStudentId(userService.findUserById(id).getUserId());
        LikeVo likeVo = userService.addLike(likeInsertDto);

        log.debug("즐찾한 학생 : {} 즐찾당한 선생 : {}", likeVo.getStudent().getUserId(), likeVo.getTeacher().getTeacherId());
        return new ResponseEntity<>(likeVo.getLikeId(), HttpStatus.CREATED);
    }

    // 즐겨찾기 목록
    @GetMapping("/{id}/like")
    public ResponseEntity<List<TeacherVo>> findAllLike(@PathVariable String id, @AuthenticationPrincipal UserDetailsVo userDetailsVo) {
        log.debug(userDetailsVo.getUserVo().getId());
        String currentLoginId = userDetailsVo.getUserVo().getId();

        if (!currentLoginId.equals(id)) {
            log.debug("아이디가 다릅니다.");
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS);
        }

        List<TeacherVo> likeList = userService.findAllLike(id);
        return new ResponseEntity<>(likeList, HttpStatus.OK);
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{id}/like/{likeId}")
    public ResponseEntity<Void> deleteLike(@PathVariable String id, @PathVariable int likeId, @AuthenticationPrincipal UserDetailsVo userDetailsVo) {
        log.debug(userDetailsVo.getUserVo().getId());
        String currentLoginId = userDetailsVo.getUserVo().getId();

        if (!currentLoginId.equals(id)) {
            log.debug("아이디가 다릅니다.");
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS);
        }

        userService.deleteLike(likeId);
        for (TeacherVo teacherVo : userService.findAllLike(id)) {
            log.debug("삭제후 {} {}", teacherVo.getTeacherId(), teacherVo.getName());
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

//    유저 프로필이미지 추가
    @PostMapping("/{id}/profileImage")
    public ResponseEntity<Void> saveProfileImage(@PathVariable String id, @RequestPart(value = "image") MultipartFile multipartFile , @AuthenticationPrincipal UserDetailsVo userDetailsVo) {
        log.debug(userDetailsVo.getUserVo().getId());
        String currentLoginId = userDetailsVo.getUserVo().getId();

        if (!currentLoginId.equals(id)) {
            log.debug("아이디가 다릅니다.");
            throw new RestApiException(ErrorCode.FORBIDDEN_ACCESS);
        }

        userService.insertProfileImage(id , multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
