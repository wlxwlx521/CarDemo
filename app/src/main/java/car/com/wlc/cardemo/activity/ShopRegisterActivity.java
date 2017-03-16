package car.com.wlc.cardemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.text.TextUtils;

import static android.R.attr.password;

public class ShopRegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_register);

        name = ((EditText) findViewById(R.id.shop_register_name_edit));
        password = ((EditText) findViewById(R.id.shop_register_password_edit));

        findViewById(R.id.shop_register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(android.text.TextUtils.isEmpty(name.getText())) && !(android.text.TextUtils.isEmpty(password.getText()))) {
                    finish();
                } else {
                    Toast.makeText(ShopRegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.shop_register_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
