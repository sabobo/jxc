package com.dgby.jxc.activity.oher;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dgby.jxc.R;
import com.dgby.jxc.adapter.StuAdapter;
import com.dgby.jxc.bean.Stu;
import com.dgby.jxc.db.StuHelp;

import java.util.List;
public class Browse extends AppCompatActivity {
    private List<Stu> stus;
    private SQLiteDatabase sqLiteDatabase;
    private StuHelp stuHelp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_browse);
        ListView listView=findViewById(R.id.list);
        sqLiteDatabase=openOrCreateDatabase(getFilesDir()+"/stu.db", Context.MODE_PRIVATE,null);
        stuHelp=StuHelp.getStuHelp(this);
        stus=stuHelp.browse(sqLiteDatabase);
        stus.add(0,new Stu("学号","姓名","年龄","性别"));
        System.out.println(stus.toString());
        StuAdapter stuAdapter=new StuAdapter(this,stus);
        listView.setAdapter(stuAdapter);
        System.out.println(stus.toString());

    }
}
