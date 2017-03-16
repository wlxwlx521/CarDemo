package car.com.wlc.cardemo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.LoginActivity;
import car.com.wlc.cardemo.activity.ShopActivity;
import car.com.wlc.cardemo.activity.ShopLoginActivity;
import car.com.wlc.cardemo.activity.ShopRegisterActivity;

import static android.R.attr.fragment;
import static android.content.ContentValues.TAG;
import static com.amap.api.mapcore.util.af.a.l;

/**
 * A simple {@link Fragment} subclass.
 * 商城个人中心界面
 */
public class PersonalCenterFragment extends Fragment {


    private static final int LOGIN = 1;
    private static final int REGISTER=2;
    private Handler mhandler;
    private TextView name;
    private TextView dengji;



    public PersonalCenterFragment() {
        // Required empty public constructor

    }

    public void setHandler(Handler handler) {
        mhandler = handler;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        name = ((TextView) view.findViewById(R.id.personal_center_name));
        dengji = ((TextView) view.findViewById(R.id.personal_center_dengji));



        view.findViewById(R.id.personal_center_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lyf", "onClick: 我的购物车");
                Message message = new Message();
                message.what=123;
                mhandler.sendMessage(message);

            }
        });
        view.findViewById(R.id.personal_center_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ShopLoginActivity.class);
                startActivityForResult(intent, LOGIN);

            }
        });
        view.findViewById(R.id.personal_center_register).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ShopRegisterActivity.class);
                startActivityForResult(intent, REGISTER);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOGIN:
                if (resultCode==ShopLoginActivity.LOGINRESULT){
                    String nameString = data.getStringExtra("name");
                    name.setText(nameString);
                    dengji.setText("等级：三星");

                }
                break;
        }

    }
}
