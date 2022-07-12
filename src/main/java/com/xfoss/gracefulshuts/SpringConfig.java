package com.xfoss.gracefulshuts;

import java.util.EnumMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
class SpringConfig {
    private static final Logger log = LoggerFactory.getLogger(SpringConfig.class);
    private final UPSRepository UPSRepo;
    private final UPSStatusRepository UPSStatusRepo;

    SpringConfig(UPSRepository UPSR, UPSStatusRepository UPSStatusR) {
        UPSRepo = UPSR;
        UPSStatusRepo = UPSStatusR;
    }

    private UPS getUPS() {
        return new UPS("10.12.10.108");
    }

    private UPSStatus getUPSStatus(UPS ups) {
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

        return new UPSStatus(
                ups,
                gridPowerAVOIDs,
                gridPowerAFOIDs,
                gridPowerBVOIDs,
                "1.3.6.1.2.1.33.1.5.1.0"
                );

    }


    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void scheduledStatusCapture() {
        List<UPS> upsList = UPSRepo.findAll();

        for (UPS ups: upsList) {
            log.info(String.format("新的 UPS 状态已记录 - %s", UPSStatusRepo.save(getUPSStatus(ups))));
        }
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            UPS ups = getUPS();
            UPSStatus status = getUPSStatus(ups);
            log.info(String.format("预加载 %s, %s", UPSRepo.save(ups), UPSStatusRepo.save(status)));
        };
    }
}
