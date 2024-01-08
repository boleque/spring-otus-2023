DROP TABLE IF EXISTS GENRE;
DROP TABLE IF EXISTS AUTHOR;
DROP TABLE IF EXISTS BOOK;
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS LIBRARY_USER;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS USER_ROLE;

CREATE TABLE GENRE
(
    ID BIGSERIAL,
    NAME VARCHAR(50),
    PRIMARY KEY (ID)
);

CREATE TABLE AUTHOR
(
    ID BIGSERIAL,
    NAME VARCHAR(255),
    PRIMARY KEY (ID)
);

CREATE TABLE BOOK (
    ID BIGSERIAL,
    TITLE VARCHAR(50),
    AUTHOR_ID BIGINT NOT NULL,
    GENRE_ID BIGINT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (AUTHOR_ID) REFERENCES author (ID) ON DELETE CASCADE,
    FOREIGN KEY (GENRE_ID) REFERENCES genre (ID) ON DELETE SET NULL
);

CREATE TABLE COMMENT
(
    ID BIGSERIAL,
    BOOK_ID BIGINT NOT NULL,
    TEXT VARCHAR(255),
    PRIMARY KEY (ID),
    FOREIGN KEY (BOOK_ID) REFERENCES book (ID) ON DELETE CASCADE
);

CREATE TABLE LIBRARY_USER
(
    ID BIGSERIAL,
    USERNAME VARCHAR(50),
    PASSWORD VARCHAR(150),
    PRIMARY KEY (ID)
);

CREATE TABLE ROLE
(
    ID BIGSERIAL,
    NAME VARCHAR(50),
    PRIMARY KEY (ID)
);

CREATE TABLE USER_ROLE
(
    user_id BIGSERIAL NOT NULL,
    role_id BIGSERIAL NOT NULL,
    CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES LIBRARY_USER (id),
    CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES ROLE (id)
);