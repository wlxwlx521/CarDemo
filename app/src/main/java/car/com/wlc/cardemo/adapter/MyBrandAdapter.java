package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.utils.city.CitySortModel;

/**
 * Created by Administrator on 2016/12/18.
 */

public class MyBrandAdapter extends BaseAdapter {
   private Context context;
    private List<CitySortModel> mList=new ArrayList<>();
    LayoutInflater inflater;

    public MyBrandAdapter(Context context) {
        this.context=context;
       inflater= LayoutInflater.from(context);

    }
    public void refreshDate(List<CitySortModel> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList!=null ? mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CitySortModel sortModel = mList.get(position);

            if (convertView == null){
                holder=new ViewHolder();
                convertView = inflater.inflate(R.layout.draw_item, null);
                holder.brandName=((TextView) convertView.findViewById(R.id.draw_name));
                holder.brandImage= (ImageView) convertView.findViewById(R.id.draw_ver_logoimage);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
        /**
         * 判断类型
         */
        holder.brandName.setText(sortModel.getCityName());


            if (sortModel.getImageUrl().equals("")){
                Picasso.with(context).load(R.mipmap.cxz_common_location_car).
                        placeholder(R.mipmap.cxz_common_location_car).error(R.mipmap.cxz_common_location_car).into(holder.brandImage);
            }else
                Picasso.with(context).load(sortModel.getImageUrl()).
                        placeholder(R.mipmap.cxz_common_location_car).error(R.mipmap.cxz_common_location_car).into(holder.brandImage);


        return convertView;
    }
    class ViewHolder{
        TextView brandName;
        ImageView brandImage;
    }
}
