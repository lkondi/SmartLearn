package com.example.lkondilidis.smartlearn.Interfaces;

public enum StatusLectureFlag {

    SERVER_STATUS_ADD_LECTURE,
    SERVER_STATUS_GET_LECTURE;

    public final int flag;

    StatusLectureFlag() {
        this.flag = 1 << this.ordinal();
    }
}
