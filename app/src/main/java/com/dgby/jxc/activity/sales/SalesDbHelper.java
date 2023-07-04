package com.dgby.jxc.activity.sales;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dgby.jxc.activity.goods.Product;

import java.util.ArrayList;
import java.util.List;

public class SalesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sales.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "sales";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PRODUCT_NAME + " TEXT," +
                    COLUMN_PRICE + " REAL," +
                    COLUMN_AMOUNT + " INTEGER," +
                    COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SalesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * 添加销售记录
     * @param product 商品名称
     * @param price 商品单价
     * @param amount 商品数量
     * @return 插入的行号，插入失败返回 -1
     */
    public long addSaleRecord(String product, double price, int amount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_AMOUNT, amount);
        return db.insert(TABLE_NAME, null, values);
    }

    /**
     * 查询所有销售记录
     * @return 销售记录列表
     */
    @SuppressLint("Range")
    public List<SaleRecord> getAllSaleRecords() {
        List<SaleRecord> saleRecords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TIMESTAMP + " DESC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String product = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
            int amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
            String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
            saleRecords.add(new SaleRecord(id, product, price, amount, timestamp));
        }
        cursor.close();
        return saleRecords;
    }

    /**
     * 查询某个时间段内的销售记录
     * @param start 起始时间戳
     * @param end 结束时间戳
     * @return 销售记录列表
     */
    @SuppressLint("Range")
    public List<SaleRecord> getSaleRecordsByTimeRange(long start, long end) {
        List<SaleRecord> saleRecords = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_TIMESTAMP + " BETWEEN ? AND ?";
        String[] selectionArgs = {String.valueOf(start), String.valueOf(end)};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, COLUMN_TIMESTAMP + " DESC");
        while (cursor.moveToNext()) {
             int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String product = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
            int amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
            String timestamp = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
            saleRecords.add(new SaleRecord(id, product, price, amount, timestamp));
        }
        cursor.close();
        return saleRecords;
    }

    /**
     * 删除销售记录
     * @param id 销售记录id
     * @return 删除的行数
     */
    public int deleteSaleRecord(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        return db.delete(TABLE_NAME, selection, selectionArgs);
    }

    /**
     * 获取销售总金额
     * @return 销售总金额
     */
    @SuppressLint("Range")
    public double getTotalSalesAmount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_PRICE + "*" + COLUMN_AMOUNT + ") AS total FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(cursor.getColumnIndex("total"));
        }
        cursor.close();
        return total;
    }

    /**
     * 获取销售记录数量
     * @return 销售记录数量
     */
    public int getSaleRecordCount() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

}
