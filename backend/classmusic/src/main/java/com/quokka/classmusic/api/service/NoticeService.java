package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.NoticeDto;
import com.quokka.classmusic.api.response.NoticeDetailVo;
import com.quokka.classmusic.api.response.NoticeListVo;

import java.util.List;

public interface NoticeService {
    List<NoticeListVo> selectAllNotice(Integer pageNo, String keyword);
    NoticeDetailVo selectNotice(int noticeId);
    int insertNotice(NoticeDto noticeInsertDto);
    NoticeDetailVo updateNotice(NoticeDto noticeDto, int noticeId);
}
