package com.example.app_02.entity;

public class Record {
    private String startTime;
    private String endTime;
    private String record;

    public Record() {
    }

    public Record(String startTime, String endTime, String record) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.record = record;
    }

    /**
     * 获取
     *
     * @return startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置
     *
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取
     *
     * @return endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置
     *
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取
     *
     * @return record
     */
    public String getRecord() {
        return record;
    }

    /**
     * 设置
     *
     * @param record
     */
    public void setRecord(String record) {
        this.record = record;
    }

    public String toString() {
        return "startTime: " + startTime + ", endTime: " + endTime + "\nrecord: " + record + "\n";
    }
}
