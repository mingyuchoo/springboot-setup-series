# [REQ-N006] 사용자 테스트 시나리오 문서

## 1. 개요

### 목적

코드베이스를 분석하여 **사용자 관점의 E2E 테스트 시나리오**를 체계적으로 도출한 문서입니다.

### 추출 방법론

| 소스 | 추출 대상 | 파일 |
|------|----------|------|
| 웹 컨트롤러 | 사용자 동작(Action) 흐름 | `AuthWebController.java`, `PostWebController.java` |
| DTO 검증 규칙 | 입력 유효성 시나리오 | `SignupRequestDto.java`, `PostRequestDto.java`, `CommentRequestDto.java` |
| Service 비즈니스 로직 | 예외/에러 시나리오 | `UserServiceImpl.java`, `PostServiceImpl.java`, `CommentServiceImpl.java` |
| SecurityConfig | 보안/인증 시나리오 | `SecurityConfig.java` |

### 시나리오 분류 체계

- **TC-AUTH**: 인증 (회원가입, 로그인, 로그아웃)
- **TC-POST**: 포스트 관리 (CRUD)
- **TC-CMT**: 댓글 관리 (생성, 삭제)
- **TC-SEC**: 권한/보안
- **TC-VAL**: 입력 유효성 검증

---

## 2. 사전 조건

### 테스트 환경

- 애플리케이션 URL: `http://localhost:8080`
- 데이터베이스: H2 인메모리 (애플리케이션 재시작 시 초기화)

### 샘플 계정 (DataInitializer에 의해 자동 생성)

| 사용자명 | 비밀번호 | 이메일 |
|---------|---------|-------|
| user1 ~ user100 | password | <user1@example.com> ~ <user100@example.com> |

### 샘플 데이터

- 포스트: 100개 (랜덤 작성자 배정)
- 댓글: 100개 (랜덤 작성자·포스트 배정)

---

## 3. 테스트 시나리오

### 3.1 인증 (TC-AUTH)

#### TC-AUTH-001: 회원가입 - 정상 처리

- **카테고리**: 정상 경로
- **사전 조건**: 비로그인 상태
- **테스트 단계**:
  1. `http://localhost:8080/auth/register` 접속
  2. 사용자명: `newuser`, 비밀번호: `pass1234`, 이메일: `newuser@test.com` 입력
  3. "회원가입" 버튼 클릭
- **기대 결과**:
  - 로그인 페이지(`/auth/login`)로 리다이렉트
  - "회원가입이 완료되었습니다. 로그인해주세요." 메시지 표시
- **코드 근거**: `AuthWebController.java:37-53`, `UserServiceImpl.java:33-47`

#### TC-AUTH-002: 로그인 - 정상 처리

- **카테고리**: 정상 경로
- **사전 조건**: user1 계정이 존재
- **테스트 단계**:
  1. `http://localhost:8080/auth/login` 접속
  2. 사용자명: `user1`, 비밀번호: `password` 입력
  3. "로그인" 버튼 클릭
- **기대 결과**:
  - 포스트 목록 페이지(`/posts`)로 리다이렉트
- **코드 근거**: `SecurityConfig.java:50-53` (`formLogin`, `defaultSuccessUrl("/posts", true)`)

#### TC-AUTH-003: 로그인 - 잘못된 비밀번호

- **카테고리**: 예외 경로
- **사전 조건**: user1 계정이 존재
- **테스트 단계**:
  1. `http://localhost:8080/auth/login` 접속
  2. 사용자명: `user1`, 비밀번호: `wrongpassword` 입력
  3. "로그인" 버튼 클릭
- **기대 결과**:
  - 로그인 페이지에 에러 메시지 표시
  - 로그인되지 않음
- **코드 근거**: `SecurityConfig.java:50-53` (Spring Security 폼 로그인 기본 동작)

#### TC-AUTH-004: 로그인 - 존재하지 않는 사용자

- **카테고리**: 예외 경로
- **사전 조건**: 없음
- **테스트 단계**:
  1. `http://localhost:8080/auth/login` 접속
  2. 사용자명: `nonexistent`, 비밀번호: `password` 입력
  3. "로그인" 버튼 클릭
- **기대 결과**:
  - 로그인 페이지에 에러 메시지 표시
- **코드 근거**: `UserServiceImpl.java:50-52` (`UsernameNotFoundException`)

#### TC-AUTH-005: 로그아웃

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인된 상태
- **테스트 단계**:
  1. 네비게이션의 "로그아웃" 버튼 클릭
- **기대 결과**:
  - 로그인 페이지(`/auth/login?logout`)로 리다이렉트
  - 세션 종료
