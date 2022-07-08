package com.xfoss.gracefulshuts;

import java.util.Objects;
import java.util.Date;
import java.util.EnumMap;
import java.net.InetAddress;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

enum GridPowerA {
    UFREQ,
    VFREQ,
    WFREQ,
    UVOLTAGE,
    VVOLATGE,
    WVOLATGE,
}

enum GridPowerB {
    FREQ,
    UVOLTAGE,
    VVOLATGE,
    WVOLATGE,
}

@Entity
public class UPSStatus {

    private @Id @GeneratedValue Long id;
    private UPS ups;
    private Date captueredAt;
    private boolean gridPowerSupply;
    private EnumMap<GridPowerA, Integer> gridPowerA;
    private EnumMap<GridPowerB, Integer> gridPowerB; 

    UPSStatus() {}

    UPSStatus(UPS specifiedUPS) {
        ups = specifiedUPS;
        captueredAt = Date(System.currentTimeMillis());
        gridPowerA = new EnumMap<GridPowerA, Integer>(GridPowerA.class);
        gridPowerB = new EnumMap<GridPowerB, Integer>(GridPowerB.class);
    }
}
