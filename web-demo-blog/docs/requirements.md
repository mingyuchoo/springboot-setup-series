# 요구사항 관리 문서

## 요구사항 목록

| ID | 구분 | 제목 | 상태 | 영향 파일 | 등록일 |
|---|---|---|---|---|---|
| REQ-F001 | 기능 | 포스트 및 댓글 CRUD 웹 블로그 서비스 | 완료 | 전체 (26개 파일) | 2026-02-06 |
| REQ-N001 | 문서 | README.md 프로젝트 소개 문서 작성 | 완료 | README.md | 2026-02-07 |
| REQ-N002 | 문서 | .gitignore 파일 작성 | 완료 | .gitignore | 2026-02-07 |
| REQ-N003 | 테스트 | 레이어별 테스트 코드 작성 | 완료 | test/ 전체, build.gradle | 2026-02-07 |
| REQ-N004 | 빌드 | 테스트 커버리지 측정 (JaCoCo) 설정 | 완료 | build.gradle, test/controller/web/ | 2026-02-07 |
| REQ-N005 | 기능 | 샘플 데이터 초기화 (사용자·포스트·댓글 각 100건) | 완료 | config/DataInitializer.java, test/config/DataInitializerTest.java | 2026-02-07 |
| REQ-N006 | 문서 | 코드베이스 기반 사용자 테스트 시나리오 문서 생성 | 완료 | docs/test-scenarios.md | 2026-02-07 |
| REQ-N007 | 문서 | CLAUDE.md에 테스트 시나리오 문서 자동 생성 워크플로우 추가 | 완료 | CLAUDE.md | 2026-02-07 |
| REQ-N008 | 문서 | requirements.md를 docs/ 디렉토리로 이동 및 참조 경로 갱신 | 완료 | requirements.md → docs/requirements.md, CLAUDE.md, README.md | 2026-02-07 |
| REQ-N009 | 테스트 | Playwright E2E 테스트 코드 생성 (32개 시나리오) | 완료 | build.gradle, test/e2e/ 전체 | 2026-02-07 |

---

## REQ-F001: 포스트 및 댓글 CRUD 웹 블로그 서비스

- **구분**: 기능 (Feature)
- **상태**: 완료
- **설명**: 포스트(Post)와 댓글(Comment)을 생성, 조회, 수정, 삭제할 수 있는 웹 블로그 서비스 구현
- **세부 요구사항**:
  - 포스트 CRUD (제목, 내용, 작성자, 작성일시)
  - 댓글 CRUD (포스트에 종속, 내용, 작성자, 작성일시)
  - REST API 제공
  - Thymeleaf 웹 UI 제공
  - Spring Security 폼 로그인 인증
  - Spring Boot 4.0.2 + Java 25 + Gradle 9.3.1 + H2 인메모리 DB
- **영향 파일**:
  - `build.gradle`, `settings.gradle`, `application.yml`
  - `domain/`: User.java, Post.java, Comment.java
  - `repository/`: UserRepository, PostRepository, CommentRepository
  - `dto/`: PostRequestDto, PostResponseDto, CommentRequestDto, CommentResponseDto, SignupRequestDto
  - `service/`: UserService, PostService, CommentService + impl
  - `controller/`: AuthController, PostController, CommentController
  - `controller/web/`: AuthWebController, PostWebController
  - `config/`: SecurityConfig
  - `exception/`: BusinessException, GlobalExceptionHandler
  - `common/`: ApiResponse
  - `templates/`: layout, auth/login, auth/register, post/list, post/detail, post/form
  - `static/css/`: style.css

---

## REQ-N001: README.md 프로젝트 소개 문서 작성

- **구분**: 문서 (Non-functional)
- **상태**: 완료
- **설명**: 프로젝트 소개, 기술 스택, 실행 방법, API 엔드포인트 등을 포함하는 README.md 작성
- **영향 파일**:
  - `README.md`

---

## REQ-N002: .gitignore 파일 작성

- **구분**: 문서 (Non-functional)
- **상태**: 완료
- **설명**: Gradle 빌드 산출물, IDE 설정, OS 파일, H2 DB 파일 등을 Git 추적에서 제외하는 .gitignore 작성
- **영향 파일**:
  - `.gitignore`

---

## REQ-N003: 레이어별 테스트 코드 작성

- **구분**: 테스트 (Non-functional)
- **상태**: 완료
- **설명**: 코드베이스를 분석하여 Service/Controller/Repository 레이어별 테스트 코드를 작성하고 전체 테스트 통과를 검증
- **테스트 결과**: 48개 테스트 전체 통과 (Service 21 + Controller 17 + Repository 9 + 기존 1)
- **영향 파일**:
  - `build.gradle` — 테스트 의존성 추가 (spring-boot-starter-webmvc-test, spring-boot-starter-data-jpa-test)
  - `src/test/java/com/demo/blog/service/impl/PostServiceImplTest.java` — 8개 테스트
  - `src/test/java/com/demo/blog/service/impl/CommentServiceImplTest.java` — 8개 테스트
  - `src/test/java/com/demo/blog/service/impl/UserServiceImplTest.java` — 5개 테스트
  - `src/test/java/com/demo/blog/controller/PostControllerTest.java` — 7개 테스트
  - `src/test/java/com/demo/blog/controller/CommentControllerTest.java` — 6개 테스트
  - `src/test/java/com/demo/blog/controller/AuthControllerTest.java` — 4개 테스트
  - `src/test/java/com/demo/blog/repository/PostRepositoryTest.java` — 5개 테스트
  - `src/test/java/com/demo/blog/repository/CommentRepositoryTest.java` — 4개 테스트

