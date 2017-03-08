package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.utils.DateUtils;
import car.com.wlc.cardemo.utils.VehicleTrackIntf;

/**
 * Created by Administrator on 2017/1/11.
 */

public class RecordAdapter extends BaseAdapter  {
    private final LayoutInflater inflater;

     private List<VehicleTrackIntf> mList=new ArrayList();

    public RecordAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }
    public void  refreshData(List<VehicleTrackIntf> list){
        mList.clear();
        mList.addAll(list);
        Log.e("1415", "refreshData: " + list.size());
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mList !=null?mList.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
        VehicleTrackIntf bean = mList.get(position);
        if (convertView == null){
           convertView= inflater.inflate(R.layout.car_recorder_item,null);
            holder=new ViewHolder();
            holder.startTime= (TextView) convertView.findViewById(R.id.text_starttime);
            holder.endTime= (TextView) convertView.findViewById(R.id.text_endtime);
            holder.startId= (TextView) convertView.findViewById(R.id.text_startid);
            holder.endId= (TextView) convertView.findViewById(R.id.text_endid);
           holder.distance= (TextView) convertView.findViewById(R.id.text_distance);
            holder.stopTime= (TextView) convertView.findViewById(R.id.text_stoptime);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        String endTime = bean.getEndTime();
        holder.startTime.setText(DateUtils.convertToTime(bean.getStartTime())+"起点");
        holder.endTime.setText(DateUtils.convertToTime(endTime)+"终点");
        holder.startId.setText(bean.getStartLocation());
        holder.endId.setText(bean.getEndLocation());
        holder.distance.setText(bean.getTotalMileAge());
        if (mList.size()>1 &&position != mList.size()-1){
            VehicleTrackIntf in = mList.get(position + 1);
            String startTime = in.getStartTime();
            String time = DateUtils.getTime(bean.getEndTime(),startTime);
            if (time !=null){
                holder.stopTime.setVisibility(View.VISIBLE);
                holder.stopTime.setText(time);

            }else
                holder.stopTime.setVisibility(View.INVISIBLE);

        }
        return convertView;
    }

    class ViewHolder{
        TextView startTime;
        TextView endTime;
        TextView startId;
        TextView endId;
        TextView distance;
        TextView stopTime;
    }
}
