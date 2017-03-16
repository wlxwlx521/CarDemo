package car.com.wlc.cardemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import car.com.wlc.cardemo.fragment.ClassifyFragment;
import car.com.wlc.cardemo.fragment.PersonalCenterFragment;
import car.com.wlc.cardemo.fragment.ShoppingCartFragment;

/**
 * Created by Administrator on 2017/3/9.
 */

public class MyShopAdapter extends FragmentPagerAdapter {
    private String[] mTitles = new String[]{ "分类", "购物车","会员中心"};
    private Fragment[] mFragment ;
    public MyShopAdapter(FragmentManager fm ,Fragment[] fragments ) {
        super(fm);
        mFragment=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment[position];
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        Log.e("lyf", "getPageTitle: "+position+mTitles[position] );
        return mTitles[position];
    }
}
