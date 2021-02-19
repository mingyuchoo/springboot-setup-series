# graphql01

## Run Server

```
./gradlew bootRun
```

## Connect to Server

```
http://localhost:4000/graphql
```

## Connect to h2base

connect url `http://localhost:8080/h2` in a web browser

check below information

- driver-class-name: org.h2.Driver
- url: jdbc:h2:file:./test_db;MODE=MYSQL;AUTO_SERVER=TRUE
- username: sa
- password:

add seed data using /src/main/resources/seed.sql
