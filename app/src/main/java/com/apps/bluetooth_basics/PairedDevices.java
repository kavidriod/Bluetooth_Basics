package com.apps.bluetooth_basics;

import java.io.Serializable;

/**
 * Created by Kavitha on 6/21/2017.
 */

public class PairedDevices implements Serializable {


    public PairedDevices(String name, String macAddress) {
        this.name = name;
        this.macAddress = macAddress;
    }

    private String name,macAddress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
