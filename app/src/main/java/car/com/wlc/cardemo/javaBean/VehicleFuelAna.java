package car.com.wlc.cardemo.javaBean;

/**
 * Created by Administrator on 2017/2/20.
 */

public class VehicleFuelAna {
    private String resultNote;
    private String monthFuelConsumption;
    private  double  monthSpeed;
    private double monthMile;
    private double monthDuration;
    private double monthFuel;
    private  String monthFuelCost;
    private double defeatSameStyleRate;
    private double defeatSameDisplRate;
    private int rapidAccNum;
    private int rapidDecNum;
    private int brakeNum;
    private int turnNum;

    public int getBrakeNum() {
        return brakeNum;
    }

    public void setBrakeNum(int brakeNum) {
        this.brakeNum = brakeNum;
    }

    public int getRapidDecNum() {
        return rapidDecNum;
    }

    public void setRapidDecNum(int rapidDecNum) {
        this.rapidDecNum = rapidDecNum;
    }

    public int getRapidAccNum() {
        return rapidAccNum;
    }

    public void setRapidAccNum(int rapidAccNum) {
        this.rapidAccNum = rapidAccNum;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public void setTurnNum(int turnNum) {
        this.turnNum = turnNum;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public String getMonthFuelConsumption() {
        return monthFuelConsumption;
    }

    public void setMonthFuelConsumption(String monthFuelConsumption) {
        this.monthFuelConsumption = monthFuelConsumption;
    }

    public double getMonthSpeed() {
        return monthSpeed;
    }

    public void setMonthSpeed(double monthSpeed) {
        this.monthSpeed = monthSpeed;
    }

    public double getMonthMile() {
        return monthMile;
    }

    public void setMonthMile(double monthMile) {
        this.monthMile = monthMile;
    }

    public double getMonthFuel() {
        return monthFuel;
    }

    public void setMonthFuel(double monthFuel) {
        this.monthFuel = monthFuel;
    }

    public String getMonthFuelCost() {
        return monthFuelCost;
    }

    public void setMonthFuelCost(String monthFuelCost) {
        this.monthFuelCost = monthFuelCost;
    }

    public double getMonthDuration() {
        return monthDuration;
    }

    public void setMonthDuration(double monthDuration) {
        this.monthDuration = monthDuration;
    }

    public double getDefeatSameStyleRate() {
        return defeatSameStyleRate;
    }

    public void setDefeatSameStyleRate(double defeatSameStyleRate) {
        this.defeatSameStyleRate = defeatSameStyleRate;
    }

    public double getDefeatSameDisplRate() {
        return defeatSameDisplRate;
    }

    public void setDefeatSameDisplRate(double defeatSameDisplRate) {
        this.defeatSameDisplRate = defeatSameDisplRate;
    }
}
