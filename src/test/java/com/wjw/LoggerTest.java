package com.wjw;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//更改测试运行器,使用Spring容器来运行单元测试
@SpringBootTest//使用SpringBoot的上下文测试
@Slf4j//--->此注解的作用等价于private final Logger log = LoggerFactory.getLogger(LoggerTest.class);//传入的是当前类的类名
public class LoggerTest {

    @Test
    public void test01(){
        log.debug("debug.....");
        log.info("info.....");
        log.error("error.....");

        /**
         * 如何在日志中输出变量
         */
        String name = "wjw";
        Integer age = 23;
        log.info("name: "+name + ", age: " + age);
        log.info("name: {}, age: {}",name,age);

    }
}
