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
import java.util.List;

@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService{

    private NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public List<NoticeListVo> selectAllNotice(Integer pageNo, String keyword) {

        return null;
    }

    @Override
    public NoticeDetailVo selectNotice(int noticeId) {
        // 값이 없으면 NoSuchElementException이 발생한다!
        Notice notice = noticeRepository.findById(noticeId).get();
        NoticeDetailVo noticeDetailVo = new NoticeDetailVo(notice);
        return noticeDetailVo;
    }

    @Override
    public int insertNotice(NoticeDto noticeInsertDto) {
        Notice notice = Notice.builder().title(noticeInsertDto.getTitle()).content(noticeInsertDto.getContent()).hit(noticeInsertDto.getHit()).build();
        return noticeRepository.save(notice).getNoticeId();
    }

    @Override
    @Transactional
    public NoticeDetailVo updateNotice(NoticeDto noticeDto, int noticeId) {
        log.debug("NoticeService updateNotice 호출 noticeDto : {} noticeId : {}", noticeDto, noticeId);
        Notice notice = noticeRepository.findById(noticeId).get();
        notice.noticeUpdate(noticeDto.getTitle(), noticeDto.getContent());
        return null;
    }
}
