package com.example.redis_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


@SpringBootTest
class RedisDemoApplicationTests {
    @Autowired
    private RedisLock redisLock;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {


        //获取到锁才执行语句块
        if (redisLock.lock()) {
            redisTemplate.opsForValue().setIfAbsent("ticket", 100);

            Object ticketObj = redisTemplate.opsForValue().get("ticket");

            double ticket = Double.parseDouble(ticketObj.toString());

            if (ticket >= 1) {

                redisTemplate.opsForValue().decrement("ticket", 1);

                System.out.println("余额现在为:" + redisTemplate.opsForValue().get("ticket"));


            } else System.out.println("余额不足,当前余额:" + ticketObj);

            redisLock.unLock();

        }
    }

}
