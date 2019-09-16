package com.train;

public class Alarms {



    String ID;
    String alarmName;
    String alarmTime;
    String trainTime;
    String station;

    public Alarms(String ID, String alarmName, String alarmTime, String trainTime, String station) {
        this.ID= ID;
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
        this.trainTime = trainTime;
        this.station = station;
    }

    public Alarms(){};

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(String trainTime) {
        this.trainTime = trainTime;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }



}
