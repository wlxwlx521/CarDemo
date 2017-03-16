package car.com.wlc.cardemo.chatmessage.chat.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by wulixia on 2017/3/11.
 */
public class User implements Serializable {
    private int mId;
    private String mName;
    private int mIcon;

    public User() {
    }

    public User(int id, String name, int icon) {
        mId = id;
        mName = name;
        mIcon = icon;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mIcon=" + mIcon +
                '}';
    }
}
