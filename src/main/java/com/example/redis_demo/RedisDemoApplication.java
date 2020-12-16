package com.example.redis_demo;

import com.example.redis_demo.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class RedisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDemoApplication.class, args);
        ApplicationContext applicationContext  =  new AnnotationConfigApplicationContext(RedisConfig.class);
        //打印所有在容器中的实例（Bean）
        String [] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames
        ) {
            System.out.println(beanName);

        }
    }

}
