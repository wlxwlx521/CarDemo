package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.ShopInfoActivity;
import car.com.wlc.cardemo.activity.ShopListActivity;
import car.com.wlc.cardemo.javaBean.Product;

import static android.R.attr.type;

/**
 * Created by Administrator on 2017/3/10.
 */

public class ShopListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Context mContext;
    private ArrayList<Product> mList;
    private int mtype;

    public ShopListAdapter(Context context, ArrayList<Product> list,int type) {
        mContext =context;
        mList= list;
        mtype=type;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
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
            convertView = inflater.inflate(R.layout.shop_list_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.shop_list_image);
            holder.name = (TextView) convertView.findViewById(R.id.shop_list_name);
            holder.price = (TextView) convertView.findViewById(R.id.shop_list_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Product product = mList.get(position);
        holder.img.setBackgroundResource(product.image);
        holder.name.setText(product.name);
        holder.price.setText(product.price);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ShopInfoActivity.class);
                intent.putExtra("product",product);
                mContext.startActivity(intent);

            }
        });


        return convertView;
    }

    class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView price;
    }
}
