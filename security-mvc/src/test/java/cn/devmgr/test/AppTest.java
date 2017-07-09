package cn.devmgr.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import cn.devmgr.example.sso.mvc.config.SecurityConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppTest {

    @Test
    public void contextLoads() {
    	//if loaded then pass
    }

}

