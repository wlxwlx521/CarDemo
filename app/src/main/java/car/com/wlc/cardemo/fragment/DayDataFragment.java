package car.com.wlc.cardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.utils.DateUtils;
import car.com.wlc.cardemo.utils.ToastUtil;


/**
 * Created by wlx on 2016/11/25.
 */

public class DayDataFragment extends Fragment{

    public static DayDataFragment newInstance() {
        Bundle args = new Bundle();
        DayDataFragment fragment = new DayDataFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
           return inflater.inflate(R.layout.data_day_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date(System.currentTimeMillis());
            String time = dateFormat.format(date);
            initDayView(view,time,dateFormat);
    }

    private void initDayView(View view, final String time, final DateFormat df ) {

        TextView desBtn = ((TextView) view.findViewById(R.id.des_data_btn));
        TextView addBtn = ((TextView) view.findViewById(R.id.add_data_btn));
        final TextView textData = ((TextView) view.findViewById(R.id.text_data_day));
        textData.setText(time);
        desBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = textData.getText().toString();
                String desTime = DateUtils.desTime(date,1,df);
                textData.setText(desTime);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = textData.getText().toString();
                if (time.equals(date)){
                    ToastUtil.toastUtils(getContext(),"您选择的查询时间已超过当前时间范围");
                    return;
                }else{
                    String addTime = DateUtils.addTime(date,1,df);
                    textData.setText(addTime);
                }

            }
        });
    }
}
