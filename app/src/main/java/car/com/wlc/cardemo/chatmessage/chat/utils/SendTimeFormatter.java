package car.com.wlc.cardemo.chatmessage.chat.utils;

import java.util.Calendar;

/**
 * Created by wulixia on 2017/3/11.
 */
public class SendTimeFormatter implements ITimeFormatter {
    @Override
    public String getFormattedTimeText(Calendar createdAt) {
        return TimeUtils.calendarToString(createdAt, "HH:mm");
    }
}
