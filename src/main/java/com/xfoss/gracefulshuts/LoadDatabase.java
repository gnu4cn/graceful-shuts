package com.xfoss.gracefulshuts;

import java.util.EnumMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UPSRepository repo, UPSStatusRepository sRepo) {
        return args -> {
            UPS ups = new UPS("10.12.10.108");

            EnumMap<Line, String> gridPowerAFOIDs = new EnumMap<>(Line.class);
            gridPowerAFOIDs.put(Line.U, "1.3.6.1.2.1.33.1.3.3.1.2.1");
            gridPowerAFOIDs.put(Line.V, "1.3.6.1.2.1.33.1.3.3.1.2.2");
            gridPowerAFOIDs.put(Line.W, "1.3.6.1.2.1.33.1.3.3.1.2.3");

            EnumMap<Line, String> gridPowerAVOIDs = new EnumMap<>(Line.class);
            gridPowerAVOIDs.put(Line.U, "1.3.6.1.2.1.33.1.3.3.1.3.1");
            gridPowerAVOIDs.put(Line.V, "1.3.6.1.2.1.33.1.3.3.1.3.2");
            gridPowerAVOIDs.put(Line.W, "1.3.6.1.2.1.33.1.3.3.1.3.3");

            EnumMap<Line, String> gridPowerBVOIDs = new EnumMap<>(Line.class);
            gridPowerBVOIDs.put(Line.U, "1.3.6.1.2.1.33.1.5.3.1.2.1");
            gridPowerBVOIDs.put(Line.V, "1.3.6.1.2.1.33.1.5.3.1.2.1");
            gridPowerBVOIDs.put(Line.W, "1.3.6.1.2.1.33.1.5.3.1.2.1");

            UPSStatus status = new UPSStatus(
                    ups,
                    gridPowerAVOIDs,
                    gridPowerAFOIDs,
                    gridPowerBVOIDs,
                    "1.3.6.1.2.1.33.1.5.1.0"
                    );

            log.info(String.format("预加载 %s, %s", repo.save(ups), sRepo.save(status)));
        };
    }
}
