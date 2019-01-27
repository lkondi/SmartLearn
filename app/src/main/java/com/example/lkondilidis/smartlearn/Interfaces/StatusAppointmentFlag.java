package com.example.lkondilidis.smartlearn.Interfaces;

public enum StatusAppointmentFlag {

    STATUS_YOUR_APPOINTMENT_FLAG,
    STATUS_USER_APPOINTMENT_FLAG;

    public final int flag;

    StatusAppointmentFlag() {
        this.flag = 1 << this.ordinal();
    }
}
