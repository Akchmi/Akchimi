package com.quokka.classmusic.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactsSelectAllDto {
    private String id;
//    0 대기 1 진행 2 완료 3 거절
    private int state;
//    0 학생 1 강사 2 관리자
    private int type;
}
