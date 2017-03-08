package car.com.wlc.cardemo.utils;

import android.app.Activity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/16.
 */

public class ExitApp {
    private boolean isExit;
    private static ExitApp exitApp;
    public static ExitApp getIntance(){
        if (exitApp==null){
            exitApp=new ExitApp();
        }
        return exitApp;
    }
    public void exitBy2Click(Activity context){
        Timer timer =null;
        if (isExit == false){
            // 准备退出
            isExit =true;
            ToastUtil.toastUtils(context,"再按一次退出程序");
            timer =new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;//取消退出
                }
            },2000);//如果2秒没有按下返回键，则启动定时器取消掉刚才执行的任务

        }else {
            System.exit(0);
        }
    }
}
