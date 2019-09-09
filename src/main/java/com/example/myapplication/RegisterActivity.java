package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.Utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    //    1. 获取界面上的控件
//    2. button的点击事件的监听
//    3. 处理点击事件
//    3.1 获取控件的值
//    3.2 检查数据的有效性
//    3.3 将注册信息储存
//    3.4 跳转到登录页面
    private EditText etUsername, etPassword, etPwdAgain;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

//        1. 获取界面上的控件
        initView();
//        2. button的点击事件的监听
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                3.1 获取控件的值
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String pwdAgain = etPwdAgain.getText().toString();
//                3.2 检查数据的有效性
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(signinActivity.this,"用户名不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(signinActivity.this,"密码不能为空",
                            Toast.LENGTH_SHORT).show();
                }else if(!password.equals(pwdAgain)){
                    Toast.makeText(signinActivity.this,"两次密码必须一致",
                            Toast.LENGTH_SHORT).show();
                }else{
                    savePref(username, MD5Utils.md5(password));
                    Intent intent = new Intent(signinActivity.this,loginActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
            }
        });
    }
    private void savaLoginStatus(String username,boolean isLogin){
        getSharedPreferences("userInfo",MODE_PRIVATE)
                .edit()
                .putString("loginUser",username)
                .putBoolean("isLogin",isLogin)
                .apply();
    }

    /**
     * 保存注册的用户名和密码
     * @param username 用户名，类型String
     * @param password 密码，类型String
     */

    private void savePref(String username, String password) {
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.apply();
    }


    /**
     * 判断用户是否存在
     * @param userame 用户名
     * @return ture 存在，false 不存在
     */
    private boolean isExist(String userame){
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        String pwd = sp.getString(username,"");
        return !TextUtils.isEmpty(pwd);
    }

    private void initView(){
        etUsername = findViewById(R.id.username_edit);
        etPassword = findViewById(R.id.userpassword_edit);
        etPwdAgain = findViewById(R.id.userpassword_edit1);
        btnRegister = findViewById(R.id.login_button);
    }



    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            String username = data.getStringExtra("username");
            etUsername.setText(username);

        }
    }
}

