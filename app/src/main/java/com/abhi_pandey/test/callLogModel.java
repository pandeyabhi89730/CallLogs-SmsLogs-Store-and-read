package com.abhi_pandey.test;

public class callLogModel {

    private String  id,callName, callNumber, callStartTime,callEndTime, callDuration, callType,callDate;


    public callLogModel(String id,String callName, String callNumber, String callStartTime, String callEndTime, String callDuration, String callType, String callDate) {
        this.id = id;
        this.callName = callName;
        this.callNumber = callNumber;
        this.callStartTime = callStartTime;
        this.callEndTime = callEndTime;
        this.callDuration = callDuration;
        this.callType = callType;
        this.callDate = callDate;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(String callStartTime) {
        this.callStartTime = callStartTime;
    }

    public String getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(String callEndTime) {
        this.callEndTime = callEndTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }
}
