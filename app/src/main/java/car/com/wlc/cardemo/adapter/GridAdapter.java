package car.com.wlc.cardemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.CarGridActivity;

/**
 * Created by Administrator on 2017/3/1.
 */
public class GridAdapter extends BaseAdapter{
    private final LayoutInflater inflater;
    private final String[] strings;
    private Context mContext;
    private List<Integer> imList;
    private List<String> tList;


    public GridAdapter(Context mContext, List<Integer> imList, List<String> tList) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(this.mContext);
        this.imList = imList;
        this.tList = tList;

        strings = new String[]{
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html",
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html",
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html",
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html",
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html",
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html",
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html",
                "http://m.jingzhengu.com/xiansuo/pinggu-jumapao.html"

        };

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
        ViewHolder holder ;
                       if (convertView == null) {

                               holder=new ViewHolder();
                               convertView = inflater.inflate(R.layout.car_gridview_item, null);
                               holder.img = (ImageView)convertView.findViewById(R.id.image_item);
                               holder.title = (TextView)convertView.findViewById(R.id.text_item);
                           convertView.setTag(holder);
                            }else {
                           holder= (ViewHolder) convertView.getTag();
                       }
                        holder.img.setBackgroundResource(imList.get(position));
                        holder.title.setText(tList.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = strings[position];
                Intent intent = new Intent(mContext, CarGridActivity.class);
                intent.putExtra("url",string);

                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder{
        public ImageView img;
        public TextView title;
    }
}
