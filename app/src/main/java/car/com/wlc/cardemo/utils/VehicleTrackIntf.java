package car.com.wlc.cardemo.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/17.
 */

public class VehicleTrackIntf implements Serializable {
    private String totalMileAge;
    private String totalFuelAge;
    private String averageFuel;
    private String recUid;
    private String startTime;
    private String endTime;
    private String startLocation;
    private String endLocation;
    private double startLng;
    private double startLat;
    private double endLng;
    private double endLat;
    private String mileAgep;
    private String resultNote;

    @Override
    public String toString() {
        return "VehicleTrackIntf{" +
                "totalMileAge='" + totalMileAge + '\'' +
                ", totalFuelAge='" + totalFuelAge + '\'' +
                ", averageFuel='" + averageFuel + '\'' +
                ", recUid='" + recUid + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", startLng=" + startLng +
                ", startLat=" + startLat +
                ", endLng=" + endLng +
                ", endLat=" + endLat +
                ", mileAgep='" + mileAgep + '\'' +
                ", resultNote='" + resultNote + '\'' +
                '}';
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public String getTotalMileAge() {
        return totalMileAge;
    }

    public void setTotalMileAge(String totalMileAge) {
        this.totalMileAge = totalMileAge;
    }

    public String getTotalFuelAge() {
        return totalFuelAge;
    }

    public void setTotalFuelAge(String totalFuelAge) {
        this.totalFuelAge = totalFuelAge;
    }

    public String getRecUid() {
        return recUid;
    }

    public void setRecUid(String recUid) {
        this.recUid = recUid;
    }

    public String getAverageFuel() {
        return averageFuel;
    }

    public void setAverageFuel(String averageFuel) {
        this.averageFuel = averageFuel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getStartLng() {
        return startLng;
    }

    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getEndLng() {
        return endLng;
    }

    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public String getMileAgep() {
        return mileAgep;
    }

    public void setMileAgep(String mileAgep) {
        this.mileAgep = mileAgep;
    }
}
