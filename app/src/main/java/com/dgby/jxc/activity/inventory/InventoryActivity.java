package com.dgby.jxc.activity.inventory;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dgby.jxc.R;
import com.dgby.jxc.activity.goods.ProductDbHelper;

/**
 * 库存管理
 */
public class InventoryActivity extends AppCompatActivity {

    private EditText mProductNameEditText;
    private EditText mQuantityEditText;
    private EditText mProductPriceEditText;
    private EditText mSupplierNameEditText;
    private EditText mSupplierPhoneEditText;
    private EditText mSupplierSoldEditText;
    private Button mAddButton;
    private Button mSubtractButton;
    private Button mQueryButton;
    private TextView mInventoryTextView;

    private int mInventory = 0;

    private  InventoryDbHelper mDatabaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_inventory);
        mDatabaseHelper = new InventoryDbHelper(this);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        mProductNameEditText = findViewById(R.id.product_name_edittext);
        mQuantityEditText = findViewById(R.id.quantity_edittext);
        mProductPriceEditText = findViewById(R.id.price_edittext);
        mSupplierNameEditText = findViewById(R.id.supplier_name_edittext);
        mSupplierPhoneEditText = findViewById(R.id.supplier_phone_edittext);
        mSupplierSoldEditText = findViewById(R.id.quantity_sold_edittext);

        mAddButton = findViewById(R.id.add_button);
        mSubtractButton = findViewById(R.id.subtract_button);
        mQueryButton = findViewById(R.id.query_button);
        mInventoryTextView = findViewById(R.id.inventory_textview);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = mProductNameEditText.getText().toString();
                String quantityStr = mQuantityEditText.getText().toString();
                String price = mProductPriceEditText.getText().toString();
                String name = mSupplierNameEditText.getText().toString();
                String phone = mSupplierPhoneEditText.getText().toString();

                if (productName.isEmpty() || quantityStr.isEmpty()) {
                    return;
                }
                int quantity = Integer.parseInt(quantityStr);
                long newRowId = mDatabaseHelper.insertInventory(productName,Integer.valueOf(price),Integer.valueOf(quantityStr),name,phone);
                if (newRowId == -1) {
                    Toast.makeText(getApplicationContext(), "Error while adding inventory", Toast.LENGTH_SHORT).show();
                } else {
                    mInventory += quantity;
                    mInventoryTextView.setText(
                            "产品名称："+productName+
                            "产品数量："+mInventory+
                            "产品价格："+price+
                            "供应商名字："+name+
                            "供应商电弧："+phone);
                    mProductNameEditText.setText("");
                    mQuantityEditText.setText("");
                    mProductPriceEditText.setText("");
                    mSupplierNameEditText.setText("");
                    mSupplierPhoneEditText.setText("");
                }
            }
        });

        mSubtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = mProductNameEditText.getText().toString();
                String quantityStr = mSupplierSoldEditText.getText().toString();
                if (productName.isEmpty() || quantityStr.isEmpty()) {
                    return;
                }
                int quantity = Integer.parseInt(quantityStr);
                if (mInventory < quantity) {
                    Toast.makeText(getApplicationContext(), "Inventory not sufficient", Toast.LENGTH_SHORT).show();
                    return;
                }
                long newRowId = mDatabaseHelper.subtractFromInventory(productName,quantity);
                if (newRowId == -1) {
                    Toast.makeText(getApplicationContext(), "减去已售数量成功", Toast.LENGTH_SHORT).show();
                } else {
                    mInventory -= quantity;
                    mInventoryTextView.setText(String.valueOf(mInventory));
                    mProductNameEditText.setText("");
                    mQuantityEditText.setText("");
                    mProductPriceEditText.setText("");
                    mSupplierNameEditText.setText("");
                    mSupplierPhoneEditText.setText("");
                }

            }
        });
        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = mProductNameEditText.getText().toString();
                if (productName.isEmpty()) {
                    return;
                }
                int quantity = mDatabaseHelper.querInventory(productName);
                mInventoryTextView.setText("产品数量："+quantity);
                mProductNameEditText.setText("");
            }
        });
    }
}