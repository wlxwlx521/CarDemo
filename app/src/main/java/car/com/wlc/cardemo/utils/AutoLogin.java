package car.com.wlc.cardemo.utils;

import android.content.Context;

import java.util.Map;

import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;

/**
 * Created by Administrator on 2016/12/13.
 */

public class AutoLogin {
    private static AutoLogin autoLogin;
    public static AutoLogin getInstace(){
        if (autoLogin == null){
            autoLogin=new AutoLogin();
        }
        return autoLogin;
    }
    public static boolean getIsAutoLogin(Context context){
        Map<String, UserInfo> data = SharedData.getData(context);
        UserInfo userInfo = data.get(Contact.USERINFO);
        return userInfo.isStatus();
    }
}
