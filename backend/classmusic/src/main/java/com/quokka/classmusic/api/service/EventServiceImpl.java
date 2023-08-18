package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.response.EventVo;
import com.quokka.classmusic.db.entity.Event;
import com.quokka.classmusic.db.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<EventVo> selectEvent(int userId) {
        List<Event> list = eventRepository.findAll(userId);
        List<EventVo> events = new ArrayList<>();
        for (Event event : list) {
            events.add(new EventVo(event.getEventId(),
                    event.getType(),
                    event.getMessage(),
                    event.getTime()));
        }
        return events;
    }

    @Override
    public void deleteEvent(int eventId) {
        Event event = eventRepository.findById(eventId);
        eventRepository.delete(event);
    }
}
