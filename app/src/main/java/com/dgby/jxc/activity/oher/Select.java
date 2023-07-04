package com.dgby.jxc.activity.oher;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dgby.jxc.R;
import com.dgby.jxc.adapter.StuAdapter;
import com.dgby.jxc.bean.Stu;
import com.dgby.jxc.db.StuHelp;

import java.util.ArrayList;
import java.util.List;
public class Select extends AppCompatActivity implements View.OnClickListener {
    private String SqlPath;
    private SQLiteDatabase sqLiteDatabase;
    private StuHelp stuHelp;
    public List<Stu> stus;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_select);
        Button select=findViewById(R.id.select);
        select.setOnClickListener(this);
        SqlPath=getFilesDir()+"/stu.db";

    }

    @Override
    public void onClick(View view) {
        EditText id=findViewById(R.id.id);
        sqLiteDatabase=openOrCreateDatabase(SqlPath,Context.MODE_PRIVATE,null);
        stuHelp=StuHelp.getStuHelp(this);
        stus=new ArrayList<>();
        stus.add(new Stu("学号","姓名","年龄","性别"));
        Stu stu=stuHelp.select(sqLiteDatabase,id.getText().toString());
        if(stu!=null)
            stus.add(stu);
        else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("查询失败")
                    .setMessage("未找到相关信息")
                    .setPositiveButton("确认",null)
                    .show();
        }
        StuAdapter stuAdapter=new StuAdapter(this,stus);
        ListView listView=findViewById(R.id.list);
        listView.setAdapter(stuAdapter);
    }
}
