package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.response.EventVo;
import java.util.List;

public interface EventService {
    List<EventVo> selectEvent(int userId);
    void deleteEvent(int eventId);
}
