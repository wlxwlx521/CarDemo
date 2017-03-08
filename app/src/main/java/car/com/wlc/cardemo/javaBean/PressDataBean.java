package car.com.wlc.cardemo.javaBean;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PressDataBean  {
    private double startLongitude;
    private double startLatitude;
    private String startTime;
    private double endLongitude;
    private double endLatitude;
    private String endTime;
    private int endDirection;
    private String startAddress;
    private String endAddress;
    private float distance;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getEndDirection() {
        return endDirection;
    }

    public void setEndDirection(int endDirection) {
        this.endDirection = endDirection;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "PressDataBean{" +
                "startLongitude=" + startLongitude +
                ", startLatitude=" + startLatitude +
                ", startTime='" + startTime + '\'' +
                ", endLongitude=" + endLongitude +
                ", endLatitude=" + endLatitude +
                ", endTime='" + endTime + '\'' +
                ", endDirection=" + endDirection +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                '}';
    }
}