---

## REQ-N004: 테스트 커버리지 측정 (JaCoCo) 설정

- **구분**: 빌드 (Non-functional)
- **상태**: 완료
- **설명**: JaCoCo 플러그인을 설정하여 테스트 커버리지를 측정하고 HTML/XML 리포트를 생성. 최소 라인 커버리지 80% 기준 적용
- **커버리지 결과**: 전체 라인 커버리지 95% (339라인 중 322라인 커버), 전체 테스트 65개 통과
- **영향 파일**:
  - `build.gradle` — JaCoCo 플러그인, 리포트 설정, 커버리지 검증 규칙 추가
  - `src/test/java/com/demo/blog/controller/web/AuthWebControllerTest.java` — 5개 테스트
  - `src/test/java/com/demo/blog/controller/web/PostWebControllerTest.java` — 12개 테스트

---

## REQ-N005: 샘플 데이터 초기화 (사용자·포스트·댓글 각 100건)

- **구분**: 기능 (Feature)
- **상태**: 완료
- **설명**: 애플리케이션 기동 시 CommandLineRunner로 샘플 사용자 100명, 포스트 100개, 댓글 100개를 자동 생성하여 즉시 사용 가능한 데모 환경 제공
- **샘플 계정 정보**:
  - 사용자명: `user1` ~ `user100`
  - 비밀번호: `password` (모든 계정 동일)
  - 이메일: `user1@example.com` ~ `user100@example.com`
- **테스트 결과**: 5개 테스트 전체 통과 (전체 빌드 JaCoCo 커버리지 검증 포함)
- **영향 파일**:
  - `src/main/java/com/demo/blog/config/DataInitializer.java` — CommandLineRunner 기반 시드 데이터 생성
  - `src/test/java/com/demo/blog/config/DataInitializerTest.java` — 5개 테스트 (@SpringBootTest 통합 테스트)

---

## REQ-N006: 코드베이스 기반 사용자 테스트 시나리오 문서 생성

- **구분**: 문서 (Non-functional)
- **상태**: 완료
- **설명**: 코드베이스(웹 컨트롤러, DTO 검증, Service 비즈니스 로직, SecurityConfig)를 분석하여 사용자 관점의 E2E 테스트 시나리오를 체계적으로 도출한 문서 생성
- **시나리오 통계**: 총 32개 시나리오 (정상 경로 11 + 예외 경로 3 + 보안 7 + 유효성 검증 11)
- **추출 소스**: 웹 컨트롤러 2개, DTO 3개, Service 구현체 3개, SecurityConfig 1개
- **영향 파일**:
  - `docs/test-scenarios.md` — 사용자 테스트 시나리오 문서

---

## REQ-N007: CLAUDE.md에 테스트 시나리오 문서 자동 생성 워크플로우 추가

- **구분**: 문서 (Non-functional)
- **상태**: 완료
- **설명**: CLAUDE.md에 사용자 테스트 시나리오 문서(docs/test-scenarios.md) 자동 생성·갱신 워크플로우를 추가하여, 코드 변경 시 시나리오 문서도 함께 추적·갱신되도록 규칙 정의
- **영향 파일**:
  - `CLAUDE.md` — 테스트 시나리오 문서 자동 생성 섹션 추가

---

## REQ-N008: requirements.md를 docs/ 디렉토리로 이동 및 참조 경로 갱신

- **구분**: 문서 (Non-functional)
- **상태**: 완료
- **설명**: 프로젝트 루트의 requirements.md를 docs/ 디렉토리로 이동하고, CLAUDE.md 및 README.md에서 해당 파일을 참조하는 경로를 갱신
- **영향 파일**:
  - `requirements.md` → `docs/requirements.md` — 파일 이동
  - `CLAUDE.md` — requirements.md 경로 참조 11곳 갱신
  - `README.md` — 변경 사항 반영

---

## REQ-N009: Playwright E2E 테스트 코드 생성 (32개 시나리오)

- **구분**: 테스트 (Non-functional)
- **상태**: 완료
- **설명**: `docs/test-scenarios.md`에 정의된 32개 사용자 테스트 시나리오를 Playwright for Java 기반 E2E 자동화 테스트 코드로 변환. `@SpringBootTest(RANDOM_PORT)` + `@Tag("e2e")`로 기존 단위 테스트와 분리
- **시나리오 분포**: TC-AUTH 5 + TC-POST 7 + TC-CMT 2 + TC-SEC 7 + TC-VAL 11 = 32개
- **영향 파일**:
  - `build.gradle` — Playwright 의존성 추가, e2eTest 태스크 분리
  - `src/test/java/com/demo/blog/e2e/BaseE2ETest.java` — 추상 베이스 클래스
  - `src/test/java/com/demo/blog/e2e/AuthE2ETest.java` — 인증 E2E 테스트 (5개)
  - `src/test/java/com/demo/blog/e2e/PostE2ETest.java` — 포스트 E2E 테스트 (7개)
  - `src/test/java/com/demo/blog/e2e/CommentE2ETest.java` — 댓글 E2E 테스트 (2개)
  - `src/test/java/com/demo/blog/e2e/SecurityE2ETest.java` — 보안 E2E 테스트 (7개)
  - `src/test/java/com/demo/blog/e2e/ValidationE2ETest.java` — 유효성 검증 E2E 테스트 (11개)
