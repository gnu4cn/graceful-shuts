package com.xfoss.gracefulshuts;

import java.net.InetAddress;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;

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

    public static UPSStatus getUPSStatus(UPS ups) {
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


    @Scheduled(fixedRate = 1000 * 30)
    public void scheduledStatusCapture() {
        List<UPS> upsList = UPSRepo.findAll();

        for (UPS ups: upsList) {
            ups.setRunningTime();
            UPSRepo.save(ups);
            log.info(String.format("?????? UPS ??????????????? - %s", UPSStatusRepo.save(getUPSStatus(ups))));
        }
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            UPS ups;

            Optional<UPS> result = UPSRepo.findOneByNameFQDN(InetAddress.getByName("10.12.10.108").getHostName());

            if ( result.isEmpty() ) {
                ups = new UPS("10.12.10.108");
                System.out.format("%s, UPS ????????????????????? %s", result, ups);
                log.info(String.format("????????? - %s, %s", UPSRepo.save(ups), UPSStatusRepo.save(getUPSStatus(ups))));

            } else {
                ups = result.get();
                System.out.format("UPS ????????? - %s", ups);
                log.info(String.format("????????? - %s, %s", ups, UPSStatusRepo.save(getUPSStatus(ups))));
            }

        };
    }
}
