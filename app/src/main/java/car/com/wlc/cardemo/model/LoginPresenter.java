package car.com.wlc.cardemo.model;

import android.text.TextUtils;
import android.util.Log;

import car.com.wlc.cardemo.utils.JsonData;


/**
 * Created by Administrator on 2017/2/27.
 */

public class LoginPresenter  {
    private final Xutils xutil;
    private final String URL="https://58.215.50.52:38443/openapi/openapi";
    private Login activity;
    public LoginPresenter(Login activity) {
        xutil = Xutils.getInstance();
        this.activity=activity;
    }

    /**
     *用户输入处理
     */
    public boolean checkUserInfo(User user){
        if(TextUtils.isEmpty(user.getName()) ||TextUtils.isEmpty(user.getPassword())) {
            return  false;
        }else {
            return true;
        }
    }

    /**
     * 登录业务处理
     */
    public void login(User user){
        Log.e("232", "User: " + user.getName());
       xutil.post(URL, getMap(user),  new Xutils.XCallBack() {
           @Override
           public void onResponse(String result) {
               String login = JsonData.isLogin(result);
               if (login.equals("Success")){
                   activity.loginSuccess();

               }else {
                  activity.loginFail();
               }
           }
       });
    }
    private String getMap(User user){
        String content ="{\"cmd\":\"signin\",\"auth\":{\"devKey\":\"8da849223f31407f8602dabb54ddeb92\"},\"params\":{\"userName\":\""+user.getName()+"\",\"password\":\""+user.getPassword()+"\"}}";

        Log.e("TAG", "getMap: " + ""+content);
        return content;
    }


}
