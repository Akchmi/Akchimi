package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.response.EventVo;
import com.quokka.classmusic.api.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EventVo>> selectEvent(@PathVariable int userId){
        return new ResponseEntity<>(eventService.selectEvent(userId) , HttpStatus.OK);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int eventId){
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
