package car.com.wlc.cardemo.chatmessage.chat.utils;



import java.util.Calendar;

/**
 * Created by wulixia on 2017/3/11.
 */
public class ChatBot {

    public static String talk(String username, String message) {
        String receive = message.toLowerCase();
        if (receive.contains("hello")) {
            String user = "";
            if (username != null) {
                user = " " + username;
            }
            return "Hello" + user + "!";
        } else if (receive.contains("hey")) {
            return "Hey " + username + "!";
        } else if (receive.startsWith("do ")) {
            return "Yes, I do.";
        } else if (receive.contains("time")) {
            return "It's " + TimeUtils.calendarToString(Calendar.getInstance(), null) + ".";
        } else if (receive.contains("today")) {
            return "It's " + TimeUtils.calendarToString(Calendar.getInstance(), "M/d(E)");

        } else {
            return "车马炮欢迎你的加入！";
        }
    }
}
