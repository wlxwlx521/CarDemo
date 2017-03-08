package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.utils.city.CitySortModel;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>  {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;
    private Context context;

    private int type;

    private List<CitySortModel> cityLists = new ArrayList<>();
    private onItemClickListener listener;

    public CityAdapter(Context context,int type) {
        this.context = context;
        this.type=type;

    }
    public void refresh(List<CitySortModel> list){
        cityLists.addAll(list);
        Log.e("6776", "refresh: " + cityLists.size());
        notifyDataSetChanged();
    }
    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {

        void itemClick(int i);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

     View view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false);
      return new CityViewHolder(view);

    }

    @Override
    public void onBindViewHolder(CityViewHolder viewHolder, final int position) {
        CitySortModel cityModel = cityLists.get(position);
            viewHolder.tvCityName.setText(cityModel.getCityName());
            if (type==0){
                viewHolder.verLogo.setVisibility(View.VISIBLE);
                Picasso.with(context).load(cityModel.getLogopath()).
                        placeholder(R.mipmap.cxz_common_location_car).error(R.mipmap.cxz_common_location_car).into(viewHolder.verLogo);
            }else {
                viewHolder.verLogo.setVisibility(View.GONE);
            }
            viewHolder.rlContentWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(position);
                }
            });

            if (position == 0) {
                viewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                viewHolder.tvStickyHeader.setText(cityModel.getFirstPinYin());
                viewHolder.itemView.setTag(FIRST_STICKY_VIEW);
            } else {
                if (!TextUtils.equals(cityModel.getFirstPinYin(), cityLists.get(position -1).getFirstPinYin())) {
                    viewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                    viewHolder.tvStickyHeader.setText(cityModel.getFirstPinYin());
                    viewHolder.itemView.setTag(HAS_STICKY_VIEW);
                } else {
                    viewHolder.tvStickyHeader.setVisibility(View.GONE);
                    viewHolder.itemView.setTag(NONE_STICKY_VIEW);
                }
            }

            viewHolder.itemView.setContentDescription(cityModel.getFirstPinYin());



    }

    @Override
    public int getItemCount() {
        return cityLists != null ?  cityLists.size():0;
    }


    public class CityViewHolder extends RecyclerView.ViewHolder {

        ImageView verLogo;
        TextView tvStickyHeader, tvCityName;
        LinearLayout rlContentWrapper;


        public CityViewHolder(View itemView) {
            super(itemView);
            tvStickyHeader = (TextView) itemView.findViewById(R.id.tv_sticky_header_view);
            rlContentWrapper = (LinearLayout)itemView.findViewById(R.id.rl_content_wrapper);
            tvCityName = (TextView) itemView.findViewById(R.id.tv_name);
            verLogo = ((ImageView) itemView.findViewById(R.id.ver_logoimage));
        }
    }


}
