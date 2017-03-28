package car.com.wlc.cardemo.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.utils.ExitApp;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.view.JellyInterpolator;


/**
 * Created by Administrator on 2016/12/10.
 */

public class LoginActivity extends AppActivity implements View.OnClickListener {
    private EditText editName, editPassword;
    private View progressBar;
    private final int SUCCESS = 100;
    private Context context = LoginActivity.this;
    private final int FALSE = 101;
//    private Handler handler =new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case SUCCESS :
//                startActivity(new Intent(context, MainActivity.class));
//                    progressBar.setVisibility(View.GONE);
//                    finish();
//                    break;
//                case  FALSE :
//                    Toast.makeText(context,"用户名或者密码错误",Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//                    break;
//
//            }
//        }
//    };

    // {"cmd":"signin","auth":{"devKey":"8da849223f31407f8602dabb54ddeb92"},"params":{"userName":""+name+"\",\"password\":\""+password+"\"}}"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
    }

    protected void initView() {
        findViewById(R.id.reg_btn).setOnClickListener(this);
        editName = ((EditText) findViewById(R.id.name_edit));
        editPassword = ((EditText) findViewById(R.id.password_edit));
        progressBar = findViewById(R.id.login_progress);
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.findpassword).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                progressBar.setVisibility(View.VISIBLE);
                progressAnimator(progressBar);
                final String name = editName.getText().toString();

                final String password = editPassword.getText().toString();
//                final String encodePass = Base64Encode.encryption(password);


                if (name.isEmpty()) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!TextUtils.isEmpty(name) && TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (IsNetwork.isNetworkConnected(context)) {
                    RequestParams requestParams = new RequestParams(Contact.URL);
                    requestParams.setAsJsonContent(true);
                    String url = "{\"cmd\":\"signin\",\"auth\":{\"devKey\":\"8da849223f31407f8602dabb54ddeb92\"},\"params\":{\"userName\":\"" + name + "\",\"password\":\"" + password + "\"}}";
                    Log.e("TAG", "onClick: " + url);
                    requestParams.setBodyContent(url);
                    Log.e("TAG", "onClick: " + requestParams.getBodyContent());
                    x.http().post(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {

                            UserInfo userInfo = JsonData.getStatu(result);

                            Log.e("TAG", "onSuccess: " + userInfo.toString());

                            if (userInfo.getResultNote().equals("Success")) {

                                SharedData.saveData(context, name, name, password, userInfo.getUserId(), true);
                                //  handler.sendEmptyMessageDelayed(SUCCESS,100);
                                startActivity(new Intent(context, MainActivity.class));
                                progressBar.setVisibility(View.GONE);
                                finish();
                            } else {
                                //  handler.sendEmptyMessageDelayed(FALSE,100);
                                Toast.makeText(context, "服务器忙，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.e("TAG", "onError: ." + ex.getMessage());

                            SharedData.saveData(context, "", "", "", "", false);
                            Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();

                            progressBar.setVisibility(View.GONE);


                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                } else {
                    Toast.makeText(this, "请检查网络状态！", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.reg_btn:
                startActivity(new Intent(this, RegActivity.class));
                break;
        }
    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(100);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //调用双击退出方法
            ExitApp.getIntance().exitBy2Click(this);
        }
        return true;
    }
}
