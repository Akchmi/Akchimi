package com.quokka.classmusic.db.repository;

import com.quokka.classmusic.db.entity.Event;

import java.util.List;
import java.util.Map;

public interface EventRepository {
    List<Event> findAll(int userId);
    void save(Event event);
    Event findById(int eventId);
    void delete(Event event);
}
