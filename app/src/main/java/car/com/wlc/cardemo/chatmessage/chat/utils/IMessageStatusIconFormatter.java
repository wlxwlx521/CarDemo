package car.com.wlc.cardemo.chatmessage.chat.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by wulixia on 2017/3/11.
 */
public interface IMessageStatusIconFormatter {
    /**
     * Return icon depend on message status and sender
     * @param status message status
     * @param isRightMessage Whether sender is right or not
     * @return status icon image
     */
    Drawable getStatusIcon(int status, boolean isRightMessage);
}
