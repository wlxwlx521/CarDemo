package car.com.wlc.cardemo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/12.
 */

public class IsPhone {
    /**
     * 验证手机号码
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean checkPhoneNumber(String phoneNumber){
        Pattern pattern= Pattern.compile("^1[0-9]{10}$");
        Matcher matcher=pattern.matcher(phoneNumber);
        return matcher.matches();
    }
   public static  boolean checkPassWord(String passWord){

       Pattern pattern= Pattern.compile("^[^\\s]{6,20}$");
       Matcher matcher = pattern.matcher(passWord);
      return matcher.matches();
   }
    public static boolean  IsVehicleNumber(String vehicleNumber){
        String express = "@\"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}[黑黄蓝绿]{1}$\"";
        Pattern pattern= Pattern.compile(express);
        Matcher matcher = pattern.matcher(vehicleNumber);
        return matcher.matches();
    }
}
