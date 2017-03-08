package car.com.wlc.cardemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by wlx on 2016/11/25.
 */

public class MyDataAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> mList;
    public MyDataAdapter(FragmentManager fm,String [] titles,List<Fragment> list) {
        super(fm);
        this.titles=titles;
        this.mList=list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
