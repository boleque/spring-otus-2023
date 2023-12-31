DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE
(
    ID IDENTITY NOT NULL PRIMARY KEY,
    NAME VARCHAR(50)
);
DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR
(
    ID IDENTITY NOT NULL PRIMARY KEY,
    NAME VARCHAR(255)
);
DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK (
    ID IDENTITY NOT NULL PRIMARY KEY,
    TITLE VARCHAR(50),
    AUTHOR_ID BIGINT NOT NULL,
    GENRE_ID BIGINT NOT NULL,
    FOREIGN KEY (AUTHOR_ID) REFERENCES author (ID) ON DELETE CASCADE,
    FOREIGN KEY (GENRE_ID) REFERENCES genre (ID) ON DELETE SET NULL
);