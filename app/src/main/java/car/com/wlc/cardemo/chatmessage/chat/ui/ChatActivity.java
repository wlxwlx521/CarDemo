package car.com.wlc.cardemo.chatmessage.chat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.fragment.MyCarFragment;

/**
 * Created by wulixia on 2017/3/11.
 */
public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Fragment> list;
    private ViewPager mVp;
    private TabLayout mTab;
    private String title[]={"附近","聊天" ,"好友"};
    private View layout;
    private TextView mTilte;
    private ImageView mImaAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);


        list=new ArrayList<>();
        list.add(ChatCarFragment.newInstance());
        list.add(ChatFragment.newInstance());
        list.add(ContactFragment.newInstance());
        initView();

    }

    private void initView() {
        findViewById(R.id.vehilce_back).setOnClickListener(this);
        mTilte = ((TextView) findViewById(R.id.vehilce_title));
        mImaAdd = ((ImageView) findViewById(R.id.add_friend));
        mImaAdd.setOnClickListener(this);
        mVp = ((ViewPager) findViewById(R.id.view_pager));
        mTab = ((TabLayout) findViewById(R.id.tab_layout));
        mVp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mTab.setupWithViewPager(mVp);
       mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
                mTilte.setText(title[position]);
               if (position==title.length-1){
                   mImaAdd.setVisibility(View.VISIBLE);
               }else
                   mImaAdd.setVisibility(View.GONE);
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vehilce_back:
                finish();
                break;
            case R.id.add_friend:
                startActivity(new Intent(this,AddFriendActivity.class));
                break;

        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

}
