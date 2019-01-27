package com.example.lkondilidis.smartlearn.Interfaces;

public enum StatusRatingFlag {

    STATUS_YOUR_RATINGS,
    STATUS_USER_RATINGS;

    public final int flag;

    StatusRatingFlag() {
        this.flag = 1 << this.ordinal();
    }
}
