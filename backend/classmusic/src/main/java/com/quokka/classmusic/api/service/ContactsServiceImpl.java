package com.quokka.classmusic.api.service;

import com.quokka.classmusic.api.request.*;
import com.quokka.classmusic.api.response.ContactsVo;
import com.quokka.classmusic.common.exception.ErrorCode;
import com.quokka.classmusic.common.exception.RestApiException;
import com.quokka.classmusic.db.entity.Contact;
import com.quokka.classmusic.db.entity.Event;
import com.quokka.classmusic.db.entity.Teacher;
import com.quokka.classmusic.db.entity.User;
import com.quokka.classmusic.db.repository.ContactsRepository;
import com.quokka.classmusic.db.repository.EventRepository;
import com.quokka.classmusic.db.repository.TeacherRepository;
import com.quokka.classmusic.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class ContactsServiceImpl implements ContactsService{
    private final ContactsRepository contactsRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ContactsServiceImpl(ContactsRepository contactsRepository, UserRepository userRepository, TeacherRepository teacherRepository, EventRepository eventRepository) {
        this.contactsRepository = contactsRepository;
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.eventRepository = eventRepository;
    }

    //    내 매칭 보기
    @Override
    public List<ContactsVo> selectAllContacts(Map<String,Integer> params){
        return contactsRepository.findAll(params);
    }
//    매칭 삭제
    @Override
    public void deleteContacts(int contactId){
        Contact contact = contactsRepository.findById(contactId);
        contactsRepository.delete(contact);
    }
//    매칭 상태 바꾸기
    @Override
    public void updateContactsState(int contactId , ContactsUpdateStateDto contactsUpdateStateDto) {
        Contact contact = contactsRepository.findById(contactId);
        int state = contactsUpdateStateDto.getState();
//        수락 시 매칭 수 증가 > 시간 넣어줘야됨
        Teacher teacher = contact.getTeacher();
        User student = contact.getStudent();
        if(state == 1){
            teacher.setContactCnt(teacher.getContactCnt() + 1);
            contact.setStartTime((int) (System.currentTimeMillis() / 1000));

            int leftLimit = 65;
            int rightLimit = 90;
            int targetStringLength = 16;
            Random random = new Random();
            String roomKey = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            contact.setRoomKey(roomKey);
            eventRepository.save(new Event(student , 1 , teacher.getUser().getName() + " 강사가 강의를 수락했습니다."));

        } else if(state == 2){
            contact.setEndTime((int) (System.currentTimeMillis() / 1000));
            eventRepository.save(new Event(teacher.getUser() , 6 , student.getName() + " 학생과의 강의가 완료되었습니다."));
            eventRepository.save(new Event(student , 3 , teacher.getUser().getName() + " 강사와의 강의가 완료되었습니다."));
        } else if(state == 3){
            eventRepository.save(new Event(student , 2 , teacher.getUser().getName() + " 강사가 강의를 거절했습니다."));
        }
        contact.setState(state);
        contactsRepository.save(contact);
    }
//    매칭 메모 바꾸기
    @Override
    public void updateContactsMemo(int contactId , ContactsUpdateMemoDto contactsUpdateMemoDto) {
        Contact contact = contactsRepository.findById(contactId);
        if(contactsUpdateMemoDto.getType() == 0){
            contact.setStudentMemo(contactsUpdateMemoDto.getMemo());
        } else {
            contact.setTeacherMemo(contactsUpdateMemoDto.getMemo());
        }
        contactsRepository.save(contact);
    }
//    매칭 순서 바꾸기
    @Override
    public void updateContactsOrder(ContactsUpdateOrderListDto contactsUpdateOrderListDto){
        int type = contactsUpdateOrderListDto.getType();
        if(type == 0){
            for (ContactsUpdateOrderDto contactsUpdateOrderDto : contactsUpdateOrderListDto.getContacts()) {
                Contact contact = contactsRepository.findById(contactsUpdateOrderDto.getContactId());
                contact.setStudentOrder(contactsUpdateOrderDto.getOrder());
                contactsRepository.save(contact);
            }
        } else if(type == 1){
            for (ContactsUpdateOrderDto contactsUpdateOrderDto : contactsUpdateOrderListDto.getContacts()) {
                Contact contact = contactsRepository.findById(contactsUpdateOrderDto.getContactId());
                contact.setTeacherOrder(contactsUpdateOrderDto.getOrder());
                contactsRepository.save(contact);
            }
        }
    }
//    매칭 생성하기
    @Override
    public int insertContacts(ContactsInsertDto contactsInsertDto) {
        if(contactsRepository.findByPair(contactsInsertDto.getStudentId() , contactsInsertDto.getTeacherId()) != null){
            throw new RestApiException(ErrorCode.CONTACT_DUPLICATED);
        }

        User student = userRepository.findById(contactsInsertDto.getStudentId());
        Teacher teacher = teacherRepository.findById(contactsInsertDto.getTeacherId());

        Contact contact = Contact.builder()
                .student(student)
                .teacher(teacher)
                .state(0)
                .studentOrder(contactsRepository.maxOrder(contactsInsertDto.getStudentId() ,0,0) + 1)
                .teacherOrder(contactsRepository.maxOrder(contactsInsertDto.getTeacherId() ,0,1) + 1)
                .build();

        eventRepository.save(new Event(teacher.getUser() , 5 , student.getName() + " 학생에게 강의 신청이 왔습니다."));

        contactsRepository.save(contact);
        return contact.getContactId();
    }
//    강의실 입장
    @Override
    public String selectContactsRoom(int contactId){
        return contactsRepository.findById(contactId).getRoomKey();
    }
}
