package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.EngineOil;
import car.com.wlc.cardemo.javaBean.Product;
import car.com.wlc.cardemo.javaBean.Repair;
import car.com.wlc.cardemo.javaBean.Tire;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ShopBrandInfoAdapter extends BaseAdapter

    {


        private final LayoutInflater inflater;
        private Context mContext;
        private List<Product> mList;
        private int mType;


        public ShopBrandInfoAdapter(Context mContext, List < Product > list, int type){
        this.mContext = mContext;
        mType = type;
        inflater = LayoutInflater.from(this.mContext);
        mList = list;
    }

        @Override
        public int getCount () {
        if (mType == 0) {
            return mList.size();
        } else if (mType == 1) {
            for (Product p : mList) {
                if (!(p instanceof Tire)) {
                    mList.remove(p);

                }

            }
            return mList.size();

        } else if (mType == 2) {
            for (Product p : mList) {
                if (!(p instanceof Repair)) {
                    mList.remove(p);

                }

            }
            return mList.size();

        } else if (mType == 3) {
            for (Product p : mList) {
                if (!(p instanceof EngineOil)) {
                    mList.remove(p);

                }

            }
            return mList.size();

        }
        return mList.size();
    }

        @Override
        public Object getItem ( int position){
        return position;
    }

        @Override
        public long getItemId ( int position){
        return 0;
    }

        @Override
        public View getView ( final int position, View convertView, ViewGroup parent){
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
        holder.img.setBackgroundResource(mList.get(position).image);
        holder.title.setText(mList.get(position).name);
        return convertView;
    }

        class ViewHolder {
            public ImageView img;
            public TextView title;
        }

    }
