package com.abhi_pandey.test.firebase;

public class dbModelSMS {


    private String id,threadId, smsDatee, smsTime, smsName,smsAddress, smsType,smsContent;


    public dbModelSMS(String id, String threadId, String smsDatee, String smsTime, String smsName, String smsAddress, String smsType, String smsContent) {
        this.id = id;
        this.threadId = threadId;
        this.smsDatee = smsDatee;
        this.smsTime = smsTime;
        this.smsName = smsName;
        this.smsAddress = smsAddress;
        this.smsType = smsType;
        this.smsContent = smsContent;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getSmsDatee() {
        return smsDatee;
    }

    public void setSmsDatee(String smsDatee) {
        this.smsDatee = smsDatee;
    }

    public String getSmsTime() {
        return smsTime;
    }

    public void setSmsTime(String smsTime) {
        this.smsTime = smsTime;
    }

    public String getSmsName() {
        return smsName;
    }

    public void setSmsName(String smsName) {
        this.smsName = smsName;
    }

    public String getSmsAddress() {
        return smsAddress;
    }

    public void setSmsAddress(String smsAddress) {
        this.smsAddress = smsAddress;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }
}
