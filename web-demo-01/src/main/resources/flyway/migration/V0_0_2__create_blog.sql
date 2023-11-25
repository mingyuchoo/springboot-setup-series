CREATE TABLE blog
( id      INT         NOT NULL DEFAULT 0  COMMENT 'id'
, user_id INT         NOT NULL DEFAULT 0  COMMENT 'User ID'
, title   VARCHAR(20) NOT NULL DEFAULT '' COMMENT "Blog Title"
, PRIMARY KEY(id)
, FOREIGN KEY (user_id) REFERENCES user (id)
);

INSERT INTO blog(id, user_id, title) VALUES (1, 1, 'Blog of Adam');
INSERT INTO blog(id, user_id, title) VALUES (2, 2, 'First blog of Ben');
INSERT INTO blog(id, user_id, title) VALUES (3, 2, 'Second blog of Ben');
INSERT INTO blog(id, user_id, title) VALUES (4, 3, 'First blog of Chris');
INSERT INTO blog(id, user_id, title) VALUES (5, 3, 'Second blog of Chris');
INSERT INTO blog(id, user_id, title) VALUES (6, 3, 'Third blog of Chris');
