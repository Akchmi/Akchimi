package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.NoticeDto;
import com.quokka.classmusic.api.response.NoticeDetailVo;
import com.quokka.classmusic.api.response.NoticeListVo;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    List<NoticeListVo> selectAllNotice(Map<String, String> params);
    NoticeDetailVo selectNotice(int noticeId);
    int insertNotice(NoticeDto noticeInsertDto);
    void updateNotice(NoticeDto noticeDto, int noticeId);
    void deleteNotice(int noticeId);
}
