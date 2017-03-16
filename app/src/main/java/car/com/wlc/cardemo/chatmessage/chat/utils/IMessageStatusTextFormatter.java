package car.com.wlc.cardemo.chatmessage.chat.utils;

/**
 * Created by wulixia on 2017/3/11.
 */
public interface IMessageStatusTextFormatter {
    /**
     * Return status text depend on message status and sender
     * @param status message status
     * @param isRightMessage Whether sender is right or not
     * @return message status text
     */
    String getStatusText(int status, boolean isRightMessage);
}
