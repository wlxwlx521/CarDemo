package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;

/**
 * Created by Administrator on 2016/12/18.
 */

public class MyCarAdapter extends RecyclerView.Adapter<MyCarAdapter.ViewHolder> implements View.OnClickListener {
    private List<VerInfoListBean > mList =new ArrayList<>();
    private onItemClickListener listener;
    private Context context;

    public MyCarAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
        listener.itemClick(v, (int ) v.getTag()
        );
    }

    public interface onItemClickListener {

        void itemClick(View view, int  postion);
    }

    public void setonItemClickListener(onItemClickListener listener) {
        this.listener =  listener;
    }

    public void refreshDate(List<VerInfoListBean> list){
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
        VerInfoListBean bean = mList.get(position);
        String url="http://static.cpsdna.com/laso/images/vehicle_logo/"+bean.getPicture();
        if (bean.getIdName() != null){
            holder.mHeadName.setText(bean.getIdName());
        }
        else
        holder.mHeadName.setText(bean.getLicensePlateNo());
        Picasso.with(context).load(url).
                placeholder(R.mipmap.cxz_common_location_car).error(R.mipmap.cxz_common_location_car).into(holder.headImage);

       holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size():0;
    }

    class  ViewHolder extends RecyclerView.ViewHolder{

        private  TextView mHeadName;
        private  ImageView headImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mHeadName = ((TextView) itemView.findViewById(R.id.head_name));
            headImage = ((ImageView) itemView.findViewById(R.id.head_ver_logoimage));
        }
    }

}
