package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MyArrayAdapter extends RecyclerView.Adapter<MyArrayAdapter.ViewHolder> implements View.OnClickListener {
    private List<CitySortModel > mList =new ArrayList<>();
    private onItemClickListener listener;
    private Context context;

    public MyArrayAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
        listener.itemClick(v, (CitySortModel) v.getTag()
        );
    }

    public interface onItemClickListener {

        void itemClick(View view , CitySortModel citySortModel);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.listener =  listener;
    }

    public void refreshDate(List<CitySortModel > list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyler_head_layout, parent,false );
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int random = (int) (Math.random()*mList.size());

        holder.mHeadName.setText(mList.get(position).getCityName());
        Picasso.with(context).load(mList.get(position).getLogopath()).
                placeholder(R.mipmap.cxz_common_location_car).error(R.mipmap.cxz_common_location_car).into(holder.headImage);

       holder.itemView.setTag(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size():0;
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        private  TextView mHeadName;
        private  ImageView headImage;
        private  View headLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            headLayout = itemView.findViewById(R.id.head_layout);
            mHeadName = ((TextView) itemView.findViewById(R.id.head_name));
            headImage = ((ImageView) itemView.findViewById(R.id.head_ver_logoimage));
        }
    }

}
