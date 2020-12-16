package com.example.redis_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.util.concurrent.TimeUnit;

/**
 * 实现了自旋的redis分布式悲观锁
 */

@Component
public class RedisLock {
    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate redisTemplate;

//守护线程,自动为锁延时
    private Thread expireThread = new Thread() {

        public synchronized void run() {
            while (true) {
                redisTemplate.expire("lock", 3, TimeUnit.SECONDS);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public boolean lock() {

        Boolean lock = false;

        while (lock == false) {

            lock = redisTemplate.opsForValue().setIfAbsent("lock", "1", 3, TimeUnit.SECONDS);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        expireThread.start();
        return lock;
    }

    public boolean unLock() {
        expireThread.stop();
        return
                redisTemplate.delete("lock");
    }


}
