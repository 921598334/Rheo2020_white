package com.Rheo.Rheo2020;

import com.Rheo.Rheo2020.Service.MailServer;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class ResumeApplicationTests {


    @Autowired
    MailServer mailServer;




    @Test
    void contextLoads() {

        mailServer.sendMail("201708021040@cqu.edu.cn","第15届全国流变学学术会议注册码","您好，您的注册码为XXXX，请尽快输入注册码完成注册");

    }




}
