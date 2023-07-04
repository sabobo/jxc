package com.dgby.jxc.activity.goods;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgby.jxc.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private Button btnAddGoods, btnAddPurchase, btnAddSale, btnQuery, btnReport;
    private RecyclerView mRecyclerView;
    private List<Product> productList;
    private ProductAdapter mAdapter;

    private ProductDbHelper mDatabaseHelper;
    private Button mAddBtn;
    private Button mEdtBtn;
    private Button mDelBtn;
    private TextView mTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        mTitle = findViewById(R.id.title_top);
        mTitle.setText("商品管理");
// 创建数据库和表
        mDatabaseHelper = new ProductDbHelper(this);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        productList = new ArrayList<>();
        //mDatabaseHelper.deleteAllProducts();
//        for (int i = 0; i < 10; i++) {
//            Product product = new Product();
//            product.setId(1001 + i);
//            product.setName("西瓜" + i);
//            product.setPrice(Double.valueOf(22 + i));
//            product.setStock(3 + i);
//            mDatabaseHelper.addProduct(product);
//        }
        productList = mDatabaseHelper.getAllProducts();
// 初始化RecyclerView
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddBtn = findViewById(R.id.btn_add_product);
        mEdtBtn = findViewById(R.id.btn_ed_product);
        mDelBtn = findViewById(R.id.btn_del_product);

// 设置 RecyclerView 的布局方式为线性布局
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

// 初始化适配器，并设置给 RecyclerView
        mAdapter = new ProductAdapter(productList, this);
        mRecyclerView.setAdapter(mAdapter);

// 设置 RecyclerView 的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

// 添加 RecyclerView 的滑动删除和编辑功能
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
//                        // 删除商品
                        Product product = productList.get(position);
                        mDatabaseHelper.deleteProduct(product.getId());
                        mAdapter.removeProduct(position);
                        break;
                    case ItemTouchHelper.RIGHT:
                        showAddGoodsDialog();
//                        // 编辑商品
                        Intent intent = new Intent(ProductActivity.this, ProductEditActivity.class);
                        intent.putExtra("product", productList.get(position));
                        intent.putExtra("position", position);
                        startActivityForResult(intent, EDIT_PRODUCT_REQUEST_CODE);
                        break;
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddGoodsDialog();
            }
        });
        mEdtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllProductsDialog();
            }
        });
        mDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearGoodsDialog();
            }
        });
    }


    @Override
    public void onProductClick(Product product) {
        showEdtGoodsDialog(product);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库
        mDatabaseHelper.close();
    }

    /**
     * 显示添加商品的对话框
     */
    @SuppressLint("MissingInflatedId")
    private void showAddGoodsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_goods, null);
        final EditText etName = view.findViewById(R.id.et_goods_name);
         final EditText etPrice = view.findViewById(R.id.et_goods_price);
        final EditText etStock = view.findViewById(R.id.et_goods_stock);
        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String stockStr = etStock.getText().toString().trim();

                // 判断商品名称、价格、库存是否为空
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ProductActivity.this, R.string.toast_empty_name, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(priceStr)) {
                    Toast.makeText(ProductActivity.this, R.string.toast_empty_price, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(stockStr)) {
                    Toast.makeText(ProductActivity.this, R.string.toast_empty_stock, Toast.LENGTH_SHORT).show();
                    return;
                }

                // 将价格和库存转换为浮点数和整数
                float price = Float.parseFloat(priceStr);
                int stock = Integer.parseInt(stockStr);

                // 将新商品插入到数据库中
                Product product = new Product();
                product.setId(new Date().getTime());
                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                long newRowId =  mDatabaseHelper.addProduct(product);
                // 根据插入结果显示提示信息
                if (newRowId == -1) {
                    Toast.makeText(ProductActivity.this, R.string.toast_add_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductActivity.this, R.string.toast_add_success, Toast.LENGTH_SHORT).show();
                    // 刷新商品列表
                    productList.clear();
                    productList.addAll(mDatabaseHelper.getAllProducts());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton(R.string.dialog_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
    /**
     * 显示编辑商品的对话框
     */
    @SuppressLint("MissingInflatedId")
    private void showEdtGoodsDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_goods, null);
        final EditText etName = view.findViewById(R.id.et_goods_name);
        final EditText etPrice = view.findViewById(R.id.et_goods_price);
        final EditText etStock = view.findViewById(R.id.et_goods_stock);
        etName.setText(product.getName()+product.getId());
        etPrice.setText(""+product.getPrice());
        etStock.setText(""+product.getStock());
        builder.setView(view);

        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String stockStr = etStock.getText().toString().trim();

                // 判断商品名称、价格、库存是否为空
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ProductActivity.this, R.string.toast_empty_name, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(priceStr)) {
                    Toast.makeText(ProductActivity.this, R.string.toast_empty_price, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(stockStr)) {
                    Toast.makeText(ProductActivity.this, R.string.toast_empty_stock, Toast.LENGTH_SHORT).show();
                    return;
                }
                // 将价格和库存转换为浮点数和整数
                float price = Float.parseFloat(priceStr);
                int stock = Integer.parseInt(stockStr);
                // 将新商品插入到数据库中
                product.setId(product.getId());
                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                long newRowId =  mDatabaseHelper.updateProduct(product);
                // 根据插入结果显示提示信息
                if (newRowId == -1) {
                    Toast.makeText(ProductActivity.this, R.string.toast_edt_failed, Toast.LENGTH_SHORT).show();
                } else {
                    // 刷新RecyclerView
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(ProductActivity.this, R.string.toast_edt_success, Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton(R.string.dialog_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * 显示清空商品的对话框
     */
    private void showClearGoodsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_clear_goods);
        builder.setMessage(R.string.dialog_msg_clear_goods);
        builder.setPositiveButton(R.string.dialog_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 清空商品列表
                productList.clear();
                // 刷新RecyclerView
                mAdapter.notifyDataSetChanged();
                // 清空数据库中的商品
                mDatabaseHelper.deleteAllProducts();
            }
        });
        builder.setNegativeButton(R.string.dialog_btn_cancel, null);
        builder.show();
    }
    private void showAllProductsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title_all_products);

        // 查询所有商品
        ProductDbHelper dbHelper = new ProductDbHelper(this);
        List<Product> productList = dbHelper.getAllProducts();

        // 构造商品列表的显示文本
        StringBuilder sb = new StringBuilder();
        for (Product product : productList) {
            sb.append("名称："+product.getName())
                    .append(" - ")
                    .append("价格：¥"+product.getPrice())
                    .append(" - ")
                    .append("库存："+product.getStock())
                    .append("\n\n");
        }

        builder.setMessage(sb.toString());
        builder.setPositiveButton(R.string.dialog_btn_ok, null);

        builder.create().show();
    }

    public void onBackPressed(View view){
        finish();
    }


}
