package car.com.wlc.cardemo.utils.city;

import java.io.Serializable;

public class CitySortModel implements Serializable {

    private String cityPinyin;
    private String cityName;
    private String firstPinYin;
    private String logopath;
    private String imageUrl;
    private String displacementList;
    private String id;
    private int transmissionType;

    public CitySortModel() {
    }

    public CitySortModel(String cityName) {
        this.cityName = cityName;
    }

    public int getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(int transmissionType) {
        this.transmissionType = transmissionType;
    }


    public String getDisplacementList() {
        return displacementList;
    }

    public void setDisplacementList(String displacementList) {
        this.displacementList = displacementList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLogopath() {
        return logopath;
    }

    public void setLogopath(String logopath) {
        this.logopath = logopath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityPinyin() {
        return cityPinyin;
    }

    public void setCityPinyin(String cityPinyin) {
        this.cityPinyin = cityPinyin;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFirstPinYin() {
        firstPinYin=cityPinyin.substring(0,1);
        return firstPinYin;
    }


}
