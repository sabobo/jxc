package com.dgby.jxc.activity.inventory;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dgby.jxc.activity.count.CountItem;

import java.util.ArrayList;
import java.util.List;

public class InventoryDbHelper extends SQLiteOpenHelper {

    // 数据库名称和版本号
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    // 创建数据库表的 SQL 语句
    private static final String SQL_CREATE_INVENTORY_TABLE =
            "CREATE TABLE " + InventoryContract.InventoryEntry.TABLE_NAME + " (" +
                    InventoryContract.InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL, " +
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, " +
                    InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME + " TEXT, " +
                    InventoryContract.InventoryEntry.COLUMN_QUANTITY_SOLD + " TEXT, " +
                    InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE + " TEXT);";

    // 删除数据库表的 SQL 语句
    private static final String SQL_DELETE_INVENTORY_TABLE =
            "DROP TABLE IF EXISTS " + InventoryContract.InventoryEntry.TABLE_NAME;
    public static final int LOW_INVENTORY_THRESHOLD = 10;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库表
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除旧的数据库表
        db.execSQL(SQL_DELETE_INVENTORY_TABLE);
        // 创建新的数据库表
        onCreate(db);
    }

    // 插入新的库存记录
    public long insertInventory(String productName, int price, int quantity, String supplierName, String supplierPhone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, productName);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME, supplierName);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, supplierPhone);
       // values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY_SOLD, quantity_sold);

        long result = db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);
        db.close();
        return result;
    }

    // 从库存中减去指定数量
    @SuppressLint("Range")
    public long subtractFromInventory(String productName, int quantity) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY +
                        " FROM " + InventoryContract.InventoryEntry.TABLE_NAME +
                        " WHERE " + InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + "=?",
                new String[]{String.valueOf(productName)}
        );
        if (cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(cursor.getColumnIndex(
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY));
            int newQuantity = currentQuantity - quantity;
            if (newQuantity < 0) {
                newQuantity = 0;
            }
            ContentValues values = new ContentValues();
            values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY_SOLD, newQuantity);

            String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + "=?";
            String[] selectionArgs = {String.valueOf(productName)};

            long result =    db.update(InventoryContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
            cursor.close();
            return result;
        }

        cursor.close();
        return 0;
    }

    // 查询库存
    @SuppressLint("Range")
    public int querInventory(String productName) {
        SQLiteDatabase db = getWritableDatabase();
        String[] projection = {
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY
        };
        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + "=?";
        String[] selectionArgs = {productName};
        Cursor cursor = db.query(
                InventoryContract.InventoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int quantity = 0;
        if (cursor.moveToFirst()) {
             quantity = cursor.getInt(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY));
        } else {
            quantity = 0;
        }
        cursor.close();
        return  quantity;

    }

    // 更新库存记录
    public void updateInventory(long id, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantity);

        String selection = InventoryContract.InventoryEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        db.update(InventoryContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    // 查询所有库存记录
    public Cursor queryAllInventory() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE};

        return db.query(InventoryContract.InventoryEntry.TABLE_NAME,
                projection, null, null, null, null, null);
    }

    public int getTotalInventory() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY
        };
        Cursor cursor = db.query(
                InventoryContract.InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        int totalInventory = 0;
        try {
            while (cursor.moveToNext()) {
                int quantityIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY);
                int quantity = cursor.getInt(quantityIndex);
                totalInventory += quantity;
            }
        } finally {
            cursor.close();
        }
        return totalInventory;
    }

    public List<CountItem> getSoldProducts() {
        List<CountItem> soldProducts = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                "SUM(" + InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY + ") as total_sold"
        };

        String groupBy = InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME;

        Cursor cursor = db.query(
                InventoryContract.InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                groupBy,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME));
            int totalSold = cursor.getInt(cursor.getColumnIndexOrThrow("total_sold"));

            soldProducts.add(new CountItem(productName, String.valueOf(totalSold),1));
        }

        cursor.close();
        db.close();

        return soldProducts;
    }

    public List<CountItem> getLowInventoryProducts() {
        List<CountItem> lowInventoryProducts = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY
        };

        String selection = InventoryContract.InventoryEntry.COLUMN_PRODUCT_QUANTITY + " < ?";
        String[] selectionArgs = {String.valueOf(LOW_INVENTORY_THRESHOLD)};

        Cursor cursor = db.query(
                InventoryContract.InventoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(
                    InventoryContract.InventoryEntry.COLUMN_QUANTITY_SOLD));

            lowInventoryProducts.add(new CountItem(productName, String.valueOf(quantity),2));
        }

        cursor.close();
        db.close();

        return lowInventoryProducts;
    }

    public List<CountItem> getSupplierProducts() {
        List<CountItem> supplierProducts = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME,
                "COUNT(*) as product_count"
        };

        String groupBy = InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME;

        Cursor cursor = db.query(
                InventoryContract.InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                groupBy,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String supplierName = cursor.getString(cursor.getColumnIndexOrThrow(
                    InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME));
            int productCount = cursor.getInt(cursor.getColumnIndexOrThrow("product_count"));

            supplierProducts.add(new CountItem(supplierName, String.valueOf(productCount),3));
        }

        cursor.close();
        db.close();

        return supplierProducts;
    }
    @SuppressLint("Range")
    public List<CountItem> getPriceDistribution() {
        SQLiteDatabase db = getReadableDatabase();

        // Query the database to get the price distribution
        Cursor cursor = db.rawQuery("SELECT price_range, COUNT(*) AS count FROM (" +
                "SELECT CASE " +
                "  WHEN price <= 10 THEN 'Under ¥10' " +
                "  WHEN price <= 20 THEN '¥10-¥20' " +
                "  WHEN price <= 50 THEN '¥20-¥50' " +
                "  WHEN price <= 100 THEN '¥50-¥100' " +
                "  ELSE 'Over ¥100' " +
                "END AS price_range " +
                "FROM " + InventoryContract.InventoryEntry.TABLE_NAME +
                ") GROUP BY price_range;", null);

        List<CountItem> priceDistribution = new ArrayList<>();

        // Convert the cursor to a list of CountItem objects
        if (cursor.moveToFirst()) {
            do {
               String priceRange = cursor.getString(cursor.getColumnIndex("price_range"));
                int count = cursor.getInt(cursor.getColumnIndex("count"));
                priceDistribution.add(new CountItem(priceRange, String.valueOf(count),4));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return priceDistribution;
    }

}
