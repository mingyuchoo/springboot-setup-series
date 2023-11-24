DROP TABLE IF EXISTS member;
CREATE TABLE member (
    id serial PRIMARY KEY,
    name varchar(255),
    surname varchar(255),
    username varchar(255),
    email varchar(255),
    passport varchar(255)
);
