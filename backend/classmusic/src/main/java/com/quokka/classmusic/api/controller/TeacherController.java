package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.TeacherDto;
import com.quokka.classmusic.api.response.TeacherDetailVo;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers")
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @GetMapping
    public ResponseEntity<List<TeacherVo>> selectAllTeacher(@RequestParam Map<String, String> params){

        return new ResponseEntity<>(teacherService.selectAllTeacher(params), HttpStatus.OK);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<TeacherDetailVo> selectDetailTeacher(@PathVariable int teacherId , @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        return new ResponseEntity<>(teacherService.selectDetailTeacher(teacherId , userDetailsVo), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Integer> insertTeacher(@RequestBody TeacherDto teacherDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        int userId = userDetailsVo.getUserVo().getUserId();
        teacherDto.setUserId(userId);

        return new ResponseEntity<>(teacherService.insertTeacher(teacherDto), HttpStatus.CREATED);
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Integer> updateTeacher(@PathVariable int teacherId , @RequestBody TeacherDto teacherDto){
        teacherService.updateTeacher(teacherId , teacherDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable int teacherId){
        teacherService.deleteTeacher(teacherId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/{teacherId}/images")
    public ResponseEntity<Void> insertImage(@PathVariable int teacherId , @RequestPart(value = "image") List<MultipartFile> multipartFiles){
        teacherService.insertImage(teacherId , multipartFiles);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{teacherId}/images")
    public ResponseEntity<Void> deleteImage(@PathVariable int teacherId , @RequestPart(value = "image") List<String> files){
        teacherService.deleteImage(teacherId , files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
