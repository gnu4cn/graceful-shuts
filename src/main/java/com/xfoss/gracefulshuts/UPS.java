package com.xfoss.gracefulshuts;

import java.util.Objects;
import java.net.InetAddress;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class UPS implements Serializable {

    // 
    // https://blog.csdn.net/zlzl8885/article/details/78203295
    // Hibernate 有二级缓存， 缓存会将对象写进硬盘。就必须序列化。以及兼容对象在网络钟的传输。
    //
    private @Id @GeneratedValue Long id;
    private String name;
    private String nameFQDN;
    private InetAddress ipAddress;

    public UPS(){}

    public UPS(String UPSName, String UPSNameFQDN, String UPSIpAddress) {

        try {
            name = UPSName;
            nameFQDN = UPSNameFQDN;
            ipAddress = InetAddress.getByName(UPSIpAddress);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public String getName() {
        return name;
    }

    public String getNameFQDN() {
        return nameFQDN;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
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
        return String.format("UPS {id=%s, name=%s, nameFQDN=%s, ipAddress=%s}", 
                id,
                name,
                nameFQDN,
                this.ipAddress.getHostAddress()
                );
    }
}
