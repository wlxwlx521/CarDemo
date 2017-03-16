package car.com.wlc.cardemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.MyShopAdapter;
import car.com.wlc.cardemo.fragment.ClassifyFragment;
import car.com.wlc.cardemo.fragment.PersonalCenterFragment;
import car.com.wlc.cardemo.fragment.ShoppingCartFragment;


public class ShopActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private MyShopAdapter myShopAdapter;
    private TabLayout mTabLayout;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==123){
                Log.e("lyf", "handleMessage: " );
                mViewPager.setCurrentItem(1);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initViews();


    }

    private void initViews() {
        PersonalCenterFragment personalCenterFragment = new PersonalCenterFragment();
        personalCenterFragment.setHandler(handler);
        Fragment[] mFragment = new Fragment[]{new ClassifyFragment(),new ShoppingCartFragment(),personalCenterFragment};
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        myShopAdapter = new MyShopAdapter(getSupportFragmentManager(),mFragment);
        mViewPager.setAdapter(myShopAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);


        //设置Tab的图标，假如不需要则把下面的代码删去
        mTabLayout.getTabAt(0).setIcon(R.mipmap.fenlei);
        mTabLayout.getTabAt(1).setIcon(R.mipmap.gouwuche);
        mTabLayout.getTabAt(2).setIcon(R.mipmap.geren);


    }
}
