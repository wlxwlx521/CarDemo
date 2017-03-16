package car.com.wlc.cardemo.chatmessage.chat.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Created by wulixia on 2017/3/11.
 */
public class TimeUtils {

    /***
     * Return formatted text of calendar
    * @param calendar Calendar object to format
    * @param format format text
    * @return formatted text
    */
    public static String calendarToString(Calendar calendar, String format) {
        if (format == null) {
            format = "HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calendar.getTime());
    }

    /**
     * Return time difference days
     * @param prev previous date
     * @param target target date
     * @return time difference days
     */
    public static int getDiffDays(Calendar prev, Calendar target) {
        long timeDiff = prev.getTimeInMillis() - target.getTimeInMillis();
        int millisOfDay = 1000 * 60 * 60 * 24;
        return (int)(timeDiff / millisOfDay);
    }
}
