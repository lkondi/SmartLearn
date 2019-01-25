package com.example.lkondilidis.smartlearn.Interfaces;

public enum StatusUserFlag {

    SERVER_STATUS_UPDATE_USER,
    SERVER_STATUS_ADD_USER,
    SERVER_STATUS_GET_USERS,
    SERVER_STATUS_REGISTER_USER,
    SERVER_STATUS_LOGIN_USER;

    public final int flag;

    StatusUserFlag() {
        this.flag = 1 << this.ordinal();
    }
}
