CRATE TABLE hobby
( id      INT         NOT NULL DEFAULT 0  COMMENT 'id'
, user_id INT         NOT NULL DEFAULT 0  COMMENT 'User ID'
, name    VARCHAR(20) NOT NULL DEFAULT '' COMMENT "Hobby Name"
, PRIMARY KEY(id)
);

INSERT INTO hobby(id, user_id, name) VALUES (1, 1, 'Soccer');