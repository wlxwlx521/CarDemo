package car.com.wlc.cardemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import car.com.wlc.cardemo.R;

import static android.R.attr.password;

public class ShopLoginActivity extends AppCompatActivity {

    public static final int LOGINRESULT = 1;
    private EditText name;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_login);
        name = ((EditText) findViewById(R.id.shop_name_edit));
        password = ((EditText) findViewById(R.id.shop_name_edit));
        findViewById(R.id.shop_login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(android.text.TextUtils.isEmpty(name.getText())) && !(android.text.TextUtils.isEmpty(password.getText()))) {
                    Intent intent = new Intent();
                    String text = String.valueOf(name.getText());
                    intent.putExtra("name", text);

                    setResult(LOGINRESULT, intent);
                    finish();
                }else {
                    Toast.makeText(ShopLoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.shop_login_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
