package car.com.wlc.cardemo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import car.com.wlc.cardemo.fragment.ShopTabFragment;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ShopTabAdapter extends FragmentPagerAdapter implements TabAdapter {
    private String[] titles = {"推荐分类", "正品轮胎", "轮胎修复", "正品机油"};

    private ArrayList<Fragment> mfragments = new ArrayList<>();

    public ShopTabAdapter(FragmentManager fm) {
        super(fm);

        ShopTabFragment shopTabFragment0 = new ShopTabFragment();
        Bundle bundle0 = new Bundle();
        bundle0.putInt("type", 0);
        shopTabFragment0.setArguments(bundle0);
        mfragments.add(shopTabFragment0);

        ShopTabFragment shopTabFragment1 = new ShopTabFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 1);
        shopTabFragment1.setArguments(bundle1);
        mfragments.add(shopTabFragment1);

        ShopTabFragment shopTabFragment2 = new ShopTabFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 2);
        shopTabFragment2.setArguments(bundle2);
        mfragments.add(shopTabFragment2);

        ShopTabFragment shopTabFragment3 = new ShopTabFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("type", 3);
        shopTabFragment3.setArguments(bundle3);
        mfragments.add(shopTabFragment3);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public TabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public TabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public TabView.TabTitle getTitle(int position) {

        return new TabView.TabTitle.Builder()
                .setContent(titles[position])
                .build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}

