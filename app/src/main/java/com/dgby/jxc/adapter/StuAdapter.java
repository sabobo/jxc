package com.dgby.jxc.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dgby.jxc.R;
import com.dgby.jxc.bean.Stu;

import java.util.List;

public class StuAdapter extends BaseAdapter {
    private Context context;
    private List<Stu> stus;
    public StuAdapter(Context context, List<Stu> stus) {
        super();
        this.context=context;
        this.stus=stus;

    }

    @Override
    public int getCount() {
        return stus.size();
    }

    @Override
    public Object getItem(int i) {
        return stus.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View stuview = LayoutInflater.from(context).inflate(R.layout.layout_atu_item,null);
        TextView[] textView=new TextView[9];
        Stu stu=stus.get(i);
        textView[0]=stuview.findViewById(R.id.t1);
        textView[0].setText(stu.getId());
        textView[1]=stuview.findViewById(R.id.t2);
        textView[1].setText(stu.getName());
        textView[2]=stuview.findViewById(R.id.t3);
        textView[2].setText(stu.getAge());
        textView[3]=stuview.findViewById(R.id.t4);
        textView[3].setText(stu.getSex());
        return stuview;
    }
}
