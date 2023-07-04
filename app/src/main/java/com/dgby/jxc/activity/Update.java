package com.dgby.jxc.activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dgby.jxc.R;
import com.dgby.jxc.activity.oher.Main;
import com.dgby.jxc.bean.Stu;
import com.dgby.jxc.db.StuHelp;

public class Update extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Stu stu;
    private String SqlPath;
    private SQLiteDatabase sqLiteDatabase;
    private StuHelp stuHelp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update);
        Button update=findViewById(R.id.update);
        update.setOnClickListener(this);
        stu=new Stu();
        SqlPath=getFilesDir()+"/stu.db";
        RadioGroup sex=findViewById(R.id.sex);
        sex.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        sqLiteDatabase=openOrCreateDatabase(SqlPath, Context.MODE_PRIVATE,null);
        stuHelp=StuHelp.getStuHelp(getApplicationContext());
        EditText id=findViewById(R.id.id);
        stu.setId(id.getText().toString());
        EditText name=findViewById(R.id.name);
        stu.setName(name.getText().toString());
        EditText age=findViewById(R.id.age);
        stu.setAge(age.getText().toString());
        EditText oldid=findViewById(R.id.oldid);
        long row=stuHelp.update(sqLiteDatabase,stu,oldid.getText().toString());
        String res;
        if(row>0) res="修改成功";
        else res="修改失败";
        builder.setTitle("删除结果")
                .setMessage(res)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent();
                        intent.setClass(Update.this, Main.class);
                        startActivity(intent);
                    }
                })
                .show();

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.man:
                stu.setSex("男");
                break;
            case R.id.woman:
                stu.setSex("女");
                break;
        }
    }
}
