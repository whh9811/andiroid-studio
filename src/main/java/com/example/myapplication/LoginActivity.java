package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.login.Utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etUsername, etPassword;
    private Button buttonRegister, buttonLogin, buttonForget;
    private TextView tUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initToolbar();
        initView();
        getUserName();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                //3.2 检查控件的有效性
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("username", "");
                String pwd = pref.getString("password", "");
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(loginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(loginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (!username.equals(name)) {
                    Toast.makeText(loginActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                } else if (!MD5Utils.md5(password).equals(pwd)) {
                    Toast.makeText(loginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // 给bnt1添加点击响应事件
                    Intent intent = new Intent(loginActivity.this, MainActivity.class);
                    //启动
                    startActivity(intent);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 给bnt1添加点击响应事件
                Intent intent = new Intent(loginActivity.this, signinActivity.class);
                //启动
                startActivity(intent);
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity.this.finish();
            }
        });
    }

    private void saveLoginStatus(String username, boolean isLogin) {
        getSharedPreferences("userInfo", MODE_PRIVATE)
                .edit()
                .putString("isLogin", isLogin)
                .apply();
    }

    private String readPwd(String username) {
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        return sp.getString(username, "");
    }

    private void initView() {
//        View view = View.inflate(this, (R.layout.activity_login), null);
        etUsername = findViewById(R.id.username_edit);
        etPassword = findViewById(R.id.userpassword_edit);
        buttonRegister = findViewById(R.id.register);
        buttonLogin = findViewById(R.id.login_button);
        buttonForget = findViewById(R.id.login_error);
        tUsername = findViewById(R.id.username_edit);
    }

    private void getUserName() {
        Intent intent = getIntent();
        String username_register = intent.getStringExtra("username_edit");
        if (username_register == null || username_register == "") {
            tUsername.setText("");
        } else {
            tUsername.setText(username_register);
        }
    }
}

