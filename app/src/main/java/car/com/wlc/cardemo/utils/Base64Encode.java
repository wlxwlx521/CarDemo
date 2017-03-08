package car.com.wlc.cardemo.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wlx on 2016/11/24.
 */

public class Base64Encode {
    public static String encryption(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(content.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                stringBuffer.append(b);
            }
            String encodeToString = Base64.encodeToString(stringBuffer.toString().getBytes(), Base64.DEFAULT);
            return encodeToString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
