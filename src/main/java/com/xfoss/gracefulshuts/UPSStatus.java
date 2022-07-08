package com.xfoss.gracefulshuts;

import java.util.Objects;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.xfoss.Utils.SNMPv3API;

abstract class GridPower {
    private int uVoltage;
    private int vVoltage;
    private int wVoltage;

    private String uVoltOID;
    private String vVoltOID;
    private String wVoltOID;

    public void setUVoltage(int v) {
        uVoltage = v;
    }

    public void setVVoltage(int v) {
        vVoltage = v;
    }

    public void setWVoltage(int v) {
        wVoltage = v;
    }

    public void setUVoltOID(String oid) {
        uVoltOID = oid;
    }

    public void setVVoltOID(String oid) {
        vVoltOID = oid;
    }

    public void setWVoltOID(String oid) {
        wVoltOID = oid;
    }

    public int getUVoltagle() {
        return uVoltage;
    }

    public int getVVoltage() {
        return vVoltage;
    }

    public int getWVoltage() {
        return wVoltage;
    }

    public String getUVoltOID() {
        return uVoltOID;
    }

    public String getVVoltOID() {
        return vVoltOID;
    }

    public String getWVoltOID() {
        return wVoltOID;
    }

    public GridPower(UPS ups, String uVOID, String vVOID, String wVOID) {
        uVoltage = u;
        vVoltage = v;
        wVoltage = w;
    }
}

class GridPowerA extends GridPower {
    private int uFreq;
    private int vFreq;
    private int wFreq;

    public void setUFreq(int f) {
        uFreq = f;
    }

    public void setVFreq(int f) {
        vFreq = f;
    }

    public void setWFreq(int f) {
        wFreq = f;
    }

    public int getUFreq() {
        return uFreq;
    }

    public int getVFreq() {
        return vFreq;
    }

    public int getWFreq() {
        return wFreq;
    }
    
    public GridPowerA(int uF, int vF, int wF, int uV, int vV, int wV) {
        super(uV, vV, wV);
        uFreq = uF;
        vFreq = vF;
        wFreq = wF;
    }

    @Override
    public String toString() {
        return String.format("GridPowerA {uFreq=%s, vFreq=%s, wFreq=%s, uVoltage=%s, vVoltage=%s, wVoltage=%s}", 
                uFreq,
                vFreq,
                wFreq,
                getUVoltagle(),
                getVVoltage(),
                getWVoltage());
    }
}

class GridPowerB extends GridPower {
    private int freq;

    public void setFreq(int f) {
        freq = f;
    }

    public int getFreq() {
        return freq;
    }

    public GridPowerB(int f, int uV, int vV, int wV) {
        super(uV, vV, wV);
        freq = f;
    }

    @Override
    public String toString() {
        return String.format("GridPowerB {freq=%s, uVoltage=%s, vVoltage=%s, wVoltage=%s}", 
                freq,
                getUVoltagle(),
                getVVoltage(),
                getWVoltage());
    }
}

@Entity
public class UPSStatus {

    private @Id @GeneratedValue Long id;
    private UPS ups;
    private Date captueredAt;
    private boolean gridPowerSupply;
    private GridPowerA gridPowerA;
    private GridPowerB gridPowerB;

    UPSStatus() {}

    UPSStatus(UPS specifiedUPS) {
        ups = specifiedUPS;
        captueredAt = new Date(System.currentTimeMillis());
    }
}
