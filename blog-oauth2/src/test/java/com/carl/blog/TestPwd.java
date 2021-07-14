package com.carl.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Auther: Carl
 * @Date: 2021/05/08/13:52
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPwd {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test1() {
        String carl = passwordEncoder.encode("carl");
        System.out.println("加密：" + carl);
    }
}
