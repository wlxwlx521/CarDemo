package car.com.wlc.cardemo.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.AddCarActivity;
import car.com.wlc.cardemo.activity.LoginActivity;
import car.com.wlc.cardemo.activity.SettingActivity;
import car.com.wlc.cardemo.activity.UserVehicleListActivity;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;
import car.com.wlc.cardemo.utils.DatabaseOpenHelper;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;


public class UserFragment extends Fragment {

    private static UserFragment userFragment;
    private ImageView mSetting;
    private TextView mNike;
    private View addMyCar;
    private List<VerInfoListBean> mList=new ArrayList<>();


    public static UserFragment getInstance() {
        if (userFragment == null) {
            userFragment = new UserFragment();
        }
        return userFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSetting = ((ImageView) view.findViewById(R.id.user_image_setting));
        mNike = ((TextView) view.findViewById(R.id.user_text_nichen));
         addMyCar = view.findViewById(R.id.add_mycars);
        init();
    }

    private void init() {
        Map<String, UserInfo> data = SharedData.getData(getContext());
        final UserInfo userInfo = data.get(Contact.USERINFO);
        mNike.setText(userInfo.getUserPhone());
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),SettingActivity.class));

            }
        });

        addMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDate();
            }
        });
    }

    private void requestDate() {
        UserInfo userInfo = SharedData.getData(getContext()).get(Contact.USERINFO);
        if (! userInfo.isStatus()){
            showLoadDialog();
            return;
        }else {
            List<VerInfoListBean> list = null;
            try {
                list = DatabaseOpenHelper.getInstance().findAll(VerInfoListBean.class);
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (IsNetwork.isNetworkConnected(getContext())){
                RequestParams requestParams=new RequestParams(Contact.URL);
                requestParams.setAsJsonContent(true);
                requestParams.setBodyContent("{\"cmd\": \"userVehicleList\", \"auth\": { \"devKey\": \"8da849223f31407f8602dabb54ddeb92\", \"userName\": \""+userInfo.getUserName()+"\",\"password\": \""+userInfo.getUserPassword()+"\"}, \"params\": { \"objAuthType\": \"0\",\"pageNo\":0}}");
                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        List<VerInfoListBean> list = JsonData.getVehicleInfoList(result);

                        VerInfoListBean bean = list.get(0);

                        if (bean.getResultNote().equals("Success")){

                            try {
                                DatabaseOpenHelper.getInstance().delete(VerInfoListBean.class);
                                list.remove(0);
                                mList.clear();
                                mList.addAll(list);
                                DatabaseOpenHelper.getInstance().save(bean);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            startIntent();
                        }else {

                            showDialog();
                        }

                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e(getClass().getName(), "onError: " + ex.getMessage());

                    }
                    @Override
                    public void onCancelled(CancelledException cex) {

                    }
                    @Override
                    public void onFinished() {

                    }
                });
            }else {
                if (list!=null){
                    list.remove(0);
                    mList.clear();
                    mList.addAll(list);
                    startIntent();
                }
            }
        }


    }
    public void startIntent(){
        Intent intent=new Intent(getContext(), UserVehicleListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contact.USERCARKEY,(Serializable)mList);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void showLoadDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("提示").setMessage("亲爱的用户,目前您还没有登陆，是否登录").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        b.create().show();
    }

    private void showDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("提示").setMessage("亲爱的用户，检测到您未添加车辆，请先添加并绑定车辆").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(getContext(), AddCarActivity.class));
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        b.create().show();
    }

}
