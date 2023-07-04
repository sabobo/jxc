package com.dgby.jxc.activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import com.dgby.jxc.R;
import com.dgby.jxc.activity.main.GridActivity;
import com.dgby.jxc.activity.user.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    private static final int AD_DURATION = 3000; // 广告展示时间（毫秒）
    private static final int REQUEST_LOGIN = 1; // 登录请求码

    private Handler mHandler = new Handler();
    private Runnable mAdRunnable = new Runnable() {
        @Override
        public void run() {
            // 广告展示时间到，根据登录状态跳转到主页或登录页
            if (isLoggedIn()) {
                Intent intent = new Intent(WelcomeActivity.this, GridActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
            finish(); // 结束当前活动，防止用户返回到欢迎界面
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 模拟广告展示
        mHandler.postDelayed(mAdRunnable, AD_DURATION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mAdRunnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LOGIN && resultCode == RESULT_OK) {
            // 登录成功，跳转到主页
            Intent intent = new Intent(this, GridActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isLoggedIn() {
        // 在这里检查用户是否已登录
        // 如果已登录，返回 true，否则返回 false
        // 这里只是一个示例，没有实现具体的逻辑
        return true;
    }
}
