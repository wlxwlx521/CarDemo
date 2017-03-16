package car.com.wlc.cardemo.chatmessage.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Created by wulixia on 2017/3/11.
 */
public class DefaultTimeFormatter implements ITimeFormatter {
    @Override
    public String getFormattedTimeText(Calendar createdAt) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(createdAt.getTime());
    }
}
