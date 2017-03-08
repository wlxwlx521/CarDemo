package car.com.wlc.cardemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.simonvt.datepicker.DatePickDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.VehicleFuelAna;
import car.com.wlc.cardemo.utils.DateUtils;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.ToastUtil;
import car.com.wlc.cardemo.view.chart.NChart;
import car.com.wlc.cardemo.view.chart.NExcel;


/**
 * Created by wlx on 2016/11/25.
 */

public class MonthDataFragment extends Fragment{
    final DateFormat df=new SimpleDateFormat("yyyy-MM");

    private TextView lastDate,addBtn,desBtn,monthFuel,monthMile,monthSpeed,monthFuelCost,monthFuelConsumption,mDefaultFue,mDefaultPai;


    private NChart nChart;



    public static MonthDataFragment newInstance() {

        Bundle args = new Bundle();

        MonthDataFragment fragment = new MonthDataFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.data_month_fragment, null, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

         addBtn = (TextView) view.findViewById(R.id.add_month_date);
        desBtn = (TextView) view.findViewById(R.id.des_month_date);

        lastDate = (TextView) view.findViewById(R.id.last_date_month);
        String nowDate = df.format(new Date());
        lastDate.setText(nowDate);
        monthFuel = ((TextView) view.findViewById(R.id.monthFuel));
        monthMile = ((TextView) view.findViewById(R.id.monthMile));
        monthFuelCost = ((TextView) view.findViewById(R.id.monthFuelCost));
        monthSpeed = ((TextView) view.findViewById(R.id.monthSpeed));
        monthFuelConsumption = ((TextView) view.findViewById(R.id.monthFuelConsumption));
         nChart = (NChart) view.findViewById(R.id.sug_recode_schar);
        mDefaultFue = ((TextView) view.findViewById(R.id.default_fue));
        mDefaultPai = ((TextView) view.findViewById(R.id.default_pailiang));
        requestData(nowDate);
        initMonthView();

    }

    /**
     * 根据不同的月份，请求数据
     * @param month
     */
    private void requestData(String  month) {
        RequestParams requestParams=new RequestParams(Contact.URL);
        requestParams.setAsJsonContent(true);

        requestParams.setBodyContent("{ \"cmd\": \"vehicleFuelAna\", \"auth\": {\"devKey\": \""+ Contact.KEY+"\",\"userName\": " +
                "\"18300647276\", \"password\": \"lolo88550845\",\"mapType\": \"amap\"}, \"params\": { \"vehicleId\":\"H201606260335\", \"month\": \""+month+"\"}}");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("1231", "result: " + result);
                VehicleFuelAna fuelAna = JsonData.getVehicleFuelAna(result);
                showData(fuelAna);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void showData(VehicleFuelAna fuelAna) {
        if (fuelAna.getMonthFuel()!=0){
            monthFuel.setText(fuelAna.getMonthFuel()+"");
        }
        if (fuelAna.getMonthMile()!=0){
            monthMile.setText(fuelAna.getMonthMile()+"");
        }
        if (fuelAna.getMonthFuelCost()!=null){
            monthFuelCost.setText(fuelAna.getMonthFuelCost());
        }
        if (fuelAna.getMonthSpeed()!=0){
            monthSpeed.setText(fuelAna.getMonthSpeed()+"");
        }
        if (fuelAna.getMonthFuelConsumption()!=null ){
            monthFuelConsumption.setText(fuelAna.getMonthFuelConsumption());
        }
        mDefaultFue.setText("击败全国同车型的车辆"+fuelAna.getDefeatSameStyleRate()*100+"%的车辆");
        mDefaultPai.setText("击败全国同车型的车辆"+fuelAna.getDefeatSameDisplRate()*100+"%的车辆");
    }

    private void initMonthView() {

        //柱状图
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        int actualMaximum = calendar.getActualMaximum(Calendar.DATE);

        List<NExcel> nExcel = getNExcel(i);
        nChart.setAbove(0);
        nChart.setNormalColor(Color.parseColor("#FF6EAFEA"));
        nChart.setScrollAble(false);
        nChart.setBarWidth(1);
        nChart.setTextSize(5);
        nChart.cmdFill(nExcel);

       //日期
        final String nowMonth = df.format(new Date());
        lastDate.setText(nowMonth);
        //增加日期
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastTime = lastDate.getText().toString();

                    if (lastTime.equals(nowMonth)){
                        ToastUtil.toastUtils(getContext(),"您选择的查询时间已超过当前时间范围");
                    }else

                    lastDate.setText(DateUtils.addMonth(lastTime));
            }
        });
        //减日期
        desBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastTime = lastDate.getText().toString();
                lastDate.setText(DateUtils.desMonth(lastTime));
            }
        });
        //自动设置时间
        lastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickDialog datePickDialog=new DatePickDialog(getContext(), new DatePickDialog.IgetDate() {
                    @Override
                    public void getDate(int i, int i1, int i2) {
                        Calendar instance = Calendar.getInstance();
                        instance.set(i,i1);
                        String ss = df.format(instance.getTime());
                        lastDate.setText(ss);
                    }
                },"设置日期","确定","");
                datePickDialog.show();
            }
        });
    }
    //柱状图
    public List<NExcel> getNExcel(int  actualMaximum) {
        Random random = new Random();
        List<NExcel> nExcel=new ArrayList<>();

        for (int j = 1; j <=actualMaximum ; j++) {
            nExcel.add(new NExcel(random.nextInt(200),j+""));
        }
        return nExcel;
    }
}
