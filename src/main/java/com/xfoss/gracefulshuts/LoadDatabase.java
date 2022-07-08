package com.xfoss.gracefulshuts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UPSRepository repo) {
        return args -> {
            log.info(String.format("预加载 %s", repo.save(
                            new UPS("ups2000", "ups1.senscomm.com", "10.12.10.108")
                            )));
        };
    }
}
