package com.xfoss.gracefulshuts;

import java.util.Objects;
import java.util.UUID;
import java.net.InetAddress;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.xfoss.Utils.SNMPv3API;


@Entity
public class UPS implements Serializable {

    // 
    // https://blog.csdn.net/zlzl8885/article/details/78203295
    // Hibernate 有二级缓存， 缓存会将对象写进硬盘。就必须序列化。以及兼容对象在网络钟的传输。
    //
    @Id 
    @Column(name = "id", unique = true, length = 16, nullable = false)
    private UUID id = UUID.randomUUID();

    private String name;
    private final static String nameOid = "1.3.6.1.2.1.1.5.0";
    private final static String runningTimeOid = "1.3.6.1.2.1.1.3.0";
    private String runningTime;
    private String nameFQDN;
    private InetAddress ipAddress;

    public UPS(){}

    public UPS(String UPSIpAddress) {

        try {
            ipAddress = InetAddress.getByName(UPSIpAddress);
            nameFQDN = ipAddress.getHostName();
            name = SNMPv3API.sendRequest(
                    UPSIpAddress,
                    "161",
                    nameOid);
            runningTime = SNMPv3API.sendRequest(
                    UPSIpAddress,
                    "161",
                    runningTimeOid);
        } catch (Exception e) { e.printStackTrace(); }
    }

    //
    // https://stackoverflow.com/questions/24936636/while-using-spring-data-rest-after-migrating-an-app-to-spring-boot-i-have-obser
    // 这些 getters 决定了返回的 JSON 包含哪些字段
    //
    public String getName() {
        return name;
    }

    public String getNameFQDN() {
        return nameFQDN;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public UUID getId() {
        return id;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setNameFQDN(String newNameFQDN) {
        nameFQDN = newNameFQDN;
    }

    public void setIpAddress(String newIpAdress) {
        try {
            ipAddress = InetAddress.getByName(newIpAdress);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public boolean equals (Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof UPS) ) return false;

        UPS ups = (UPS) o;
        return Objects.equals (this.id, ups.id)
            && Objects.equals (this.name, ups.name)
            && Objects.equals (this.nameFQDN, ups.nameFQDN)
            && Objects.equals (this.ipAddress, ups.ipAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameFQDN, ipAddress.getHostAddress());
    }

    @Override
    public String toString() {
        return String.format("UPS {id=%s, name=%s, nameFQDN=%s, ipAddress=%s, runningTime=%s}", 
                id,
                name,
                nameFQDN,
                this.ipAddress.getHostAddress(),
                runningTime
                );
    }
}