- **코드 근거**: `SecurityConfig.java:55-58` (`logoutUrl`, `logoutSuccessUrl`)

---

### 3.2 포스트 관리 (TC-POST)

#### TC-POST-001: 포스트 목록 조회

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인, 포스트가 10개 이상 존재
- **테스트 단계**:
  1. `http://localhost:8080/posts` 접속
- **기대 결과**:
  - 최신순으로 정렬된 포스트 목록 표시
  - 기본 10개씩 페이징 처리
  - 각 포스트에 제목, 작성자, 댓글 수, 작성일시 표시
  - 페이지 네비게이션 표시
- **코드 근거**: `PostWebController.java:33-38` (`@PageableDefault(size = 10)`)

#### TC-POST-002: 포스트 목록 페이징

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인, 포스트 100개 존재
- **테스트 단계**:
  1. 포스트 목록 페이지에서 "다음 페이지" 클릭
- **기대 결과**:
  - 11~20번째 포스트가 표시됨
  - URL에 `?page=1` 파라미터 포함
- **코드 근거**: `PostWebController.java:34` (`Pageable pageable`)

#### TC-POST-003: 포스트 상세 조회

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인, 포스트 ID 1이 존재
- **테스트 단계**:
  1. 포스트 목록에서 포스트 제목 클릭 (또는 `/posts/1` 접속)
- **기대 결과**:
  - 포스트 제목, 내용, 작성자, 작성일시, 수정일시 표시
  - 해당 포스트의 댓글 목록 (생성일시 오름차순)
  - 댓글 작성 폼 표시
  - 본인 작성 포스트인 경우: 수정/삭제 버튼 표시
  - 타인 작성 포스트인 경우: 수정/삭제 버튼 미표시
- **코드 근거**: `PostWebController.java:40-49` (`currentUsername` 모델 속성)

#### TC-POST-004: 포스트 작성

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인
- **테스트 단계**:
  1. "새 포스트" 버튼 클릭 (`/posts/new`)
  2. 제목: `테스트 포스트`, 내용: `테스트 내용입니다.` 입력
  3. "저장" 버튼 클릭
- **기대 결과**:
  - 생성된 포스트 상세 페이지로 리다이렉트
  - "포스트가 작성되었습니다." 메시지 표시
  - 작성자가 user1으로 표시
- **코드 근거**: `PostWebController.java:57-69`

#### TC-POST-005: 포스트 수정 (본인)

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인, user1이 작성한 포스트 존재
- **테스트 단계**:
  1. 본인이 작성한 포스트 상세 페이지 접속
  2. "수정" 버튼 클릭 (`/posts/{id}/edit`)
  3. 제목을 `수정된 제목`으로 변경
  4. "저장" 버튼 클릭
- **기대 결과**:
  - 수정된 포스트 상세 페이지로 리다이렉트
  - "포스트가 수정되었습니다." 메시지 표시
  - 변경된 제목 표시, 수정일시 갱신
- **코드 근거**: `PostWebController.java:71-96`, `PostServiceImpl.java:56-66`

#### TC-POST-006: 포스트 삭제 (본인)

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인, user1이 작성한 포스트 존재
- **테스트 단계**:
  1. 본인이 작성한 포스트 상세 페이지 접속
  2. "삭제" 버튼 클릭
- **기대 결과**:
  - 포스트 목록 페이지(`/posts`)로 리다이렉트
  - "포스트가 삭제되었습니다." 메시지 표시
  - 삭제된 포스트가 목록에서 제거됨
  - 해당 포스트의 댓글도 함께 삭제됨 (cascade)
- **코드 근거**: `PostWebController.java:98-105`, `PostServiceImpl.java:70-79`

#### TC-POST-007: 존재하지 않는 포스트 접근

- **카테고리**: 예외 경로
- **사전 조건**: user1으로 로그인
- **테스트 단계**:
  1. `http://localhost:8080/posts/99999` 접속
- **기대 결과**:
  - 에러 페이지 표시 (404 Not Found)
  - "포스트를 찾을 수 없습니다." 메시지
- **코드 근거**: `PostServiceImpl.java:38-39` (`BusinessException`, `HttpStatus.NOT_FOUND`)

---

### 3.3 댓글 관리 (TC-CMT)

#### TC-CMT-001: 댓글 작성

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인, 포스트 상세 페이지
- **테스트 단계**:
  1. 포스트 상세 페이지 하단의 댓글 입력란에 `좋은 글이네요!` 입력
  2. "댓글 작성" 버튼 클릭
