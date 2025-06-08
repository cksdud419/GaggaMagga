-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ssafytrip
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `ssafytrip` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafytrip` ;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    id VARCHAR(50) PRIMARY KEY,          -- 사용자 ID (기본 키)
    nick_name VARCHAR(50) NOT NULL,       -- 닉네임
    email VARCHAR(100) NOT NULL UNIQUE,  -- 이메일 (중복 불가)
    password VARCHAR(255) NOT NULL,      -- 비밀번호 (보안을 위해 길게)
    refresh VARCHAR(200) 				 -- refresh 토큰 컬럼
);

DROP TABLE IF EXISTS comment; 
CREATE TABLE comment (
	id INT PRIMARY KEY AUTO_INCREMENT,
	notice_id INT NOT NULL,
	author_id VARCHAR(50) NOT NULL,
	content TEXT NOT NULL,
	create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (author_id) REFERENCES `user`(id) ON DELETE CASCADE,
	FOREIGN KEY (notice_id) REFERENCES notice(id)	ON DELETE CASCADE
);

DROP TABLE IF EXISTS notice;

CREATE TABLE notice (
    id INT PRIMARY KEY AUTO_INCREMENT,
    author_id VARCHAR(50) NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 등록 시 현재 시간 자동 입력
    FOREIGN KEY (author_id) REFERENCES `user`(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS course;
CREATE TABLE course (
	id INT PRIMARY KEY AUTO_INCREMENT,
	author_id VARCHAR(50) NOT NULL,
	title VARCHAR(200) NOT NULL,
	FOREIGN KEY (author_id) REFERENCES `user`(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS course_details;
CREATE TABLE course_details (
	id INT PRIMARY KEY AUTO_INCREMENT,
	attractions_id INT NOT NULL,
	course_id INT NOT NULL,
	detail_order INT NOT NULL,
--	FOREIGN KEY (attractions_id) REFERENCES attractions(no) ON DELETE CASCADE
	FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE		
);

DROP TABLE IF EXISTS bookmark;
CREATE TABLE bookmark(
	id INT PRIMARY KEY AUTO_INCREMENT,
	author_id VARCHAR(50) NOT NULL,
	attractions_id INT NOT NULL,
	FOREIGN KEY (author_id) REFERENCES `user`(id) ON DELETE CASCADE,
--	FOREIGN KEY (attractions_id) REFERENCES attractions(no) ON DELETE CASCADE
	UNIQUE(author_id, attractions_id) -- 한 사람이 동일한 관광지를 여러번 북마크하지 못하도록 제약
);