package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.NoticeDto;
import com.quokka.classmusic.api.response.NoticeDetailVo;
import com.quokka.classmusic.api.response.NoticeListVo;
import com.quokka.classmusic.db.entity.Notice;
import com.quokka.classmusic.db.repository.NoticeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional
@Slf4j
public class NoticeServiceImpl implements NoticeService{

    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public List<NoticeListVo> selectAllNotice(Map<String, String> params) {
        log.debug("NoticeService selectAllNotice 호출 params : {}", params);

        List<Notice> notices = noticeRepository.findAll(params);
        log.debug("notices : {}", notices);

        // notice entity list -> noticeListVo list
        List<NoticeListVo> noticeVoList = new ArrayList<>();
        for(Notice notice : notices){
            noticeVoList.add(new NoticeListVo(notice));
        }

        return noticeVoList;
    }

    @Override
    public NoticeDetailVo selectNotice(int noticeId) {
        log.debug("NoticeService selectNotice 호출 noticeId : {}", noticeId);

        Notice notice = noticeRepository.findNoticeById(noticeId);
        log.debug("notice : {}", notice);

        if(notice == null) {
            throw new NoSuchElementException(noticeId + "와 일치하는 notice가 없습니다");
        }

        NoticeDetailVo noticeDetailVo = new NoticeDetailVo(notice);
        return noticeDetailVo;
    }

    @Override
    public void deleteNotice(int noticeId) {
        log.debug("NoticeService deleteNotice noticeId : {}", noticeId);

        Notice notice = noticeRepository.findNoticeById(noticeId);
        noticeRepository.delete(notice);
    }

    @Override
    public int endPageNo(Map<String, String> params) {
        return noticeRepository.getEndPage(params);
    }

    @Override
    public int insertNotice(NoticeDto noticeInsertDto) {
        log.debug("NoticeService insertNotice 호출 noticeInsertDto : {}", noticeInsertDto);
        Notice notice = Notice.builder()
                .title(noticeInsertDto.getTitle())
                .content(noticeInsertDto.getContent())
                .build();

        noticeRepository.save(notice);
        log.debug("Inserted NoticeId : {}", notice.getNoticeId());

        return notice.getNoticeId();
    }

    @Override
    public void updateNotice(NoticeDto noticeDto, int noticeId) {
        log.debug("NoticeService updateNotice 호출 noticeDto : {} noticeId : {}", noticeDto, noticeId);
        Notice notice = noticeRepository.findNoticeById(noticeId);

        if(notice == null){
            throw new NoSuchElementException(noticeId + "와 일치하는 notice가 없습니다");
        }

        notice.noticeUpdate(noticeDto.getTitle(), noticeDto.getContent());
    }
}