- **기대 결과**:
  - 같은 포스트 상세 페이지로 리다이렉트
  - 댓글 목록에 새 댓글이 추가됨 (작성자: user1)
  - 댓글이 생성일시 오름차순으로 정렬됨
- **코드 근거**: `PostWebController.java:107-117`, `CommentServiceImpl.java:45-55`

#### TC-CMT-002: 댓글 삭제 (본인)

- **카테고리**: 정상 경로
- **사전 조건**: user1으로 로그인, user1이 작성한 댓글 존재
- **테스트 단계**:
  1. 포스트 상세 페이지에서 본인 댓글의 "삭제" 버튼 클릭
- **기대 결과**:
  - 같은 포스트 상세 페이지로 리다이렉트
  - 삭제된 댓글이 목록에서 제거됨
- **코드 근거**: `PostWebController.java:119-125`, `CommentServiceImpl.java:73-82`

---

### 3.4 권한/보안 (TC-SEC)

#### TC-SEC-001: 비로그인 사용자의 포스트 목록 접근

- **카테고리**: 보안
- **사전 조건**: 비로그인 상태
- **테스트 단계**:
  1. `http://localhost:8080/posts` 직접 접속
- **기대 결과**:
  - 로그인 페이지(`/auth/login`)로 자동 리다이렉트
- **코드 근거**: `SecurityConfig.java:48` (`anyRequest().authenticated()`)

#### TC-SEC-002: 비로그인 사용자의 포스트 상세 접근

- **카테고리**: 보안
- **사전 조건**: 비로그인 상태
- **테스트 단계**:
  1. `http://localhost:8080/posts/1` 직접 접속
- **기대 결과**:
  - 로그인 페이지로 자동 리다이렉트
- **코드 근거**: `SecurityConfig.java:48`

#### TC-SEC-003: 타인 포스트 수정 시도

- **카테고리**: 보안
- **사전 조건**: user1으로 로그인, user2가 작성한 포스트 존재
- **테스트 단계**:
  1. user2가 작성한 포스트의 수정 페이지 접근 시도 (`/posts/{id}/edit`)
  2. 수정 내용 입력 후 제출
- **기대 결과**:
  - "본인의 포스트만 수정할 수 있습니다." 에러 메시지 (403 Forbidden)
  - 포스트 내용 변경되지 않음
- **코드 근거**: `PostServiceImpl.java:60-61`

#### TC-SEC-004: 타인 포스트 삭제 시도

- **카테고리**: 보안
- **사전 조건**: user1으로 로그인, user2가 작성한 포스트 존재
- **테스트 단계**:
  1. user2가 작성한 포스트의 삭제 요청 직접 전송 (`POST /posts/{id}/delete`)
- **기대 결과**:
  - "본인의 포스트만 삭제할 수 있습니다." 에러 메시지 (403 Forbidden)
  - 포스트 삭제되지 않음
- **코드 근거**: `PostServiceImpl.java:74-75`

#### TC-SEC-005: 타인 댓글 삭제 시도

- **카테고리**: 보안
- **사전 조건**: user1으로 로그인, user2가 작성한 댓글 존재
- **테스트 단계**:
  1. user2가 작성한 댓글의 삭제 요청 직접 전송
- **기대 결과**:
  - "본인의 댓글만 삭제할 수 있습니다." 에러 메시지 (403 Forbidden)
  - 댓글 삭제되지 않음
- **코드 근거**: `CommentServiceImpl.java:76-77`

#### TC-SEC-006: 인증 페이지 비로그인 접근 허용

- **카테고리**: 보안
- **사전 조건**: 비로그인 상태
- **테스트 단계**:
  1. `http://localhost:8080/auth/login` 접속
  2. `http://localhost:8080/auth/register` 접속
- **기대 결과**:
  - 리다이렉트 없이 각 페이지 정상 표시
- **코드 근거**: `SecurityConfig.java:47` (`requestMatchers("/auth/**").permitAll()`)

#### TC-SEC-007: 로그인 후 보호 페이지 세션 유지

- **카테고리**: 보안
- **사전 조건**: user1으로 로그인
- **테스트 단계**:
  1. 포스트 목록 접속
  2. 포스트 상세 접속
  3. 새 포스트 작성 페이지 접속
- **기대 결과**:
  - 모든 페이지에서 재로그인 없이 정상 접근
  - 네비게이션에 현재 사용자 정보 표시
- **코드 근거**: `SecurityConfig.java:44-63` (웹 보안 기본 세션 관리)

---

### 3.5 입력 유효성 검증 (TC-VAL)

#### TC-VAL-001: 회원가입 - 빈 사용자명

