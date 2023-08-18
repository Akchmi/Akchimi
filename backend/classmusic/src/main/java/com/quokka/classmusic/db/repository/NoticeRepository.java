package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NoticeRepository {
    List<Notice> findAll(Map<String, String> params);
    Notice findNoticeById(int noticeId);
    void save(Notice notice);
    void delete(Notice notice);
    int getEndPage(Map<String, String> params);
}
