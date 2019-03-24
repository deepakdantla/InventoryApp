package com.example.deepak.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class InventoryContract {

    public static final String CONTENT_AUTHOR = "com.example.deepak.inventoryapp";
    public static final Uri URI_FOR_BASE_CONTENT = Uri.parse("content://" + CONTENT_AUTHOR);
    public static final String PATH_PRODUCTS = "products";


    public InventoryContract() {
    }

    public static class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(URI_FOR_BASE_CONTENT, PATH_PRODUCTS);
        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String PRODUCT_NAME = "productname";
        public static final String QUANTITY = "quantity";
        public static final String PRICE = "price";
        public static final String IMAGE = "image";

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHOR + "/" + PATH_PRODUCTS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHOR + "/" + PATH_PRODUCTS;

    }
}
