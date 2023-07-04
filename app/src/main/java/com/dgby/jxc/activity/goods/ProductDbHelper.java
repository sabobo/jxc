package com.dgby.jxc.activity.goods;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class  ProductDbHelper extends SQLiteOpenHelper {
    // 数据库名称
    private static final String DATABASE_NAME = "product.db";
    // 数据库版本号
    private static final int DATABASE_VERSION = 1;

    // 商品表名称
    private static final String TABLE_NAME = "product";
    // 商品表列名
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_STOCK = "stock";
    private static final String COLUMN_DESCRIPTION = "description";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建商品表
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_PRICE + " REAL," +
                COLUMN_STOCK + " INTEGER," +
                COLUMN_DESCRIPTION + " TEXT" +
                ")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果数据库版本号变化，执行更新操作
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    // 添加商品
    public long addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_STOCK, product.getStock());
        values.put(COLUMN_DESCRIPTION, product.getDescription());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result;
    }

    // 更新商品
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_STOCK, product.getStock());
        values.put(COLUMN_DESCRIPTION, product.getDescription());

        int result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
        db.close();

        return result;
    }

    // 删除商品
    public int deleteProduct(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();

        return result;
    }

    // 查询所有商品
    @SuppressLint("Range")
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        String SELECT_ALL_PRODUCTS = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_PRODUCTS, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)));
                product.setStock(cursor.getInt(cursor.getColumnIndex(COLUMN_STOCK)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }

         // 根据ID查询商品
        @SuppressLint("Range")
        public Product getProductById(long id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, null, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(id)}, null, null, null);

            Product product = null;
            if (cursor.moveToFirst()) {
                product = new Product();
                product.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)));
                product.setStock(cursor.getInt(cursor.getColumnIndex(COLUMN_STOCK)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            }

            cursor.close();
            db.close();

            return product;
        }
    // method to delete all products
    public void deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}