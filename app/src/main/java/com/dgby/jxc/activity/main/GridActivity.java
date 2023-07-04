package com.dgby.jxc.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgby.jxc.R;
import com.dgby.jxc.activity.count.CountActivity;
import com.dgby.jxc.activity.goods.ProductActivity;
import com.dgby.jxc.activity.inventory.InventoryActivity;
import com.dgby.jxc.activity.role.RoleActivity;
import com.dgby.jxc.activity.sales.SaleActivity;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity  {

    private RecyclerView mRecyclerView;

    private RecyclerView mRecyclerViewBaner;

    private  BannerAdapter mBannerAdapter;
    private int mCurrentPosition = 0;
    private long mAutoScrollInterval = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        List<Integer> imageIds = new ArrayList<>();
        imageIds.add(R.drawable.ic_launcher_foreground);
        imageIds.add(R.drawable.ic_launcher_foreground);
        imageIds.add(R.drawable.ic_launcher_foreground);

        mRecyclerViewBaner = findViewById(R.id.banner_recycler_view);
        mRecyclerViewBaner.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBannerAdapter = new BannerAdapter(imageIds);
        mRecyclerViewBaner.setAdapter(mBannerAdapter);

        List<GridItem> items = new ArrayList<>();
        items.add(new GridItem(R.mipmap.ic_goods, "商品管理"));
        items.add(new GridItem(R.mipmap.ic_sale, "库存管理"));
        items.add(new GridItem(R.mipmap.ic_salemag, "销售管理"));
        items.add(new GridItem(R.mipmap.ic_count, "统计分析"));
        items.add(new GridItem(R.mipmap.ic_access, "权限管理"));
        items.add(new GridItem(R.mipmap.ic_exit, "退出系统"));
//        items.add(new GridItem(R.drawable.ic_launcher_background, "Item 7"));
//        items.add(new GridItem(R.drawable.ic_launcher_background, "Item 8"));
//        items.add(new GridItem(R.drawable.ic_launcher_background, "Item 9"));

        mRecyclerView = findViewById(R.id.grid_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.addItemDecoration(new GridItemDecoration(getResources().getDimensionPixelSize(R.dimen.grid_spacing)));
        GridAdapter adapter = new GridAdapter(items);
        adapter.setOnItemClickListener(new GridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GridItem item) {
                jumpActivity(item);
            }
        });
        mRecyclerView.setAdapter(adapter);


    }
    public void jumpActivity(GridItem item){
        if("商品管理".equals(item.getTitle())){
            startActivity(new Intent(GridActivity.this, ProductActivity.class));
        }else if("库存管理".equals(item.getTitle())){
            startActivity(new Intent(GridActivity.this, InventoryActivity.class));
        }else if("销售管理".equals(item.getTitle())){
            startActivity(new Intent(GridActivity.this, SaleActivity.class));
        }else if("统计分析".equals(item.getTitle())){
            startActivity(new Intent(GridActivity.this, CountActivity.class));
        }else if("权限管理".equals(item.getTitle())){
            startActivity(new Intent(GridActivity.this, RoleActivity.class));
        }else if("退出系统".equals(item.getTitle())){
            exitApp();
        }

    }

    // 退出应用程序
    public void exitApp() {
        finish();
        System.exit(0);
    }


}
