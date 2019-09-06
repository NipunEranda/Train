package com.train;

public class TrainTimeTable {

    String timeTableName;
    int startStation;
    int endStation;
    String arrivalTime;
    String departTime;
    String trainDate;
    int trainId;

    public TrainTimeTable() {
    }

    public TrainTimeTable(String timeTableName, int startStation, int endStation, String arrivalTime, String departTime, String trainDate, int trainId) {
        this.timeTableName = timeTableName;
        this.startStation = startStation;
        this.endStation = endStation;
        this.arrivalTime = arrivalTime;
        this.departTime = departTime;
        this.trainDate = trainDate;
        this.trainId = trainId;
    }


    public String getTimeTableName() {
        return timeTableName;
    }

    public void setTimeTableName(String timeTableName) {
        this.timeTableName = timeTableName;
    }

    public int getStartStation() {
        return startStation;
    }

    public void setStartStation(int startStation) {
        this.startStation = startStation;
    }

    public int getEndStation() {
        return endStation;
    }

    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }
}
