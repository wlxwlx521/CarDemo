package car.com.wlc.cardemo.chatmessage.chat.utils;

import java.util.Calendar;

/**
 * Created by wulixia on 2017/3/11.
 */
public interface ITimeFormatter {

    /**
     * Format the time text which is next to the chat bubble.
     * @param createdAt The time that message was created
     * @return Formatted time text
     */
    String getFormattedTimeText(Calendar createdAt);
}
