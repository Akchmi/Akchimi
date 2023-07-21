package com.quokka.classmusic;

import static org.junit.jupiter.api.Assertions.*;

import com.quokka.classmusic.api.controller.NoticeController;
import com.quokka.classmusic.api.service.NoticeService;
import com.quokka.classmusic.db.entity.Notice;
import com.quokka.classmusic.db.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class NoticeTests {

    private NoticeService noticeService;
    private NoticeController noticeController;
    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeTests(NoticeService noticeService, NoticeController noticeController, NoticeRepository noticeRepository) {
        this.noticeService = noticeService;
        this.noticeController = noticeController;
        this.noticeRepository = noticeRepository;
    }

    @Test
    public void beanTest(){
        assertNotNull(noticeService);
        assertNotNull(noticeController);
        assertNotNull(noticeRepository);
    }

    // Repository Test
    @Test
    @Transactional
    public void repositoryInsertTest(){
        Notice sample = Notice.builder()
                        .title("제목입니다").content("내용입니다.").hit(0).build();

        Notice result = noticeRepository.save(sample);

        log.trace(result.toString());
    }

    // Service Test
    @Test
    @Transactional
    public void serviceUpdateTest(){
        int noticeId = 1;
        String title = "수정된 제목입니다.";
        String content = "수정된 내용입니다.";
        
    }
}
