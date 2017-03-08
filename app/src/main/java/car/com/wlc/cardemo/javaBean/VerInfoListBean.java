package car.com.wlc.cardemo.javaBean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/20.
 */
@Table(name = "VerInfoListBean")
public class VerInfoListBean implements Serializable {
    @Column(name = "brandId")
    private String brandId;
    @Column(name = "productId")
    private String productId;
    private String currentKm;
   private int totalRecordNum;
    private String seriesName;
    private int isBind;

    public int getIsBind() {
        return isBind;
    }

    public void setIsBind(int isBind) {
        this.isBind = isBind;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public int getTotalRecordNum() {
        return totalRecordNum;
    }

    public void setTotalRecordNum(int totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    public String getCurrentKm() {
        return currentKm;
    }

    public void setCurrentKm(String currentKm) {
        this.currentKm = currentKm;
    }

    @Override
    public String toString() {
        return "VerInfoListBean{" +
                "brandId='" + brandId + '\'' +
                ", productId='" + productId + '\'' +
                ", transmissionType=" + transmissionType +
                ", resultNote='" + resultNote + '\'' +
                ", objId='" + objId + '\'' +
                ", idName='" + idName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", productName='" + productName + '\'' +
                ", color='" + color + '\'' +
                ", licensePlateNo='" + licensePlateNo + '\'' +
                ", displacement='" + displacement + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", emissionStd=" + emissionStd +
                ", vehicleType='" + vehicleType + '\'' +
                ", owner='" + owner + '\'' +
                ", ownerTelephone='" + ownerTelephone + '\'' +
                ", serviceType=" + serviceType +
                ", vin='" + vin + '\'' +
                ", picture='" + picture + '\'' +
                ", registAgency='" + registAgency + '\'' +
                ", produceTime='" + produceTime + '\'' +
                ", registTime='" + registTime + '\'' +
                '}';
    }

    @Column(name = "transmissionType")
    private int transmissionType;


    public int getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(int transmissionType) {
        this.transmissionType = transmissionType;
    }

    @Column(name = "resultNote")
    private String resultNote;

    @Column(name = "objId" ,isId = true)
    private String objId;

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }
    //车辆别名
    @Column(name = "idName")
    private String idName;
    //车辆品牌
    @Column(name = "brandName")
    private String brandName;
    //车型
    @Column(name = "productName")
    private String productName;
    @Column(name = "color")
    private String color;
    //车牌号
    @Column(name = "lpno")
    private String licensePlateNo;
    //排量
    @Column(name = "displacement")
    private String displacement;
    //燃油类型
    @Column(name = "fuelType")
    private String fuelType;
    //排放标准
    @Column(name = "emissionStd")
    private int emissionStd;
    //登记车型
    @Column(name = "vehicleType")
    private String vehicleType;
    //车辆所有者
    @Column(name = "owner")
    private String owner;
    //所有者电话
    @Column(name = "ownerTelephone")
    private String ownerTelephone;
    //车辆的服务性质
    @Column(name = "serviceType")
    private int serviceType;
    //车架号
    @Column(name = "vin")
    private  String vin;
    //车辆照片
    @Column(name = "picture")
    private String picture;
    //上牌交管机构
    @Column(name = "registAgency")
    private String registAgency;
    //生产时间
    @Column(name = "produceTime")
    private String produceTime;
    //注册时间
    @Column(name = "registTime")
    private String registTime;

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(String produceTime) {
        this.produceTime = produceTime;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getLicensePlateNo() {
        return licensePlateNo;
    }

    public void setLicensePlateNo(String lpno) {
        this.licensePlateNo = lpno;
    }

    public int getEmissionStd() {
        return emissionStd;
    }

    public void setEmissionStd(int emissionStd) {
        this.emissionStd = emissionStd;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerTelephone() {
        return ownerTelephone;
    }

    public void setOwnerTelephone(String ownerTelephone) {
        this.ownerTelephone = ownerTelephone;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRegistAgency() {
        return registAgency;
    }

    public void setRegistAgency(String registAgency) {
        this.registAgency = registAgency;
    }
    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
