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
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mNewPasswordEditText;
    private EditText mConfirmPasswordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mUsernameEditText = findViewById(R.id.username_edit_text);
        mNewPasswordEditText = findViewById(R.id.new_password_edit_text);
        mConfirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        Button resetPasswordButton = findViewById(R.id.reset_password_button);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEditText.getText().toString();
                String newPassword = mNewPasswordEditText.getText().toString();
                String confirmPassword = mConfirmPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    mUsernameEditText.setError("请输入用户名");
                    return;
                }

                if (TextUtils.isEmpty(newPassword)) {
                    mNewPasswordEditText.setError("请输入新密码");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    mConfirmPasswordEditText.setError("两次输入的密码不一致");
                    return;
                }

                // 根据用户名和新密码进行密码重置
                boolean resetSuccessful = resetPassword(username, newPassword);

                if (resetSuccessful) {
                    // 密码重置成功，跳转到登录界面
                    Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 密码重置失败，提示错误信息
                    Toast.makeText(ResetPasswordActivity.this, "密码重置失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean resetPassword(String username, String newPassword) {
        // 在这里执行密码重置的逻辑
        // 如果重置成功，返回 true，否则返回 false
        // 这里只是一个示例，没有实现具体的逻辑
        return false;
    }
}
