--마지막 수정일 : 2020 11 19
--ChattingApp 테스트 전에 꼭 DB를 등록해 주세요!
DROP DATABASE IF EXISTS ChattingApp_DB;
CREATE DATABASE IF NOT EXISTS ChattingApp_DB;
USE ChattingApp_DB;

DROP TABLE IF EXISTS User_info;

-- 회원가입 유저 정보가 들어갈 테이블.
-- 대응 서비스 : ID PW 찾기 요청 대응, 탈퇴시 삭제 대응
-- 추가사항 대기중.
CREATE TABLE User_info (
    Unumber         INT             NOT NULL,           -- 가입 순서대로 매길 유저 번호
    ChatName        VARCHAR(20)     NOT NULL,           -- 이름
    ID              VARCHAR(20)     NOT NULL,           -- ID
    PassWd          VARCHAR(20)     NOT NULL,           -- PW
    AcccountDate    DATE            NOT NULL,           -- 가입일자
    PRIMARY KEY(ID)                                     -- ID, PW 길이는 7자 이상
);

-- 관리자 계정의 추가.
-- 모든 암호는 암화화 복호화 과정을 거침
INSERT INTO User_info(Unumber, ChatName, ID, PassWd, AcccountDate) VALUES(1, 'rootAdmin', 'rootAdmin', 'tester', '2020-11-21');
INSERT INTO User_info(Unumber, ChatName, ID, PassWd, AcccountDate) VALUES(2, 'subAdmin', 'subAdmin', 'tester2', '2020-11-21');

DROP USER IF EXISTS 'AccountHost'@'localhost';

CREATE USER 'AccountHost'@'localhost' IDENTIFIED BY '12345';
GRANT ALL ON ChattingApp_DB.* to 'AccountHost'@'localhost';    