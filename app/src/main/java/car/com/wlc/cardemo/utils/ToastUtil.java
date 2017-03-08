package car.com.wlc.cardemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/13.
 */

public class ToastUtil {
    public static void toastUtils(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
