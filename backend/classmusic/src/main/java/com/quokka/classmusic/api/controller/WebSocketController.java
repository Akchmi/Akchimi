package com.quokka.classmusic.api.controller;

import com.quokka.classmusic.api.response.SocketVO;

import com.quokka.classmusic.api.service.ChatService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller

public class WebSocketController {

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")
    @SendTo("/send")
    public SocketVO SocketHandler(SocketVO socketVO) {

        String sender =socketVO.getSender();
        int roomId = socketVO.getRoomId();
        String content= socketVO.getContent();

        SocketVO result = new SocketVO(sender,roomId,content );
        // 반환
        return result;
    }
}
