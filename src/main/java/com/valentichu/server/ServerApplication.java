package com.valentichu.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动服务器的main方法
 *
 * @author Valentichu
 * created on 2017/08/25
 */
@MapperScan("com.valentichu.server.core.mapper")
@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
