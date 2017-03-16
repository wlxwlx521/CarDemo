package car.com.wlc.cardemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.ShopTabAdapter;
import q.rorbin.verticaltablayout.VerticalTabLayout;

/**
 * 车福商城分类界面
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends Fragment {


    private VerticalTabLayout tab;
    private ViewPager viewpager;

    public ClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_classify, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        tab = ((VerticalTabLayout) view.findViewById(R.id.classify_tab));
        viewpager = ((ViewPager) view.findViewById(R.id.classify_viewpager));
        viewpager.setAdapter(new ShopTabAdapter(getChildFragmentManager()));
        tab.setupWithViewPager(viewpager);

        view.findViewById(R.id.classify_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }


}
