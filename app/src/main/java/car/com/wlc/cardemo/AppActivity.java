package car.com.wlc.cardemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Administrator on 2017/1/21.
 */

public class AppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addDestoryActivity(this,getClass().getName());
        Log.e("5656", "onCreate: " + getClass().getName());
    }
}
