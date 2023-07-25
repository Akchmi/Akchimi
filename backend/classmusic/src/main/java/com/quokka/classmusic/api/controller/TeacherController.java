package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.ReviewInsertDto;
import com.quokka.classmusic.api.request.TeacherDto;
import com.quokka.classmusic.api.response.TeacherVo;
import com.quokka.classmusic.api.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/teachers")
@CrossOrigin("*")
@Slf4j
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @GetMapping
    public ResponseEntity<List<TeacherVo>> selectAllTeacher(@RequestParam Map<String, Objects> params){
        try {
            return ResponseEntity.status(200).body(teacherService.selectAllTeacher(params));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> insertTeacher(@RequestBody TeacherDto teacherDto){
        try {
            return ResponseEntity.status(200).body(teacherService.insertTeacher(teacherDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<Integer> updateTeacher(@PathVariable int teacherId , @RequestBody TeacherDto teacherDto){
        try {
            teacherService.updateTeacher(teacherId , teacherDto);
            return ResponseEntity.status(200).body(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