- **카테고리**: 유효성 검증
- **사전 조건**: 회원가입 페이지
- **테스트 단계**:
  1. 사용자명을 비워두고 나머지 필드 정상 입력
  2. "회원가입" 버튼 클릭
- **기대 결과**:
  - 회원가입 페이지 유지
  - "사용자명을 입력해주세요." 에러 메시지 표시
- **코드 근거**: `SignupRequestDto.java:10` (`@NotBlank(message = "사용자명을 입력해주세요.")`)

#### TC-VAL-002: 회원가입 - 사용자명 길이 위반 (2자)

- **카테고리**: 유효성 검증
- **사전 조건**: 회원가입 페이지
- **테스트 단계**:
  1. 사용자명: `ab` (2자), 나머지 정상 입력
  2. "회원가입" 버튼 클릭
- **기대 결과**:
  - 회원가입 페이지 유지
  - "사용자명은 3~50자 이내로 입력해주세요." 에러 메시지
- **코드 근거**: `SignupRequestDto.java:11` (`@Size(min = 3, max = 50)`)

#### TC-VAL-003: 회원가입 - 비밀번호 길이 위반 (3자)

- **카테고리**: 유효성 검증
- **사전 조건**: 회원가입 페이지
- **테스트 단계**:
  1. 비밀번호: `abc` (3자), 나머지 정상 입력
  2. "회원가입" 버튼 클릭
- **기대 결과**:
  - "비밀번호는 4자 이상 입력해주세요." 에러 메시지
- **코드 근거**: `SignupRequestDto.java:15` (`@Size(min = 4, max = 100)`)

#### TC-VAL-004: 회원가입 - 잘못된 이메일 형식

- **카테고리**: 유효성 검증
- **사전 조건**: 회원가입 페이지
- **테스트 단계**:
  1. 이메일: `not-an-email`, 나머지 정상 입력
  2. "회원가입" 버튼 클릭
- **기대 결과**:
  - "올바른 이메일 형식을 입력해주세요." 에러 메시지
- **코드 근거**: `SignupRequestDto.java:19` (`@Email(message = "올바른 이메일 형식을 입력해주세요.")`)

#### TC-VAL-005: 회원가입 - 중복 사용자명

- **카테고리**: 유효성 검증 (비즈니스)
- **사전 조건**: user1 계정이 이미 존재
- **테스트 단계**:
  1. 사용자명: `user1`, 비밀번호: `password`, 이메일: `new@test.com` 입력
  2. "회원가입" 버튼 클릭
- **기대 결과**:
  - 회원가입 페이지 유지
  - "이미 사용 중인 사용자명입니다." 에러 메시지
- **코드 근거**: `UserServiceImpl.java:34-35`

#### TC-VAL-006: 회원가입 - 중복 이메일

- **카테고리**: 유효성 검증 (비즈니스)
- **사전 조건**: <user1@example.com> 이메일이 이미 존재
- **테스트 단계**:
  1. 사용자명: `uniqueuser`, 비밀번호: `password`, 이메일: `user1@example.com` 입력
  2. "회원가입" 버튼 클릭
- **기대 결과**:
  - 회원가입 페이지 유지
  - "이미 사용 중인 이메일입니다." 에러 메시지
- **코드 근거**: `UserServiceImpl.java:37-38`

#### TC-VAL-007: 포스트 작성 - 빈 제목

- **카테고리**: 유효성 검증
- **사전 조건**: user1으로 로그인, 포스트 작성 페이지
- **테스트 단계**:
  1. 제목을 비워두고 내용만 입력
  2. "저장" 버튼 클릭
- **기대 결과**:
  - 작성 폼 유지
  - "제목을 입력해주세요." 에러 메시지
- **코드 근거**: `PostRequestDto.java:9` (`@NotBlank(message = "제목을 입력해주세요.")`)

#### TC-VAL-008: 포스트 작성 - 제목 200자 초과

- **카테고리**: 유효성 검증
- **사전 조건**: user1으로 로그인, 포스트 작성 페이지
- **테스트 단계**:
  1. 제목에 201자 이상 입력, 내용 정상 입력
  2. "저장" 버튼 클릭
- **기대 결과**:
  - 작성 폼 유지
  - "제목은 200자 이내로 입력해주세요." 에러 메시지
- **코드 근거**: `PostRequestDto.java:10` (`@Size(max = 200)`)

#### TC-VAL-009: 포스트 작성 - 빈 내용

- **카테고리**: 유효성 검증
- **사전 조건**: user1으로 로그인, 포스트 작성 페이지
- **테스트 단계**:
  1. 제목 정상 입력, 내용 비워둠
  2. "저장" 버튼 클릭
