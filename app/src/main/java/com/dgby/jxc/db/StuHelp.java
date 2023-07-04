package com.dgby.jxc.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.dgby.jxc.bean.Stu;

import java.util.ArrayList;
import java.util.List;
public class StuHelp extends SQLiteOpenHelper {
    private static StuHelp stuHelp;
    private static final String Name = "stu";


    public static StuHelp getStuHelp(Context context) {
        if (stuHelp == null) stuHelp = new StuHelp(context);
        return stuHelp;
    }

    public long add(SQLiteDatabase sqLiteDatabase, Stu stu) {
        System.out.println(stu.toString());
        ContentValues values = new ContentValues();
        values.put("id", stu.getId());
        values.put("name", stu.getName());
        values.put("age", stu.getAge());
        values.put("sex", stu.getSex());
        return sqLiteDatabase.insert(Name, null, values);
    }

    public long delete(SQLiteDatabase sqLiteDatabase, String id) {
        return sqLiteDatabase.delete(Name, "id=?", new String[]{id});
    }

    public long update(SQLiteDatabase sqLiteDatabase, Stu stu, String id) {
        ContentValues values = new ContentValues();
        if (stu.getId() != null && !stu.getId().equals(""))
            values.put("id", stu.getId());
        if (stu.getName() != null && !stu.getName().equals(""))
            values.put("name", stu.getName());
        if (stu.getAge() != null && !stu.getAge().equals(""))
            values.put("age", stu.getAge());
        if (stu.getSex() != null && !stu.getSex().equals(""))
            values.put("sex", stu.getSex());

        return sqLiteDatabase.update(Name, values, "id=?", new String[]{id});
    }

    public Stu select(SQLiteDatabase sqLiteDatabase, String id) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from stu where id=?", new String[]{id});
        Stu stu = null;
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            stu = new Stu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        return stu;
    }

    public List<Stu> browse(SQLiteDatabase sqLiteDatabase) {
        List<Stu> stus = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(Name, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Stu stu = new Stu();
            stu.setId(cursor.getString(0));
            stu.setName(cursor.getString(1));
            stu.setAge(cursor.getString(2));
            stu.setSex(cursor.getString(3));
            stus.add(stu);
        }
        return stus;
    }

    private StuHelp(@Nullable Context context) {
        super(context, Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists stu(" +
                "id varchar(10) not null primary key," +
                "`name` varchar(10) not null," +
                "age varchar(10) not null," +
                "sex varchar(10) not null)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}