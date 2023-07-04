package com.dgby.jxc.activity.inventory;

import android.provider.BaseColumns;

import android.provider.BaseColumns;

public final class InventoryContract {



    private InventoryContract() {}

    public static final class InventoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";
        public static final String COLUMN_QUANTITY_SOLD = "quantity_sold";

        public static final String[] ALL_COLUMNS = {
                _ID,
                COLUMN_PRODUCT_NAME,
                COLUMN_PRODUCT_PRICE,
                COLUMN_PRODUCT_QUANTITY,
                COLUMN_SUPPLIER_NAME,
                COLUMN_SUPPLIER_PHONE,
                COLUMN_QUANTITY_SOLD,
        };

    }
//    public static abstract class ProductEntry implements BaseColumns {
//        public static final String TABLE_NAME = "products";
//        public static final String COLUMN_NAME = "name";
//        public static final String COLUMN_QUANTITY = "quantity";
//        public static final String COLUMN_PRICE = "price";
//        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
//        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
//        public static final String COLUMN_QUANTITY_SOLD = "quantity_sold";
//    }

}
