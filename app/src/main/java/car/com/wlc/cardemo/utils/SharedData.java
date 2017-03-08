package car.com.wlc.cardemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;

/**
 * Created by wlx on 2016/11/23.
 */

public class SharedData {

    public static void saveData(Context context,String name,String phone,String password,String userId,boolean status){

        SharedPreferences sp=context.getSharedPreferences("login.txt", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("name",name);
        editor.putString("phone",phone);
        editor.putString("password",password);
        editor.putString("userId",userId);
        editor.putBoolean("status",status);

        editor.commit();
    }
    public static Map<String,UserInfo> getData(Context context){

        SharedPreferences sp=context.getSharedPreferences("login.txt", Activity.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("name",""));
        userInfo.setUserPhone (sp.getString("phone", ""));
        userInfo.setUserPassword(sp.getString("password",""));
        userInfo.setStatus(sp.getBoolean("status",false));
        userInfo.setUserId(sp.getString("userId",""));
        Log.e("444", "getData: "+userInfo.getUserPhone());
        Map<String,UserInfo> map=new HashMap();
        map.put(Contact.USERINFO,userInfo);
        return map;
    }



}
