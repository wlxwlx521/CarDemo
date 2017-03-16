package car.com.wlc.cardemo.chatmessage.chat.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.chatmessage.chat.models.User;
import car.com.wlc.cardemo.chatmessage.chat.ui.MessengerActivity;


/**
 * Created by Administrator on 2017/3/10.
 */

public class MyAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<User> list;
    private Context context;
    private int type;

    public MyAdapter(Context context, List<User> list, int type) {
        this.context = context;
        this.type = type;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final User user = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.contact_layout, null);
            holder.ima = (ImageView) convertView.findViewById(R.id.profile_image);
            holder.content = (TextView) convertView.findViewById(R.id.text_content);
            holder.time = (TextView) convertView.findViewById(R.id.text_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String time = new SimpleDateFormat("hh:mm").format(new Date());
        holder.ima.setImageResource(user.getIcon());
        if (type == 0) {
            holder.content.setText("你好");
        } else {
            holder.content.setText(user.getName());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessengerActivity.class);
                intent.putExtra("user", user);
                context. startActivity(intent);
            }
        });
        holder.time.setText(time);
        return convertView;
    }

    class ViewHolder {
        ImageView ima;
        TextView content;
        TextView time;
    }
}

