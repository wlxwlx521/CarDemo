package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.CarGridActivity;
import car.com.wlc.cardemo.activity.ShopListActivity;
import car.com.wlc.cardemo.fragment.BlankFragment;
import car.com.wlc.cardemo.fragment.PersonalCenterFragment;
import car.com.wlc.cardemo.fragment.ShoppingCartFragment;
import car.com.wlc.cardemo.javaBean.EngineOil;
import car.com.wlc.cardemo.javaBean.Product;
import car.com.wlc.cardemo.javaBean.Repair;
import car.com.wlc.cardemo.javaBean.Tire;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ShopTabGAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final FragmentManager manager;
    private Context mContext;
    private List<Integer> imList;
    private List<String> tList;
    private int mtype;


    public ShopTabGAdapter(FragmentManager manager,Context mContext, List<Integer> imList, List<String> tList,int type) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(this.mContext);
        this.imList = imList;
        this.tList = tList;
        this.manager=manager;
        mtype=type;


    }

    @Override
    public int getCount() {
        return imList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.car_gridview_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.image_item);
            holder.title = (TextView) convertView.findViewById(R.id.text_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img.setBackgroundResource(imList.get(position));
        holder.title.setText(tList.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type",mtype);
                intent.setClass(mContext, ShopListActivity.class);
                mContext.startActivity(intent);
            }
        });


        return convertView;
    }

    class ViewHolder {
        public ImageView img;
        public TextView title;
    }
}
