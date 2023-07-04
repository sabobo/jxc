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
public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private Button mResetButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mEmailEditText = findViewById(R.id.email_edit_text);
        mResetButton = findViewById(R.id.reset_password_button);

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmailEditText.setError("请输入关联的电子邮件地址");
                    return;
                }

                // 检查电子邮件地址是否与任何用户账户相关联
                if (isEmailAssociatedWithAccount(email)) {
                    // 如果关联，则生成一个随机的临时密码
                    String tempPassword = generateTempPassword();

                    // 将临时密码发送到用户的电子邮件地址
                    sendTempPasswordToEmail(email, tempPassword);

                    // 跳转到重置密码界面，并传递电子邮件地址和临时密码作为参数
                    Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("temp_password", tempPassword);
                    startActivity(intent);
                } else {
                    // 如果电子邮件地址未关联任何用户账户，则提示用户
                    Toast.makeText(ForgotPasswordActivity.this, "该电子邮件地址未关联任何用户账户", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmailAssociatedWithAccount(String email) {
        // 在这里实现检查电子邮件地址是否与任何用户账户相关联的逻辑
        // 如果关联，则返回 true，否则返回 false
        return false;
    }

    private String generateTempPassword() {
        // 在这里实现生成临时密码的逻辑
        // 可以使用随机数生成器或其他方法生成随机密码
        return "temp1234";
    }

    private void sendTempPasswordToEmail(String email, String tempPassword) {
        // 在这里实现将临时密码发送到用户的电子邮件地址的逻辑
        // 可以使用邮件发送库或其他方法发送电子邮件
    }
}
