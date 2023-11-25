# README

## Intro

Spring 3에서 JPA 와 PostgresQL 데이터베이스 를 이용한 사례입니다.
생성된 Repository 데이터를 HAL Explorer 의해 http://localhost:4000/api 에서 확인할 수 있습니다.
## Tech Stack

- Java 17
- Spring Boot 3
- Spring HAL
- JPA
- PostgreSQL

## Prerequisites

- Use {Linux | macOS} system
- Install `JDK 17`
- Install `direnv`

## How to configure your environment

`.envrc.test` 파일을 `.envrc` 파일로 복사하세요.
`.envrc` 파일에 3개 데이터베이스 값을 본인의 데이터베이스 에 맞게 수정하세요.

## How to build

```bash
./gradlew clean build
```

## How to run

```bash
./gradlew bootRun
```

## How to check

- http://localhost:4000/api

경로 `api` 는 `application.yml` 에 선언되어 있습니다.
