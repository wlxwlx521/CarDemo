package car.com.wlc.cardemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.painter.VelocimeterView;
import car.com.wlc.cardemo.view.CircularProgressDrawable;

public class MeterActivity extends AppActivity {



    private VelocimeterView velocimeterView;
    private CircularProgressDrawable drawable1,drawable2,drawable3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter);
        init();
    }

    private void init() {
        velocimeterView = ((VelocimeterView) findViewById(R.id.velocimeter2));
        velocimeterView.setValue(20,true);
        drawable1 =new CircularProgressDrawable.Builder().setBroderColor(Color.parseColor("#C8E5F3")).setCenterColor(Color.parseColor("#73CFFF")).setBroderWidth(13)
               .create();

        drawable2 =new CircularProgressDrawable.Builder().setBroderColor(Color.parseColor("#F1DDB5")).setCenterColor(Color.parseColor("#F8B734")).setBroderWidth(13)
                .create();
        drawable3 =new CircularProgressDrawable.Builder().setBroderColor(Color.parseColor("#C0ECDE")).setCenterColor(Color.parseColor("#58E7B9")).setBroderWidth(13)
                .create();

        ((TextView) findViewById(R.id.image1)).setBackgroundDrawable(drawable1);
        ((TextView) findViewById(R.id.image2)).setBackgroundDrawable(drawable2);
        ((TextView) findViewById(R.id.image3)).setBackgroundDrawable(drawable3);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolBar_layout);
        mToolBar.setTitle("仪表盘");
        mToolBar.setNavigationIcon(R.mipmap.iapppay_title_button_back_on);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        drawable1.setCurrentProgress(0.8f);
        drawable2.setCurrentProgress(0.3f);
        drawable3.setCurrentProgress(0.7f);
       drawable1.prepareAnimation().start();
        drawable2.prepareAnimation().start();
        drawable3.prepareAnimation().start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        drawable1.prepareAnimation().cancel();
        drawable2.prepareAnimation().cancel();
        drawable3.prepareAnimation().cancel();
    }
}
