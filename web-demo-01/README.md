# README

## Intro

Spring 3에서 REST API 서비스를 JPA 와 MyBatis 로 MySQL 데이터베이스 를 이용한 사례입니다.
Flyway를 이용해 데이터베이스 마이그레이션을 하도록 했습니다.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- REST API
- JPA + MyBatis
- MySQL
- Flyway

## Note

- Controller 에서 Service 를 주입할 때 `@Autowired` 어노테이션을 사용하지 않고
ServiceImpl 클래스 상단에 @Service 어노테이션에 변수명을 지정함으로써 자동 주입되도록 했습니다.

## Prerequisites

- Use {Linux | macOS} system
- Install `JDK 17`
- Install `direnv`
- Install `curl`

## How to configure your environment

`.envrc.test` 파일을 `.envrc` 파일로 복사하세요.
`.envrc` 파일에 3개 데이터베이스 값을 본인의 데이터베이스에 맞게 수정하세요.

## How to build

```bash
./gradlew clean build
```

## How to run

```bash
./gradlew bootRun
```

## How to check

```bash
curl -X GET http://localhost:4000/users
```
