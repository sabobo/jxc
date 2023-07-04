package com.dgby.jxc.activity.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dgby.jxc.R;
import com.dgby.jxc.activity.main.GridActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    mUsernameEditText.setError("请输入用户名");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPasswordEditText.setError("请输入密码");
                    return;
                }

                // 根据用户名和密码进行登录验证
                boolean loginSuccessful = doLogin(username, password);

                if (loginSuccessful) {
                    // 登录成功，跳转到主界面
                    Intent intent = new Intent(LoginActivity.this, GridActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 登录失败，提示错误信息
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean doLogin(String username, String password) {
        // 在这里执行登录验证的逻辑
        // 如果验证通过，返回 true，否则返回 false
        // 这里只是一个示例，没有实现具体的逻辑
        return false;
    }
}
