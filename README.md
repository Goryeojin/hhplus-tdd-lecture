# hhplus-tdd-lecture
🔥 [항플 2주차 과제] 특강 신청 서비스
### **`Default`**

- [X] 아키텍처 준수를 위한 애플리케이션 패키지 설계
- [X] 특강 도메인 테이블 설계 및 목록/신청 등 기본 기능 구현
- [X] 각 기능에 대한 **단위 테스트** 작성

> 사용자 회원가입/로그인 기능은 구현하지 않습니다.

### **`STEP 3`**

- [X] 설계한 테이블에 대한 **ERD** 및 이유를 설명하는 **README** 작성
- [X] 선착순 30명 이후의 신청자의 경우 실패하도록 개선
- [X] 동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공하는 것을 검증하는 **통합 테스트** 작성

### **`STEP 4`**

- [ ] 같은 사용자가 동일한 특강에 대해 신청 성공하지 못하도록 개선
- [ ] 동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공하는 것을 검증하는 **통합 테스트** 작성

## API Specs

1️⃣ **(핵심) 특강 신청 API**

- [X] 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
- [X] 동일한 신청자는 동일한 강의에 대해서 한 번의 수강 신청만 성공할 수 있습니다.
- [X] 특강은 선착순 30명만 신청 가능합니다.
- [X] 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.

2️⃣ **특강 선택 API** 

- [X] 날짜별로 현재 신청 가능한 특강 목록을 조회하는 API 를 작성합니다.
- [X] 특강의 정원은 30명으로 고정이며, 사용자는 각 특강에 신청하기전 목록을 조회해 볼 수 있어야 합니다.

3️⃣  **특강 신청 완료 목록 조회 API**

- [X] 특정 userId 로 신청 완료된 특강 목록을 조회하는 API 를 작성합니다.
- [X] 각 항목은 특강 ID 및 이름, 강연자 정보를 담고 있어야 합니다.

## 테이블 ERD 설계

![hhplus](https://github.com/user-attachments/assets/b63ffd38-448e-4389-b831-e24597cf0694)   

1. Lecture (특강 테이블)   
  - `ID`: 특강의 고유 ID로 PK 설정
  - `LECTURE_TITLE`: 특강 이름
  - `LECTURER`: 강사 이름
  - `LECTURE_DATE_TIME`: 특강 시간 (YYYY-MM-DD HH:mm:ss)

2. LECTURE_CAPACITY (특강 인원 정보 테이블)
  - `ID`: 특강 인원 정보 row 고유 ID로 PK 설정
  - `LECTURE_ID`: 참조할 특강의 ID로 FK 설정
  - `CAPACITY`: 수용할 수 있는 인원. Default: 30
  - `CURRENT_COUNT`: 현재 신청한 인원. Default: 0

3. REGISTRATION (신청 목록 테이블)
  - `ID`: 신청 목록 테이블 row 고유 ID로 PK 설정
  - `LECTURE_ID`: 참조할 특강의 ID로 FK 설정
  - `REGISTRATION_DATE_TIME`: 신청 시간 (YYYY-MM-DD HH:mm:ss)
  - `STUDENT_ID`: 신청한 학생의 ID

### LECTURE와 LECTURE_CAPACITY
    하나의 특강은 하나의 특강 인원 정보를 가질 수 있다. 
    LECTURE_CAPACITY 테이블의 LECTURE_ID는 LECTURE 테이블의 PK를 참조하는 외래키로 설정.

### LECTURE와 REGISTRATION
    하나의 특강에 여러 명의 학생들이 신청할 수 있다.
    REGISTRATION 테이블의 LECTURE_ID는 LECTURE 테이블의 PK를 참조하는 외래키로 설정.
