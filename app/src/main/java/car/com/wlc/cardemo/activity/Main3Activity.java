package car.com.wlc.cardemo.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.model.Login;
import car.com.wlc.cardemo.model.LoginPresenter;
import car.com.wlc.cardemo.model.User;

public class Main3Activity extends AppCompatActivity  implements Login,View.OnClickListener{

    private EditText mUserName;
    private EditText mUserPassd;



    private AlertDialog dialog;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();

    }

    private void initView() {
        mUserName = ((EditText) findViewById(R.id.ed_username));
        mUserPassd = ((EditText) findViewById(R.id.ed_password));
        findViewById(R.id.btn_login).setOnClickListener(this);
        loginPresenter=new LoginPresenter(this);
    }
    @Override
    public void loginSuccess(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Toast.makeText(Main3Activity.this,"登录成功",Toast.LENGTH_SHORT);
            }
        });



    }
    @Override
    public void loginFail(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                Toast.makeText(Main3Activity.this,"用户名或密码错误",Toast.LENGTH_SHORT);
            }
        });

    }

    public void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:

                if (loginPresenter.checkUserInfo(getUser())){
                    showDialog();
                    loginPresenter.login(getUser());

                }else {
                    Toast.makeText(this,"用户名或密吗不能为空",Toast.LENGTH_SHORT);
                }

                break;
        }
    }

    /**
     * 通过用户输入检查输入，得到用户名和密码
     * @return
     */
    public User getUser() {
        User user=new User();
        String name = mUserName.getText().toString().trim();
        String password = mUserPassd.getText().toString().trim();
        user.setName(name);
        user.setPassword(password);
        return user;
    }
}
