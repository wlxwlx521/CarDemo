package car.com.wlc.cardemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

import car.com.wlc.cardemo.App;
import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;

public class SettingActivity extends AppActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        Toolbar mToolBar = (Toolbar) findViewById(R.id.setting_toolbar);
       findViewById(R.id.setting_usersetting).setOnClickListener(this);
        mToolBar.setTitle("系统设置");
        mToolBar.setNavigationIcon(R.mipmap.em_mm_title_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         findViewById(R.id.unregis_layout).setOnClickListener(this);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unregis_layout :
                logOff();
                break;
        }
    }

    private void logOff() {
        Map<String, UserInfo> data = SharedData.getData(SettingActivity.this);
        UserInfo userInfo = data.get(Contact.USERINFO);
        if (IsNetwork.isNetworkConnected(SettingActivity.this)){
            RequestParams requestParams =new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);
            requestParams.setBodyContent("{\"cmd\":\"signout\",\"auth\":{\"devKey\":\"8da849223f31407f8602dabb54ddeb92\"},\"params\":{\"userId\":\""+userInfo.getUserId()+"\"}}");
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("TAG", "onSuccess: " + result);
                    UserInfo info = JsonData.getStatu(result);

                    if (info.getResultNote().equals("Success")){
                        App.destoryActivity();
                        startActivity(new Intent(SettingActivity.this,LoginActivity.class));
                        SharedData.saveData(SettingActivity.this,"","","","",false);

                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("141", "onError: " + ex.getMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }else {
            finish();
            SharedData.saveData(SettingActivity.this,"","","","",false);
        }

    }
}
