package car.com.wlc.cardemo.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.javaBean.PressDataBean;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.javaBean.VehicleFuelAna;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;
import car.com.wlc.cardemo.utils.city.CitySortModel;

/**
 * Created by Administrator on 2016/12/12.
 */

public class JsonData {
    public static String isLogin(String json){
        try {
            JSONObject object = new JSONObject(json);
            String resultNote = object.getString("resultNote");
            return resultNote;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static UserInfo getStatu(String json) {
        try {
            JSONObject object = new JSONObject(json);
            UserInfo userInfo = new UserInfo();
            userInfo.setResultNote(object.getString("resultNote"));
            String cmd = object.getString("cmd");
            if (cmd.equals("signout")) {
                return userInfo;
            }
            if (cmd.equals("signin") || cmd.equals("register")) {
                userInfo.setUserId(object.getJSONObject("detail").getString("userId"));
                return userInfo;
            }
            if (cmd.equals("addVehicle")) {
                JSONObject detail = object.getJSONObject("detail");
                userInfo.setBindToUser(detail.getBoolean("bindToUser"));
                userInfo.setObjId(detail.getString("objId"));
                Log.e("0000", "getStatu: " + userInfo.toString());
                return userInfo;
            }
            if (cmd.equals("delVehicle")) {
                return userInfo;
            }
            if (cmd.equals("modVehicelInfo")) {
                return userInfo;
            }
            return userInfo;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CitySortModel> getVehicle(String json) {
        List<CitySortModel> mList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            String cmd = object.getString("cmd");
            JSONArray jsonArray = object.getJSONObject("detail").getJSONArray("dataList");
            if (cmd.equals("vehicleBrandInfoV3")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject dataList = jsonArray.getJSONObject(i);
                    CitySortModel city = new CitySortModel();
                    city.setId(dataList.getString("id"));
                    city.setCityName(dataList.getString("name"));
                    city.setLogopath(dataList.getString("logopath"));
                    mList.add(city);
                }
                return mList;
            }
            if (cmd.equals("vehicleStyleInfoV3")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject dataList = jsonArray.getJSONObject(i);
                    CitySortModel city = new CitySortModel();
                    city.setId(dataList.getString("styleId"));
                    city.setCityName(dataList.getString("styleName"));
                    city.setDisplacementList(dataList.getString("displacement"));
                    city.setTransmissionType(dataList.getInt("transmissionType"));
                    mList.add(city);

                }
                return mList;
            }
            if (cmd.equals("vehicleProductInfoV3")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject dataList = jsonArray.getJSONObject(i);
                    CitySortModel city = new CitySortModel();
                    city.setId(dataList.getString("id"));
                    city.setCityName(dataList.getString("name"));
                    city.setImageUrl(dataList.getString("imageUrl"));
                    mList.add(city);
                }

                return mList;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static VerInfoListBean getVehicleInfo(String json) {

        try {
            JSONObject object = new JSONObject(json);
            String resultNote = object.getString("resultNote");
            VerInfoListBean bean = new VerInfoListBean();
            bean.setResultNote(resultNote);
            JSONObject ob = object.getJSONObject("detail");
            bean.setProductId(ob.getString("productId"));
            bean.setBrandId(ob.getString("brandId"));
            bean.setObjId(ob.getString("objId"));
            bean.setLicensePlateNo(ob.getString("licensePlateNo"));
            bean.setBrandName(ob.getString("brandName"));
            bean.setProductName(ob.getString("productName"));
            bean.setSeriesName(ob.getString("seriesName"));
            bean.setColor(ob.getString("color"));
            bean.setFuelType(ob.getString("fuelType"));
            bean.setDisplacement(ob.getString("engineDisplacement"));
            bean.setEmissionStd(ob.getInt("emissionStd"));
            bean.setProduceTime(ob.getString("produceTime"));
            bean.setRegistTime(ob.getString("registTime"));
            bean.setRegistAgency(ob.getString("registAgency"));
            bean.setServiceType(ob.getInt("serviceType"));
            bean.setVin(ob.getString("vin"));
            bean.setTransmissionType(ob.getInt("transmissionType"));
            bean.setVehicleType(ob.getString("vehicleType"));
            Log.e("1345", "getVehicleInfo: " + bean.getTransmissionType());
//                bean.setVehicleType();
            Log.e("134", "getVehicleInfo: " + ob.getString("vehicleType"));
            bean.setOwner(ob.getString("ownerName"));
            bean.setOwnerTelephone(ob.getString("ownerTelephone"));

            return bean;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<VerInfoListBean> getVehicleInfoList(String json) {
        List<VerInfoListBean> mList = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(json);
            String cmd = object.getString("cmd");
            String resultNote = object.getString("resultNote");
            int totalRecordNum = object.getInt("totalRecordNum");
            VerInfoListBean bean = new VerInfoListBean();
            bean.setResultNote(resultNote);
            bean.setTotalRecordNum(totalRecordNum);
            mList.add(bean);
            if (resultNote.equals("Success")) {
                JSONObject detail = object.getJSONObject("detail");
                JSONArray jsonArray = detail.getJSONArray("objList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject ob = jsonArray.getJSONObject(i);
                    VerInfoListBean listBean = new VerInfoListBean();
                    listBean.setBrandId(ob.getString("brandName"));
                    listBean.setLicensePlateNo(ob.getString("lpno"));
                    listBean.setPicture(ob.getString("picture"));
                    listBean.setObjId(ob.getString("objId"));
                    listBean.setIsBind(ob.getInt("isBind"));
                    listBean.setLongitude(ob.getString("longitude"));
                    listBean.setLatitude(ob.getString("latitude"));
                    mList.add(listBean);
                }
            }
            return mList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static List<PressDataBean> getPressDataBean(String json) {
        List<PressDataBean> mList = new ArrayList<>();
        try {
            PressDataBean bean = null;
            JSONObject object = new JSONObject(json);
            String resultNote = object.getString("resultNote");
            Log.e("45t5t", "getPressDataBean: " + resultNote);
            if (resultNote.equals("Success")) {
                JSONArray array = object.getJSONObject("detail").getJSONArray("pointdata");

                double speed = 0;
                for (int i = 0; i < array.length(); i++) {
                    JSONArray poindata = array.getJSONArray(i);

                    double currentSpeed = (double) poindata.get(2);
                    if (speed == 0 && currentSpeed != 0) {
                        bean = new PressDataBean();
                        bean.setStartLongitude((Double) poindata.get(0));
                        bean.setStartLatitude((Double) poindata.get(1));
                        bean.setStartTime((String) poindata.get(7));
                        speed = 1;
                        continue;
                    }
                    if (speed != 0 && currentSpeed == 0) {
                        bean.setEndLongitude((Double) poindata.get(0));
                        bean.setEndLatitude((Double) poindata.get(1));
                        bean.setEndTime((String) poindata.get(7));
                        speed = 0;
                        mList.add(bean);
                        continue;
                    }

                }
                return mList;
            } else
                return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static VehicleTrackIntf getVehicleTrackIntf(String json){
        try {
            JSONObject object = new JSONObject(json);
            VehicleTrackIntf intf = new VehicleTrackIntf();
            JSONObject detail = object.getJSONObject("detail");

            intf.setResultNote(object.getString("resultNote"));
            intf.setTotalFuelAge(detail.getString("totalFuelAge"));
            intf.setTotalMileAge(detail.getString("totalMileAge"));
            intf.setAverageFuel(detail.getString("averageFuel"));
            JSONArray segList = detail.getJSONArray("segList");
            for (int i = 0; i <segList.length(); i++) {
                JSONObject ob = segList.getJSONObject(i);
                intf.setRecUid(ob.getString("recUid"));
                intf.setStartTime(ob.getString("startTime"));
                intf.setEndTime(ob.getString("endTime"));
                intf.setStartLocation(ob.getString("startLocation"));
                intf.setEndLocation(ob.getString("endLocation"));
                intf.setStartLng(ob.getDouble("startLng"));
                intf.setStartLat(ob.getDouble("startLat"));
                intf.setEndLng(ob.getDouble("endLng"));
                intf.setEndLat(ob.getDouble("endLat"));
                intf.setMileAgep(ob.getString("mileAge"));
            }
            return intf;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static VehicleFuelAna getVehicleFuelAna(String json){
        try {

            JSONObject object = new JSONObject(json);
            VehicleFuelAna ana = new VehicleFuelAna();
            ana.setResultNote(object.getString("resultNote"));
            ana.setMonthFuelConsumption(object.getString("monthFuelConsumption"));
            ana.setMonthSpeed(object.getDouble("monthSpeed"));
           ana.setMonthMile(object.getDouble("monthMile"));
            ana.setMonthDuration(object.getDouble("monthDuration"));
           ana.setMonthFuel(object.getDouble("monthFuel"));
           ana.setMonthFuelCost(object.getString("monthFuelCost"));
           ana.setDefeatSameDisplRate(object.getDouble("defeatSameDisplRate"));
            ana.setDefeatSameStyleRate(object.getDouble("defeatSameStyleRate"));
           return ana;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
