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

        mailServer.sendMail("q921598334@126.com","第15届全国流变学学术会议注册码","您好，您的注册码为XXXX，请尽快输入注册码完成注册");

    }




    @Test
    void testRedis(){

        Jedis jedis = new Jedis("122.114.178.53", 6379);


        jedis.set("name", "itheima");
        jedis.get("name");



        jedis.close();


    }

}
