package car.com.wlc.cardemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.fragment.HomeFragment;
import car.com.wlc.cardemo.fragment.MyCarFragment;
import car.com.wlc.cardemo.fragment.MyDataFragment;
import car.com.wlc.cardemo.fragment.UserFragment;
import car.com.wlc.cardemo.utils.ExitApp;

public class MainActivity extends AppActivity{
    private static final int LOCATION = 1;
    private RadioGroup mRadioGroup;
    private HomeFragment homeFragment;
    private List<Fragment> fragmentList =new ArrayList<>();
    private FragmentManager manager;
    private MyCarFragment myCarFragment;
    private MyDataFragment myDataFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TAG", "onCreate: " +11111);
        initView();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int position=0;
                switch (i) {

                    case R.id.btn_home:
                        position=0;
                        break;
                    case R.id.btn_car:
                        position=1;
                   // requestLocationPermission();
                        break;
                    case R.id.btn_data:
                        position=2;
                        break;
                    case R.id.btn_user:
                        position=3;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, fragmentList.get(position)).commit();

            }
        });

    }


    private void initView() {
        fragmentList.add(HomeFragment.getInstance());
        fragmentList.add(MyCarFragment.getInstance());
        fragmentList.add(MyDataFragment.getInstance());
        fragmentList.add(UserFragment.getInstance());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, fragmentList.get(0)).commit();

        mRadioGroup = ((RadioGroup) findViewById(R.id.radio_main));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //调用双击退出方法
            ExitApp.getIntance().exitBy2Click(this);
            finish();
        }
        return false;
    }

}
