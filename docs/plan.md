# 요구사항 정의

# 회원 관련 기능

- 회원가입

1. 일반 회원가입 정보
   - 아이디(필수)
   - 패스워드(필수)
   - 이름(필수)
   - 사진(선택)
   - 이메일(필수)
   - (휴대전화번호)
   2. 마이페이지에서 강사 프로필 등록
   - 강사 자기소개(필수)
   - 할 수 있는 악기(필수)
   - 수업 가능 시간(필수)
   - 경력(선택)
   - 첨부파일(선택)
   - 리뷰 (강사평)

- 로그인
- 로그아웃
- 회원정보 조회 (마이페이지)
  - 강사 프로필 등록 버튼
  - 페이지내 탭구분
    - 내 강의 보기
    - 내 정보 보기
    - 내가 쓴 글, 댓글 보기
    - 내가 스크랩 한 강사
- 회원 정보 수정

### 일반 회원 기능

수업 신청

### 강사 프로필 등록 시

수업 등록

# 게시판 관련 기능

## 공지사항 게시판

- 관리자/스텝만 작성 가능
- 댓글 기능 없음

## 자유 게시판

- 장비 공유
- 방송 세팅하는 법
- 노하우
- 댓글, 좋아요

# 화상 강의 관련 기능

- 악보
- 메트로놈
- 녹음, 녹화
- 즉석 다시보기
- 채팅
- 전자 악기 연결

## 연습실

- 악보
- 점수
- 거울 모드
- 연습 시간 기록
- 일주일 전에 너는

## 강의 신청 프로세스

강사검색(메인화면) → 강의신청(강사 상세정보 - 원하는시간대 선택) → 강사 수락 → 강의성사

## 강의 시작 프로세스

내강의(마이페이지) → 강사가 방생성 → 방생성시 학생한테 링크 전송 → 학생 접속 → 강의종료시 학생퇴실, 강사가 나가면 방 터지기

# 화면 설계서

## 피그마 링크

[https://www.figma.com/file/cjQFMWzXR778Efclx6NkDX/TEAM-%EC%BF%BC%EC%B9%B4?type=design&node-id=83-2&mode=design&t=1pg1SovPQykyjaNi-0](https://www.figma.com/file/cjQFMWzXR778Efclx6NkDX/TEAM-%EC%BF%BC%EC%B9%B4?type=design&node-id=83-2&mode=design&t=1pg1SovPQykyjaNi-0)

# 메인

- 네비게이션 바 → 악취미 로고, 공지사항, 자유게시판, 내 강의, 내 정보(마이페이지, 강사페이지, 쪽지함, 로그아웃)
- 악기 검색 바 / 아이콘

# HEADER

- 공지사항
- 자유게시판
- 내 강의
- 강사 리스트
- 마이페이지
- 로그인 / 로그아웃
- 회원가입

# 공지사항 - 관리자만 작성 가능

### 공지사항 목록

- 게시글 목록 - 페이지
- 게시글 : 번호, 제목, 작성자, 작성일, 조회수

### 공지사항 상세

- 제목
- 작성자
- 내용
- 첨부파일
- 조회수
- 작성일
- 수정, 삭제 버튼

### 공지사항 작성 / 수정

- 입력폼 : 제목, 내용 작성자
- 파일첨부

# 자유게시판

### 게시글 목록

- 게시글 목록 - 페이지
- 게시글 : 번호, 제목, 작성자, 작성일, 조회수
- 검색 - 전체(DEFAULT), 제목, 작성자, 내용
- 정렬 - 최신순, 조회수순

### 게시글 상세

- 제목
- 작성자
- 내용
- 첨부
- 조회수
- 작성일
- 수정, 삭제 버튼
- 댓글 - 파일업로드 X
  - 내용
  - 작성자
  - 생성일
  - 수정, 삭제 버튼
- 댓글 쓰는 창
  - 내용, 작성버튼

### 게시글 작성 / 수정

- 입력폼 : 제목, 내용, 작성자
- 파일첨부

# 마이페이지

### 내 정보

- 프로필사진(수정 가능)
- 아이디(변경 불가)
- 이메일(변경 불가)
- 이름(수정 가능)
- 비밀번호 변경 - 기존 비밀번호, 새로운 비밀번호, 새로운 비밀번호 확인
- 회원 탈퇴하기

### 내 글

- 쓴 글 목록
- 클릭 시 해당 글로 이동

### 강사 즐겨찾기

- 즐겨찾기한 강사 목록
- 즐겨찾기 해제 버튼
- 클릭 시 해당 강사 프로필로 이동
- 순서 수정

# 강사프로필

- 이름 (기존 닉네임과 동일 체크 가능)
- 프로필사진 (기존 프로필 사진과 동일 체크 가능)
- 가르치는 악기
- 자기 소개
- 가능 시간
- 경력
- 비용
- 별점
- 성별

# 쪽지 (채팅UI) 페이지 (학생, 강사)

- 국룰 채팅 UI (채팅내용, 채팅입력창)

# 강사 목록(검색결과 페이지)

- 검색하기
- 필터 - 악기, 성별, 비용, 별점
- 강사 정보
  - 이름
  - 프로필
  - 악기 종류
  - 가격범위
  - 성별

# 내 강의

- 수락 대기
  - 상대 이름
  - 상대 프로필 사진
  - 수락하기 버튼
  - 거절하기 버튼
  - 채팅(쪽지) 버튼
- 진행 중
  - 상대 이름
  - 상대 프로필 사진
  - 방 생성하기(강사) 버튼
  - 방 입장하기(학생) 버튼
  - 채팅 버튼
  - 삭제하기 버튼
- 완료
  - 상대 이름
  - 상대 프로필 사진
  -

# 회원가입

- 아이디
- 이메일
- 이메일 인증(?)
- 이름
- 비밀번호
- 비밀번호 확인

# 로그인

- 아이디
- 비밀번호
- 비밀번호 찾기
- 회원가입
- 소셜 로그인 (카카오, 네이버, 페이스북, 구글) - 선택
- QR코드(누군가 원하면 언젠가 해봅시다) - 한샘
- 아이디 저장(원하면 언젠가 누군가 해봅시다)
- 로그인 상태 유지(언젠가 원하면 누군가 해봅시다)

## 아이디 찾기

이름하고 이메일 입력 시 이메일로 아이디 전송

## 비밀번호 초기화

이름하고 아이디, 연결된 이메일로 임시 비밀번호 통보

# 화상 강의실

- 음질표시, 설정
- 채팅
- 메트로놈
- 녹화
- 카운트다운
- 악보