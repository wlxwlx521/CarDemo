package car.com.wlc.cardemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.ShopTabGAdapter;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * 商城 tab右侧信息页面
 */
public class ShopTabFragment extends Fragment {


    private GridView gridView;
    private int type;
    private List<String> tlis;
    private List<Integer> imlist;


    public ShopTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_tab, container, false);
        initData();


        initView(view);
        return view;
    }

    private void initData() {
        Bundle arguments = getArguments();
        type = arguments.getInt("type");
        if (type == 0) {
            tlis = new ArrayList<>();
            imlist = new ArrayList<>();
            tlis.add("所有商品");
            imlist.add(R.mipmap.all);


        } else if (type == 1) {
            tlis = new ArrayList<>();
            imlist = new ArrayList<>();
            tlis.add("风神轮胎");
            tlis.add("米其林轮胎");
            tlis.add("普利司通");
            tlis.add("韩泰轮胎");
            tlis.add("横滨轮胎");
            tlis.add("德国马牌");
            imlist.add(R.mipmap.fengshen);
            imlist.add(R.mipmap.miqilin);
            imlist.add(R.mipmap.pulisitong);
            imlist.add(R.mipmap.hantai);
            imlist.add(R.mipmap.hengbin);
            imlist.add(R.mipmap.mapai);

        } else if (type == 2) {
            tlis = new ArrayList<>();
            imlist = new ArrayList<>();
            tlis.add("补胎");
            imlist.add(R.mipmap.butai);

        } else if (type == 3) {

            tlis = new ArrayList<>();
            imlist = new ArrayList<>();
            tlis.add("嘉实多");
            imlist.add(R.mipmap.jiashiduo);
        }
    }

    private void initView(View view) {
        gridView = ((GridView) view.findViewById(R.id.shop_tab_gridview));
        ShopTabGAdapter shopTabGAdapter = new ShopTabGAdapter(getFragmentManager(),getContext(), imlist, tlis,type);
        gridView.setAdapter(shopTabGAdapter);
    }

}
