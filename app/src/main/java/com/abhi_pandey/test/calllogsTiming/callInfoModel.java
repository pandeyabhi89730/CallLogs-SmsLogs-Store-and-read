package com.abhi_pandey.test.calllogsTiming;

public class callInfoModel {
    private  String  startTime,endTime,Duration,Date,Type,number;

    public callInfoModel(String startTime, String endTime, String duration, String date, String type, String number) {
        this.startTime = startTime;
        this.endTime = endTime;
        Duration = duration;
        Date = date;
        Type = type;
        this.number = number;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
