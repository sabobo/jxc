package com.dgby.jxc.activity.sales;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
public class SaleActivity extends AppCompatActivity {

    private SalesDbHelper dbHelper;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        dbHelper = new SalesDbHelper(this);

         Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        Button allButton = findViewById(R.id.all_button);
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllRecords();
            }
        });

        Button rangeButton = findViewById(R.id.range_button);
        rangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRangeRecords();
            }
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加销售记录");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_sale, null);
        builder.setView(view);

        final EditText productEditText = view.findViewById(R.id.product_edit_text);
        final EditText priceEditText = view.findViewById(R.id.price_edit_text);
        final EditText amountEditText = view.findViewById(R.id.amount_edit_text);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String product = productEditText.getText().toString().trim();
                String priceStr = priceEditText.getText().toString().trim();
                String amountStr = amountEditText.getText().toString().trim();
                if (TextUtils.isEmpty(product) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(amountStr)) {
                    Toast.makeText(SaleActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                } else {
                    double price = Double.parseDouble(priceStr);
                    int amount = Integer.parseInt(amountStr);
                    long rowId = dbHelper.addSaleRecord(product, price, amount);
                    if (rowId == -1) {
                        Toast.makeText(SaleActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SaleActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void showAllRecords() {
        List<SaleRecord> saleRecords = dbHelper.getAllSaleRecords();
        if (saleRecords.isEmpty()) {
            Toast.makeText(this, "暂无销售记录", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder sb = new StringBuilder();
            for (SaleRecord saleRecord : saleRecords) {
                sb.append(saleRecord.toString());
                sb.append("\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("所有销售记录");
            builder.setMessage(sb.toString());
            builder.setPositiveButton("确定", null);
            builder.show();
        }
    }
    @SuppressLint("MissingInflatedId")
    private void showRangeRecords() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择时间段");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_time_range, null);
        builder.setView(view);

        final EditText startEditText = view.findViewById(R.id.start_edit_text);
        final EditText endEditText = view.findViewById(R.id.end_edit_text);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String startStr = startEditText.getText().toString().trim();
                String endStr = endEditText.getText().toString().trim();
                if (TextUtils.isEmpty(startStr) || TextUtils.isEmpty(endStr)) {
                    Toast.makeText(SaleActivity.this, "请输入起止时间", Toast.LENGTH_SHORT).show();
                } else {
                    long start = Long.parseLong(startStr);
                    long end = Long.parseLong(endStr);
                    List<SaleRecord> saleRecords = dbHelper.getSaleRecordsByTimeRange(start, end);
                    if (saleRecords.isEmpty()) {
                        Toast.makeText(SaleActivity.this, "该时间段内无销售记录", Toast.LENGTH_SHORT).show();
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (SaleRecord saleRecord : saleRecords) {
                            sb.append(saleRecord.toString());
                            sb.append("\n");
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(SaleActivity.this);
                        builder.setTitle("销售记录");
                        builder.setMessage(sb.toString());
                        builder.setPositiveButton("确定", null);
                        builder.show();
                    }
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
