package com.xfoss.gracefulshuts;

import java.util.Objects;
import java.util.Date;
import java.util.EnumMap;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.xfoss.Utils.SNMPv3API;

enum Line {
    U,
    V,
    W,
}

@Entity
public class UPSStatus {

    private @Id @GeneratedValue Long id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="UPS_ID", referencedColumnName="ID"),
        @JoinColumn(name="UPS_name", referencedColumnName="name")
    })
    private UPS ups;

    private Date captueredAt;
    private boolean gridPowerSupply;

    @Lob
    private EnumMap<Line, Integer> powerAVolt = new EnumMap<>(Line.class);

    @Lob
    private EnumMap<Line, Integer> powerAFreq = new EnumMap<>(Line.class);
    
    @Lob
    private EnumMap<Line, Integer> powerBVolt = new EnumMap<>(Line.class);

    private int powerBFreq;

    UPSStatus() {}

    public Long getId() {
        return id;
    }

    public int getPowerAUV() {
        return powerAVolt.get(Line.U);
    }

    public int getPowerAVV() {
        return powerAVolt.get(Line.V);
    }

    public int getPowerAWV() {
        return powerAVolt.get(Line.W);
    }

    public int getPowerAUF() {
        return powerAFreq.get(Line.U);
    }

    public int getPowerAVF() {
        return powerAFreq.get(Line.V);
    }

    public int getPowerAWF() {
        return powerAFreq.get(Line.W);
    }

    public int getPowerBUV() {
        return powerAVolt.get(Line.U);
    }

    public int getPowerBVV() {
        return powerAVolt.get(Line.V);
    }

    public int getPowerBWV() {
        return powerAVolt.get(Line.W);
    }

    public int getPowerBF() {
        return powerBFreq;
    }

    public boolean getGridPowerSupply() {
        return gridPowerSupply;
    }

    public Date getCapturedAt() {
        return captueredAt;
    }

    public UPS getUPS() {
        return ups;
    }

    UPSStatus(
            UPS specifiedUPS, 
            EnumMap<Line, String> gridPowerAVOIDs, 
            EnumMap<Line, String> gridPowerAFOIDs,
            EnumMap<Line, String> gridPowerBVOIDs,
            String gridPowerBFOID ) 
    {
        String upsIp = specifiedUPS.getIpAddress().getHostAddress();

        ups = specifiedUPS;
        captueredAt = new Date(System.currentTimeMillis());

        Set<Line> keys = gridPowerAVOIDs.keySet();

        for(Line key: keys) {
            powerAVolt.put(key, Integer.parseInt(SNMPv3API.sendRequest(
                            upsIp,
                            "161",
                            gridPowerAVOIDs.get(key)
                            )));
            powerAFreq.put(key, Integer.parseInt(SNMPv3API.sendRequest(
                            upsIp,
                            "161",
                            gridPowerAFOIDs.get(key)
                            )));
            powerBVolt.put(key, Integer.parseInt(SNMPv3API.sendRequest(
                            upsIp,
                            "161",
                            gridPowerBVOIDs.get(key)
                            )));
        }

        powerBFreq = Integer.parseInt(SNMPv3API.sendRequest(
                    upsIp,
                    "161",
                    gridPowerBFOID));

        gridPowerSupply = (getPowerAUV() >= 198) 
            && (getPowerAVV() >= 198) 
            && (getPowerAWV() >= 198) 
            && (getPowerBUV() >= 198) 
            && (getPowerBVV() >= 198) 
            && (getPowerBWV() >= 198);
    }
    

    @Override
    public String toString() {
        return String.format("UPSStatus { UPS: %s, CaptuuredAt: %s, GridPowerA { U_F: %s, V_F: %s, W_F: %s, U_V: %s, V_V: %s, W_V: %s }, GridPowerB { Freq: %s, U_V: %s, V_V: %s, W_V: %s }, gridPowerSupply: %s}", 
                    getUPS(),
                    getCapturedAt(),
                    getPowerAUF(),
                    getPowerAVF(),
                    getPowerAWF(),
                    getPowerAUV(),
                    getPowerAVV(),
                    getPowerAWV(),
                    getPowerBF(),
                    getPowerBUV(),
                    getPowerBVV(),
                    getPowerBWV(),
                    getGridPowerSupply());
    }
}
