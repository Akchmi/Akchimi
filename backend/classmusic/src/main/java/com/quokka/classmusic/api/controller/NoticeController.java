package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.request.NoticeDto;
import com.quokka.classmusic.api.response.NoticeDetailVo;
import com.quokka.classmusic.api.response.NoticeListVo;
import com.quokka.classmusic.api.response.UserDetailsVo;
import com.quokka.classmusic.api.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notices")
@Slf4j
public class NoticeController {
    private NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<NoticeListVo> selectAllNotice(@RequestParam int pageNo, @RequestParam String keyword){
        log.debug("GET /notices pageNo : {} keyword : {}", pageNo, keyword);

        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDetailVo> selectNoticeDetail(@PathVariable("noticeId") int noticeId){
        NoticeDetailVo noticeDetailVo = noticeService.selectNotice(noticeId);
        return new ResponseEntity<>(noticeDetailVo, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<Integer> insertNotice(@RequestBody NoticeDto noticeInsertDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        log.debug("POST /notices");
        log.debug("공지사항 작성자 : {}", userDetailsVo);

        return new ResponseEntity<>(noticeService.insertNotice(noticeInsertDto), HttpStatus.ACCEPTED);
    }

//    @PutMapping
//    public ResponseEntity<NoticeDetailVo> updateNotice(@RequestBody){
//
//        return new ResponseEntity<>()
//    }


}
