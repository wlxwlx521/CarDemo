package car.com.wlc.cardemo.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Administrator on 2016/12/15.
 */

public class DateUtils {


    /**
     *
     * @param time currentTime
     * @param i    you want reduce day
     * @param df  dateFormat
     * @return
     */
    public static String  addTime(String time,int i,DateFormat df){

        try {
            Date date= df.parse(time);
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(date);
            //加天
            calendar.add(Calendar.DAY_OF_MONTH,i);
            return df.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
    static DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateFormat format=new SimpleDateFormat("HH:mm");
    public static  String convertToTime(String time){

        try {
            Date date = df.parse(time);
            String s = format.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getTime(String startTime,String endTime){
    try {
        String time1 = convertToTime(startTime);
        String time2 = convertToTime(endTime);
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        long start = date1.getTime();
        long end = date2.getTime();
        long ss = (end - start) / (1000);//共计秒数

        int hh= (int) (ss/3600);//共计小时
        int h= (int) (ss%3600);
        Log.e("1232", "getTime: " + hh);
        int  mm=0;
        if (h>=60){
            mm = h / 60;//共计分钟
        }
        if (hh>0 && mm>0){
            return "停留"+hh+"小时"+mm+"分钟";
        }if (hh<0 && mm>0){
            return "停留"+mm+"分钟";
        }
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return null;
}
    /**
     *
     * @param time
     * @param i
     * @param df
     * @return
     */
    public static String  desTime(String time,int i,DateFormat df){
        try {

            long dif=df.parse(time).getTime()-i*86400*1000;//减一天
            Date date=new Date();
            date.setTime(dif);
            return  df.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
    private static  DateFormat dateFormat =new SimpleDateFormat("yyyy-MM");
    public static  String addMonth(String time){
        try {
            Date date = dateFormat.parse(time);
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(date);
            //加一个月
            calendar.add(Calendar.MONTH,1);
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static  String desMonth(String time){
        try {
            Date date = dateFormat.parse(time);
            Calendar calendar =Calendar.getInstance();
            calendar.setTime(date);
            //加一个月
            calendar.add(Calendar.MONTH,-1);
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


}
