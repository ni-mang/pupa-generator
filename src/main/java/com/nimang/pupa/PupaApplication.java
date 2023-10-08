package com.nimang.pupa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.nimang.pupa"})
@MapperScan(basePackages = {"com.nimang.pupa.base.mapper"})
public class PupaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PupaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(
                "██████╗ ██╗   ██╗██████╗  █████╗ \n" +
                "██╔══██╗██║   ██║██╔══██╗██╔══██╗\n" +
                "██████╔╝██║   ██║██████╔╝███████║\n" +
                "██╔═══╝ ██║   ██║██╔═══╝ ██╔══██║\n" +
                "██║     ╚██████╔╝██║     ██║  ██║\n" +
                "╚═╝      ╚═════╝ ╚═╝     ╚═╝  ╚═╝\n" +
                "                                 ");
    }
}
