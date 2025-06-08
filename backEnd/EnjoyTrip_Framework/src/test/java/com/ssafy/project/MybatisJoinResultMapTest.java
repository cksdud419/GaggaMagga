package com.ssafy.project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.ssafy.project.model.dao.UserDao;
import com.ssafy.project.model.dto.User;

import lombok.RequiredArgsConstructor;


@MybatisTest
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 설정 사용
public class MybatisJoinResultMapTest {
    @Autowired
    private UserDao userDao;

    @Test
    void testSelectById() {
        User user = userDao.selectById("ssafy");

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("ssafy");
        System.out.println(user);
    }

    @Test
    void testSelectByEmail() {
        User user = userDao.selectByEmail("ssafy@example.com");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("sssafy@example.com");
    }
}
