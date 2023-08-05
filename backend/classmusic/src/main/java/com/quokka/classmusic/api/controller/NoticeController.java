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

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notices")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<List<NoticeListVo>> selectAllNotice(@RequestParam Map<String, String> params){
        log.debug("GET /notices params : {}", params);

        List<NoticeListVo> result = noticeService.selectAllNotice(params);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/endPageNo")
    public ResponseEntity<Integer> endPageNo(@RequestParam Map<String, String> params){
        int endPageNo = noticeService.endPageNo(params);
        return new ResponseEntity<>(endPageNo, HttpStatus.OK);
    }


    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDetailVo> selectNoticeDetail(@PathVariable("noticeId") int noticeId){
        log.debug("GET /notices/:noticeId noticeId : {}", noticeId);
        NoticeDetailVo noticeDetailVo = noticeService.selectNotice(noticeId);

        return new ResponseEntity<>(noticeDetailVo, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> insertNotice(@RequestBody NoticeDto noticeInsertDto, @AuthenticationPrincipal UserDetailsVo userDetailsVo){
        log.debug("POST /notices");
        log.debug("공지사항 작성자 : {}", userDetailsVo);

        return new ResponseEntity<>(noticeService.insertNotice(noticeInsertDto), HttpStatus.CREATED);
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<Void> updateNotice(@PathVariable int noticeId, @RequestBody NoticeDto noticeDto){
        log.debug("PUT /notices/:noticeId noticeDto : {}", noticeDto);

        noticeService.updateNotice(noticeDto, noticeId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable int noticeId){
        log.debug("DELETE /notices/:noticeId noticeId : {}", noticeId);

        noticeService.deleteNotice(noticeId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
