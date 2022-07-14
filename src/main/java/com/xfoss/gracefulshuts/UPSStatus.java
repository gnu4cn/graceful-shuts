package com.xfoss.gracefulshuts;

// import java.util.Objects;
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
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

import com.xfoss.Utils.SNMPv3API;

enum Line {
    U,
    V,
    W,
}

@Entity
class PowerA {

    private @Id @GeneratedValue Long id;

    @Lob
    private EnumMap<Line, Integer> powerVolt = new EnumMap<>(Line.class);

    @Lob
    private EnumMap<Line, Integer> powerFreq = new EnumMap<>(Line.class);

    PowerA(){}

    PowerA(
            String upsIp,
            EnumMap<Line, String> gridPowerVOIDs, 
            EnumMap<Line, String> gridPowerFOIDs
          ) 
    {
        Set<Line> keys = gridPowerVOIDs.keySet();

        for(Line key: keys) {
            powerVolt.put(key, Integer.parseInt(SNMPv3API.sendRequest(
                            upsIp,
                            "161",
                            gridPowerVOIDs.get(key)
                            )));
            powerFreq.put(key, Integer.parseInt(SNMPv3API.sendRequest(
                            upsIp,
                            "161",
                            gridPowerFOIDs.get(key)
                            )));
        }

    }

    public int getPowerUV() {
        return powerVolt.get(Line.U);
    }

    public int getPowerVV() {
        return powerVolt.get(Line.V);
    }

    public int getPowerWV() {
        return powerVolt.get(Line.W);
    }

    public int getPowerUF() {
        return powerFreq.get(Line.U);
    }

    public int getPowerVF() {
        return powerFreq.get(Line.V);
    }

    public int getPowerWF() {
        return powerFreq.get(Line.W);
    }

    public boolean getGridSupplied () {
        return (getPowerUV() >= 198)
            && (getPowerVV() >= 198)
            && (getPowerWV() >= 198);
    }

    @Override
    public String toString() {
        return String.format("{UV: %s, VV: %s, WV: %s, UF: %s, VF: %s, WF: %s}",
                getPowerUV(),
                getPowerVV(),
                getPowerWV(),
                getPowerUF(),
                getPowerVF(),
                getPowerWF());
    }
}

@Entity
class PowerB {

    private @Id @GeneratedValue Long id;

    @Lob
    private EnumMap<Line, Integer> powerVolt = new EnumMap<>(Line.class);

    private int powerF;

    PowerB(){}

    PowerB(
            String upsIp, 
            EnumMap<Line, String> gridPowerVOIDs, 
            String fOID
          ) 
    {
        Set<Line> keys = gridPowerVOIDs.keySet();
        for(Line key: keys) {
            powerVolt.put(key, Integer.parseInt(SNMPv3API.sendRequest(
                            upsIp,
                            "161",
                            gridPowerVOIDs.get(key)
                            )));
            powerF = Integer.parseInt(SNMPv3API.sendRequest(
                        upsIp,
                        "161",
                        fOID));
        }

    }

    public int getPowerUV() {
        return powerVolt.get(Line.U);
    }

    public int getPowerVV() {
        return powerVolt.get(Line.V);
    }

    public int getPowerWV() {
        return powerVolt.get(Line.W);
    }

    public int getPowerF() {
        return powerF;
    }

    public boolean getGridSupplied () {
        return (getPowerUV() >= 198)
            && (getPowerVV() >= 198)
            && (getPowerWV() >= 198);
    }

    @Override
    public String toString() {
        return String.format("{UV: %s, VV: %s, WV: %s, F: %s}",
                getPowerUV(),
                getPowerVV(),
                getPowerWV(),
                getPowerF());
    }
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

    @OneToOne(cascade = {CascadeType.ALL})
    private PowerA powerA;


    @OneToOne(cascade = {CascadeType.ALL})
    private PowerB powerB;


    UPSStatus() {}

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

        powerA = new PowerA(upsIp, gridPowerAVOIDs, gridPowerAFOIDs);
        powerB = new PowerB(upsIp, gridPowerBVOIDs, gridPowerBFOID);

        gridPowerSupply = powerA.getGridSupplied() || powerB.getGridSupplied();
    }

    public Long getId() {
        return id;
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

    public PowerA getPowerA() {
        return powerA;
    }

    public PowerB getPowerB() {
        return powerB;
    }

    @Override
    public String toString() {
        return String.format("UPSStatus { UPS: %s, CaptuuredAt: %s, GridPowerA %s, GridPowerB %s, gridPowerSupply: %s}", 
                getUPS(),
                getCapturedAt(),
                getPowerA(),
                getPowerB(),
                getGridPowerSupply());
    }
}
