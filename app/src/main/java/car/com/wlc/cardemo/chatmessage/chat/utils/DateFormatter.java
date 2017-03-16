package car.com.wlc.cardemo.chatmessage.chat.utils;



import java.util.Calendar;

import static car.com.wlc.cardemo.chatmessage.chat.utils.TimeUtils.calendarToString;

/**
 * Created by wulixia on 2017/3/11.
 */
public class DateFormatter implements ITimeFormatter {
    @Override
    public String getFormattedTimeText(Calendar createdAt) {
        return calendarToString(createdAt, "yyyy/MM/dd");
    }
}