- **기대 결과**:
  - 작성 폼 유지
  - "내용을 입력해주세요." 에러 메시지
- **코드 근거**: `PostRequestDto.java:13` (`@NotBlank(message = "내용을 입력해주세요.")`)

#### TC-VAL-010: 포스트 수정 - 빈 제목

- **카테고리**: 유효성 검증
- **사전 조건**: user1으로 로그인, 본인 포스트 수정 페이지
- **테스트 단계**:
  1. 제목을 모두 지우고 "저장" 클릭
- **기대 결과**:
  - 수정 폼 유지 (postId 모델 속성 유지)
  - "제목을 입력해주세요." 에러 메시지
- **코드 근거**: `PostWebController.java:88-91`, `PostRequestDto.java:9`

#### TC-VAL-011: 댓글 작성 - 빈 내용

- **카테고리**: 유효성 검증
- **사전 조건**: user1으로 로그인, 포스트 상세 페이지
- **테스트 단계**:
  1. 댓글 내용을 비워두고 "댓글 작성" 클릭
- **기대 결과**:
  - 포스트 상세 페이지로 리다이렉트 (댓글 저장되지 않음)
- **코드 근거**: `PostWebController.java:113-115` (`if (!bindingResult.hasErrors())` 조건으로 무시), `CommentRequestDto.java:8`

---

## 4. 시나리오 추출 근거 매핑

### 코드 소스별 시나리오 매핑

| 소스 파일 | 라인 | 추출된 시나리오 |
|----------|------|---------------|
| `AuthWebController.java` | 26-29 | TC-AUTH-002 (로그인 페이지) |
| `AuthWebController.java` | 31-35 | TC-AUTH-001 (회원가입 페이지) |
| `AuthWebController.java` | 37-53 | TC-AUTH-001, TC-VAL-001~006 |
| `PostWebController.java` | 33-38 | TC-POST-001, TC-POST-002 |
| `PostWebController.java` | 40-49 | TC-POST-003 |
| `PostWebController.java` | 51-55 | TC-POST-004 |
| `PostWebController.java` | 57-69 | TC-POST-004, TC-VAL-007~009 |
| `PostWebController.java` | 71-79 | TC-POST-005 |
| `PostWebController.java` | 81-96 | TC-POST-005, TC-VAL-010 |
| `PostWebController.java` | 98-105 | TC-POST-006 |
| `PostWebController.java` | 107-117 | TC-CMT-001, TC-VAL-011 |
| `PostWebController.java` | 119-125 | TC-CMT-002 |
| `UserServiceImpl.java` | 34-35 | TC-VAL-005 |
| `UserServiceImpl.java` | 37-38 | TC-VAL-006 |
| `UserServiceImpl.java` | 50-52 | TC-AUTH-004 |
| `PostServiceImpl.java` | 38-39 | TC-POST-007 |
| `PostServiceImpl.java` | 60-61 | TC-SEC-003 |
| `PostServiceImpl.java` | 74-75 | TC-SEC-004 |
| `CommentServiceImpl.java` | 76-77 | TC-SEC-005 |
| `SecurityConfig.java` | 47 | TC-SEC-006 |
| `SecurityConfig.java` | 48 | TC-SEC-001, TC-SEC-002 |
| `SecurityConfig.java` | 50-53 | TC-AUTH-002, TC-AUTH-003 |
| `SecurityConfig.java` | 55-58 | TC-AUTH-005 |
| `SignupRequestDto.java` | 10-11 | TC-VAL-001, TC-VAL-002 |
| `SignupRequestDto.java` | 14-15 | TC-VAL-003 |
| `SignupRequestDto.java` | 18-19 | TC-VAL-004 |
| `PostRequestDto.java` | 9-10 | TC-VAL-007, TC-VAL-008, TC-VAL-010 |
| `PostRequestDto.java` | 13 | TC-VAL-009 |
| `CommentRequestDto.java` | 8 | TC-VAL-011 |

### 시나리오 통계

| 카테고리 | 정상 경로 | 예외 경로 | 보안 | 유효성 검증 | 합계 |
|---------|----------|----------|------|-----------|------|
| TC-AUTH (인증) | 3 | 2 | - | - | 5 |
| TC-POST (포스트) | 6 | 1 | - | - | 7 |
| TC-CMT (댓글) | 2 | - | - | - | 2 |
| TC-SEC (보안) | - | - | 7 | - | 7 |
| TC-VAL (유효성) | - | - | - | 11 | 11 |
| **합계** | **11** | **3** | **7** | **11** | **32** |
