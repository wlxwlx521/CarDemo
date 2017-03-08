package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.utils.city.CitySortModel;

/**
 * Created by Administrator on 2016/12/18.
 */

public class MyStpeAdapter extends BaseAdapter {

    private List<CitySortModel> mList=new ArrayList<>();
        LayoutInflater inflater;

    public MyStpeAdapter(Context context) {

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
                convertView = inflater.inflate(R.layout.stype_info_car, null);
                holder.brandName=((TextView) convertView.findViewById(R.id.stype_name));

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
        /**
         * 判断类型
         */
        holder.brandName.setText(sortModel.getCityName());

        return convertView;
    }
    class ViewHolder{
        TextView brandName;

    }
}
