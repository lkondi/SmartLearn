package com.example.lkondilidis.smartlearn.Interfaces;

import com.example.lkondilidis.smartlearn.model.User;

public enum StatusLectureFlag {

    SERVER_STATUS_ADD_LECTURE,
    SERVER_STATUS_GET_LECTURE,
    SERVER_STATUS_FIND_LECTURE,
    SERVER_STATUS_ADD_APPOINTMENT;

    public final int flag;

    StatusLectureFlag() {
        this.flag = 1 << this.ordinal();
    }
}
