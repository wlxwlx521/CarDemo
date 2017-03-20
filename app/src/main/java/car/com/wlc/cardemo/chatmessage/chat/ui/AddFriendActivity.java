package car.com.wlc.cardemo.chatmessage.chat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import car.com.wlc.cardemo.R;


public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mSearchMessage;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                mSearchMessage.setText("您查询的用户不在服务区");
            }
        }
    };
    private RadioGroup mGroup;
    private RadioButton mBtnName,mBtnUserC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        initView();
    }

    private void initView() {
      findViewById(R.id.search_people).setOnClickListener(this);
        mSearchMessage = ((TextView) findViewById(R.id.text_search_message));
        mGroup = ((RadioGroup) findViewById(R.id.btn_gruop));
        mBtnName = ((RadioButton) mGroup.findViewById(R.id.btn_username));
        mBtnUserC = ((RadioButton) mGroup.findViewById(R.id.btn_userc));
        mBtnName.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_people :
                mSearchMessage.setText("正在为你查询....");
                mSearchMessage.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(1,1000);
                break;
        }
    }
}
