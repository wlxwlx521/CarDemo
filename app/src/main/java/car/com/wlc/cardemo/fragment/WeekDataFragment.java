package car.com.wlc.cardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.utils.DateUtils;
import car.com.wlc.cardemo.utils.ToastUtil;


/**
 * Created by wlx on 2016/11/25.
 */

public class WeekDataFragment extends Fragment{
    public static WeekDataFragment newInstance() {

        Bundle args = new Bundle();

        WeekDataFragment fragment = new WeekDataFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.data_week_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            Date date = new Date(System.currentTimeMillis());
            initWeekView(view,date);
    }


    private void initWeekView(View view, final Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        TextView desBtn = (TextView) view.findViewById(R.id.des_week_btn);
        TextView addWeek = (TextView) view.findViewById(R.id.add_week_btn);
        final TextView monDay = (TextView) view.findViewById(R.id.monday_date_week);
        final TextView lastDate = (TextView) view.findViewById(R.id.last_date_weeks);
        //当前日期星期几
        Calendar calendar =new GregorianCalendar();
        int i = calendar.get(Calendar.DAY_OF_WEEK)-1;
        //当前日期
        final String nowDay = dateFormat.format(date);
        lastDate.setText(nowDay);
        monDay.setText(DateUtils.desTime(nowDay,i,dateFormat));

        desBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nowTime = monDay.getText().toString();
                lastDate.setText(DateUtils.desTime(nowTime,1,dateFormat));
                monDay.setText(DateUtils.desTime(lastDate.getText().toString(),6,dateFormat));
            }
        });
        addWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lastTime = lastDate.getText().toString();
                try {
                    if (compare(lastTime,nowDay)){
                        String nowTime = lastDate.getText().toString();
                        String addOneDate = DateUtils.addTime(nowTime, 1, dateFormat);
                        if (compare(addOneDate,nowDay)){
                            monDay.setText(addOneDate);
                            String addTime = DateUtils.addTime(addOneDate, 6, dateFormat);
                            if (compare(addTime,nowDay)) {
                                lastDate.setText(addTime);
                            } else
                                lastDate.setText(nowDay);
                        }else {
                           monDay.setText(nowDay);
                            lastDate.setText(nowDay);

                        }

                }else {
                        ToastUtil.toastUtils(getContext(),"您选择的查询时间已超过当前时间范围");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public boolean compare(String time1,String time2) throws ParseException
    {
        //如果想比较日期则写成"MM/dd"就可以了
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
        //将字符串形式的时间转化为Date类型的时间
        Date a=sdf.parse(time1);
        Date b=sdf.parse(time2);
        Log.e("0909", "compare: " + a.before(b));
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if(a.before(b))

            return true;
        else
            return false;

    }

}
