package com.guat.lzp.product.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动完成后在控制台输出访问地址
 */
@Component
public class StartupUrlPrinter implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        System.out.println();
        System.out.println("访问地址：http://localhost:8080");
        System.out.println();
    }
}
