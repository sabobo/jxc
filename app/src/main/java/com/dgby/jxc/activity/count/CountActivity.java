package com.dgby.jxc.activity.count;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dgby.jxc.R;
import com.dgby.jxc.activity.inventory.InventoryDbHelper;

import java.util.Collections;
import java.util.List;


public class CountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mSpinner;
    private RecyclerView mRecyclerView;
    private CountAdapter mAdapter;
    private InventoryDbHelper mDbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        mSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.count_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.count_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CountAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mDbHelper = new InventoryDbHelper(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                showTotalInventory();
                break;
            case 1:
                showSoldProducts();
                break;
            case 2:
                showLowInventory();
                break;
            case 3:
                showSupplierProducts();
                break;
            case 4:
                showPriceDistribution();
                break;
            // add more cases for other count options
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // do nothing
    }

    private void showTotalInventory() {
        int totalInventory = mDbHelper.getTotalInventory();
        mAdapter.setData(Collections.singletonList(
                new CountItem("Total Inventory", String.valueOf(totalInventory),0)));
    }

    private void showSoldProducts() {
        List<CountItem> soldProducts = mDbHelper.getSoldProducts();
        mAdapter.setData(soldProducts);
    }

    private void showLowInventory() {
        List<CountItem> lowInventoryProducts = mDbHelper.getLowInventoryProducts();
        mAdapter.setData(lowInventoryProducts);
    }

    private void showSupplierProducts() {
        List<CountItem> supplierProducts = mDbHelper.getSupplierProducts();
        mAdapter.setData(supplierProducts);
    }

    private void showPriceDistribution() {
        List<CountItem> priceDistribution = mDbHelper.getPriceDistribution();
        mAdapter.setData(priceDistribution);
    }


}
