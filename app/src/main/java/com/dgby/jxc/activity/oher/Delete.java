package com.dgby.jxc.activity.oher;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dgby.jxc.R;
import com.dgby.jxc.db.StuHelp;

public class Delete extends AppCompatActivity implements View.OnClickListener {
    private String SqlPath;
    private SQLiteDatabase sqLiteDatabase;
    private StuHelp stuHelp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_delete);
        Button delete=findViewById(R.id.delete);
        delete.setOnClickListener(this);
        SqlPath=getFilesDir()+"/stu.db";
    }

    @Override
    public void onClick(View view) {
        EditText id=findViewById(R.id.id);
        sqLiteDatabase=openOrCreateDatabase(SqlPath, Context.MODE_PRIVATE,null);
        stuHelp=StuHelp.getStuHelp(getApplicationContext());
        long row=stuHelp.delete(sqLiteDatabase,id.getText().toString());
        String res;
        if(row>0) res="删除成功";
        else  res="删除失败";
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("删除结果")
                .setMessage(res)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent();
                        intent.setClass(Delete.this,Main.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

}
