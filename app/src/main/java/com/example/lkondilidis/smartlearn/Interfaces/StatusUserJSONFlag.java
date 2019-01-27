package com.example.lkondilidis.smartlearn.Interfaces;

public enum StatusUserJSONFlag {

    STATUS_YOUR_USER_FLAG,
    STATUS_USER_USER_FLAG;

    public final int flag;

    StatusUserJSONFlag() {
        this.flag = 1 << this.ordinal();
    }
}
