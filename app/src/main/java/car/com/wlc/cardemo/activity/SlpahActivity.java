package car.com.wlc.cardemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;


public class SlpahActivity extends AppActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                startActivity(new Intent(SlpahActivity.this, MainActivity.class));
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpah);
        handler.sendEmptyMessageDelayed(100, 2000);

    }


}