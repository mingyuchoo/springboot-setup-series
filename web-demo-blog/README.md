# Blog - Spring Boot 블로그 서비스

포스트(Post)와 댓글(Comment) CRUD 기능을 제공하는 웹 블로그 애플리케이션입니다.

## 기술 스택

- **언어**: Java 25
- **프레임워크**: Spring Boot 4.0.2
- **빌드 도구**: Gradle 9.3.1
- **데이터베이스**: H2 (인메모리)
- **ORM**: Spring Data JPA
- **템플릿 엔진**: Thymeleaf
- **인증**: Spring Security (폼 로그인)
- **테스트**: JUnit 5, Spring Boot Test, Spring Security Test

## 프로젝트 구조

```
├── docs/                        # 프로젝트 문서
│   ├── requirements.md          # 요구사항 관리 문서
│   └── test-scenarios.md        # 사용자 테스트 시나리오
└── src/main/java/com/demo/blog/
    ├── controller/              # REST API 엔드포인트
    │   └── web/                 # Thymeleaf 웹 컨트롤러
    ├── service/                 # 비즈니스 로직 인터페이스
    │   └── impl/                # 서비스 구현체
    ├── repository/              # 데이터 접근 계층
    ├── domain/                  # Entity (User, Post, Comment)
    ├── dto/                     # 요청/응답 DTO
    ├── config/                  # 설정 클래스 (SecurityConfig)
    ├── exception/               # 커스텀 예외 처리
    └── common/                  # 공통 유틸리티 (ApiResponse)
```

## 실행 방법

### 사전 요구사항

- JDK 25 이상

### 애플리케이션 실행

```bash
./gradlew bootRun
```

실행 후 브라우저에서 `http://localhost:8080` 으로 접속합니다.

### 테스트 실행

```bash
./gradlew test
```

## 주요 기능

| 기능 | 설명 |
| --- | --- |
| 회원가입/로그인 | Spring Security 기반 폼 인증 |
| 포스트 CRUD | 게시글 작성, 조회, 수정, 삭제 |
| 댓글 CRUD | 포스트에 종속된 댓글 관리 |
| REST API | JSON 기반 API 제공 |
| 웹 UI | Thymeleaf 기반 화면 제공 |

## API 엔드포인트

### 인증

- `POST /api/auth/signup` - 회원가입

### 포스트

- `GET /api/posts` - 포스트 목록 조회
- `GET /api/posts/{id}` - 포스트 상세 조회
- `POST /api/posts` - 포스트 작성
- `PUT /api/posts/{id}` - 포스트 수정
- `DELETE /api/posts/{id}` - 포스트 삭제

### 댓글

- `GET /api/posts/{postId}/comments` - 댓글 목록 조회
- `POST /api/posts/{postId}/comments` - 댓글 작성
- `PUT /api/comments/{id}` - 댓글 수정
- `DELETE /api/comments/{id}` - 댓글 삭제

## H2 콘솔

개발 환경에서 H2 데이터베이스 콘솔을 사용할 수 있습니다.

- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:blogdb`
- **Username**: `sa`
- **Password**: (없음)
