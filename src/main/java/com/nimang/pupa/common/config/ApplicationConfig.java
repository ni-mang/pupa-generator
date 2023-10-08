package com.nimang.pupa.common.config;

import com.nimang.pupa.common.util.SnowFlakeIdGen;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public SnowFlakeIdGen getIdGenerator(){
        SnowFlakeIdGen snowFlakeIdGen = new SnowFlakeIdGen(11, 11);
        return snowFlakeIdGen;
    }

}
