package car.com.wlc.cardemo.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by wlx on 2016/11/24.
 */

public class MyViewPager extends PagerAdapter {
    private Context context;
    private List<ImageView> mList;

    @Override
    public int getCount() {
        return mList.size();
    }

    public MyViewPager(Context context, List<ImageView> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));

    }
}
