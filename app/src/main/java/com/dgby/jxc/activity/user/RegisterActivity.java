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
public class RegisterActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mPasswordEditText = findViewById(R.id.password_edit_text);
        mConfirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    mUsernameEditText.setError("请输入用户名");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPasswordEditText.setError("请输入密码");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    mConfirmPasswordEditText.setError("两次输入的密码不一致");
                    return;
                }

                // 根据用户名和密码进行注册
                boolean registerSuccessful = doRegister(username, password);

                if (registerSuccessful) {
                    // 注册成功，跳转到登录界面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 注册失败，提示错误信息
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean doRegister(String username, String password) {
        // 在这里执行登录验证的逻辑
        // 如果验证通过，返回 true，否则返回 false
        // 这里只是一个示例，没有实现具体的逻辑
        return false;
    }


}