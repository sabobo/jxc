package com.dgby.jxc.activity.oher;
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
import com.dgby.jxc.bean.Stu;
import com.dgby.jxc.db.StuHelp;

public class Add extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Stu stu;
    private String SqlPath;
    private SQLiteDatabase sqLiteDatabase;
    private StuHelp stuHelp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add);
        Button add=findViewById(R.id.add);
        stu=new Stu();
        add.setOnClickListener(this);
        SqlPath=getFilesDir()+"/stu.db";
        RadioGroup sex=findViewById(R.id.sex);
        sex.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("添加结果");
        EditText id=findViewById(R.id.id);
        stu.setId(id.getText().toString());
        EditText name=findViewById(R.id.name);
        stu.setName(name.getText().toString());
        EditText age=findViewById(R.id.age);
        stu.setAge(age.getText().toString());
        sqLiteDatabase=openOrCreateDatabase(SqlPath, Context.MODE_PRIVATE,null);
        stuHelp=StuHelp.getStuHelp(getApplicationContext());
        long row=stuHelp.add(sqLiteDatabase,stu);
        if(row>0){
            builder.setMessage("添加成功"+" "+stu.toString());
        }
        else{
            builder.setMessage("添加失败"+" "+stu.toString());
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent();
                intent.setClass(Add.this,Main.class);
                startActivity(intent);
            }
        });
        builder.show();
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
