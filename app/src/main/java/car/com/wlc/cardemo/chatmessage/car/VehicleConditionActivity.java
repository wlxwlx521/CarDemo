package car.com.wlc.cardemo.chatmessage.car;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.like.LikeButton;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.chatmessage.car.view.RadarView;


public class VehicleConditionActivity extends AppCompatActivity {

    private RadarView mRadar;
    private TextView check_text;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    check_text.setText("正在检查动力系统......");
                    like_btn1.setLiked(true);
                    break;
                case 2:

                    check_text.setText("正在检查底盘......");
                    like_btn2.setLiked(true);
                    break;

                case 3:
                    check_text.setText("正在检查车身......");
                    like_btn3.setLiked(true);
                    like_btn4.setLiked(true);
                    break;
                case 4:
                    like_btn5.setLiked(true);
                    check_text.setText("检查完成，您的车况非常好");

                    break;


            }
            return false;
        }
    });

    private LikeButton like_btn1;
    private LikeButton like_btn2;
    private LikeButton like_btn3;
    private LikeButton like_btn4;
    private LikeButton like_btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_condition);
        init();

    }

    private void init() {
        mRadar = ((RadarView) findViewById(R.id.radar_view));
        check_text = ((TextView) findViewById(R.id.check_text));
        like_btn1 = ((LikeButton) findViewById(R.id.llike_btn1));
        like_btn2 = ((LikeButton) findViewById(R.id.llike_btn2));
        like_btn3 = ((LikeButton) findViewById(R.id.llike_btn3));
        like_btn4 = ((LikeButton) findViewById(R.id.llike_btn4));
        like_btn5 = ((LikeButton) findViewById(R.id.llike_btn5));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_layout);
        toolbar.setTitle("天天车况");
        toolbar.setNavigationIcon(R.mipmap.iapppay_title_button_back_on);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(800);
                    Message message1 = Message.obtain();
                    message1.what = 1;
                    handler.sendMessage(message1);
                    Thread.sleep(800);
                    Message message2 = Message.obtain();
                    message2.what = 2;
                    handler.sendMessage(message2);
                    Thread.sleep(800);
                    Message message3 = Message.obtain();
                    message3.what = 3;
                    handler.sendMessage(message3);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRadar.startRadar();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3600);
                    mRadar.stopRadar();
                    Message message4 = Message.obtain();
                    message4.what = 4;
                    handler.sendMessage(message4);

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }


}
