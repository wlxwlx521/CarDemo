package car.com.wlc.cardemo;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/12/12.
 */

public class App extends Application {
    private static Map<String,Activity> destoryMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);


}

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName,activity);
    }
    /**
     *销毁指定Activity
     */
    public static void destoryActivity() {
        Set<String> keySet=destoryMap.keySet();

        for (String key:keySet){
            destoryMap.get(key).finish();
            Log.e("1414", "destoryActivity: " +key);
        }
    }



}