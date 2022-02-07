CREATE TABLE book (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR,
    author VARCHAR,

    CONSTRAINT book_pk PRIMARY KEY (id)
);

INSERT INTO book(title, author) values('쿠버네티스 모범사례', '브렌던 번스');
INSERT INTO book(title, author) values('데이터 중심 애플리케이션 설계', '마틴 클레프만');
