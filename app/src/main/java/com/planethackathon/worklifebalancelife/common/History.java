package com.planethackathon.worklifebalancelife.common;

public class History {
    private String date;
    private String startTime;
    private String endTime;
    private String tag;
    private Long interval;

    public History(String date, String startTime, String endTime, String tag, Long interval) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tag = tag;
        this.interval = interval;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "History{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", tag='" + tag + '\'' +
                ", interval=" + interval +
                '}';
    }
}
