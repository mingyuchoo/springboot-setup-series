CREATE TABLE user
( id INT AUTO_INCREMENT COMMENT 'id'
, name VARCHAR(50) NOT NULL COMMENT 'name'
, PRIMARY KEY (id)
) COMMENT 'user';

INSERT INTO user(name) VALUES ('Adam');
INSERT INTO user(name) VALUES ('Ben');
INSERT INTO user(name) VALUES ('Chris');
INSERT INTO user(name) VALUES ('David');
INSERT INTO user(name) VALUES ('Eva');
INSERT INTO user(name) VALUES('Fred');
INSERT INTO user(name) VALUES('Grace');
