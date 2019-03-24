package com.example.deepak.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepak.inventoryapp.data.InventoryContract.InventoryEntry;

import com.example.deepak.inventoryapp.data.InventoryContract;

import static com.example.deepak.inventoryapp.UploadingActivity.mCurrentProductUri;


public class InventoryCursorAdapter extends CursorAdapter {
    /**
     * Constructs a new {@link InventoryCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

// Find individual views that we want to modify in the list item layout
        TextView pronameTextView = (TextView) view.findViewById(R.id.proName);
        TextView priceTextView = (TextView) view.findViewById(R.id.pricy);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quant);
        ImageView productImageView = (ImageView) view.findViewById(R.id.mainImage);
        Button saleButton = (Button) view.findViewById(R.id.sale_btn);

        // Find the columns of product attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(InventoryEntry.IMAGE);

        // Read the product attributes from the Cursor for the current product
        final String productName = cursor.getString(nameColumnIndex);
        final String price = String.valueOf(cursor.getInt(priceColumnIndex));
        final String quanity = String.valueOf(cursor.getInt(quantityColumnIndex));
        final byte[] image = cursor.getBlob(imageColumnIndex);
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bmp);
        roundedBitmapDrawable.setCircular(true);
        productImageView.setImageDrawable(roundedBitmapDrawable);

// Update the TextViews with the attributes for the current product
        pronameTextView.setText(productName);
        priceTextView.setText(price);
        quantityTextView.setText(quanity);


        final int position = cursor.getPosition();
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                cursor.moveToPosition(position);

                int currentQuantity = Integer.parseInt(quanity);

                int id = (int) cursor.getLong(cursor.getColumnIndex("_id"));

                if (currentQuantity - 1 < 0) {
                    return;
                }

                Uri currentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                ContentValues values = new ContentValues();

                values.put(InventoryEntry.QUANTITY, currentQuantity - 1);

                // updating the database

                context.getContentResolver().update(currentProductUri, values, null, null);
            }
        });


    }


}
