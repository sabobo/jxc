package com.dgby.jxc.activity.oher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dgby.jxc.R;
import com.dgby.jxc.activity.Update;
import com.dgby.jxc.db.StuHelp;

public class Main extends AppCompatActivity implements View.OnClickListener {
    private String SqlPath;
    private SQLiteDatabase sqLiteDatabase;
    private StuHelp stuHelp;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        Button[] buttons=new Button[10];
        SqlPath=getFilesDir()+"/stu.db";
        buttons[0]=findViewById(R.id.b0);
        buttons[0].setOnClickListener(this);
        buttons[1]=findViewById(R.id.b1);
        buttons[1].setOnClickListener(this);
        buttons[2]=findViewById(R.id.b2);
        buttons[2].setOnClickListener(this);
        buttons[3]=findViewById(R.id.b3);
        buttons[3].setOnClickListener(this);
        buttons[4]=findViewById(R.id.b4);
        buttons[4].setOnClickListener(this);
        buttons[5]=findViewById(R.id.b5);
        buttons[5].setOnClickListener(this);
        buttons[6]=findViewById(R.id.b6);
        buttons[6].setOnClickListener(this);
        buttons[7]=findViewById(R.id.b7);
        buttons[7].setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sqLiteDatabase=openOrCreateDatabase(SqlPath, Context.MODE_PRIVATE,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.b0:
                stuHelp=StuHelp.getStuHelp(getApplicationContext());
                stuHelp.onCreate(sqLiteDatabase);
                builder.setTitle("创建结果")
                        .setMessage("信息表路径为"+sqLiteDatabase.getPath().toString())
                        .setPositiveButton("确认",null)
                        .show();
                break;
            case R.id.b1:
                intent.setClass(Main.this,Add.class);
                startActivity(intent);
                break;
            case R.id.b2:
                intent.setClass(Main.this,Delete.class);
                startActivity(intent);
                break;
            case R.id.b3:
                intent.setClass(Main.this, Update.class);
                startActivity(intent);
                break;
            case R.id.b4:
                intent.setClass(Main.this,Select.class);
                startActivity(intent);
                break;
            case R.id.b5:
                intent.setClass(Main.this,Browse.class);
                startActivity(intent);
                break;
            case R.id.b6:
                builder.setTitle("清空表格")
                        .setMessage("表格已经清空")
                        .setPositiveButton("确认",null)
                        .show();
                sqLiteDatabase.execSQL("delete from stu where id=id");
                break;
            case R.id.b7:
                builder.setTitle("关闭结果")
                        .setMessage("数据库已经关闭")
                        .setPositiveButton("确认",null)
                        .show();
                sqLiteDatabase.close();
                break;
        }
    }

}
