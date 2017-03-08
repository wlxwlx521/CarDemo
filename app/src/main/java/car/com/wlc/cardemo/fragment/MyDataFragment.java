package car.com.wlc.cardemo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.MyDataAdapter;


public class MyDataFragment extends Fragment {
    private String titles[]={"日数据","周数据","月数据"};
    private TabLayout mTab;
    private ViewPager mVp;
    private ArrayList<Fragment> mList;

    private static MyDataFragment myDataFragment;

    public static MyDataFragment getInstance() {
        if (myDataFragment == null) {
            myDataFragment = new MyDataFragment();

        }
        return myDataFragment;
    }
    public static void hebing(int a[],int left,int right,int last ) {
        int t = left;
        int temp[] = new int[last-left+1];
        int mid = right;
        int k = 0;
        while(left<mid && right < last+1&&k<last) {
            if(a[left] < a[right]) {
                temp[k++] = a[left++];
            }
            else {
                temp[k++] = a[right++];
            }

        }
        while(left < mid) {
            temp[k++] = a[left++];
        }
        while(right < last+1) {
            temp[k++] = a[right++];
        }
        for(int i=0;i<last-t+1;i++) {
            a[t+i] = temp[i];

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_data, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mList = new ArrayList<>();
        mTab = ((TabLayout) view.findViewById(R.id.tab_data));
        mVp = ((ViewPager) view.findViewById(R.id.data_viewpager));
        mTab.setupWithViewPager(mVp);
        mList.add(DayDataFragment.newInstance());
        mList.add(WeekDataFragment.newInstance());
        mList.add(MonthDataFragment.newInstance());

        mVp.setAdapter(new MyDataAdapter(getChildFragmentManager(),titles,mList));
    }


}
