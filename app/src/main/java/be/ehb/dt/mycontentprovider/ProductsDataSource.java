package be.ehb.dt.mycontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static be.ehb.dt.mycontentprovider.ShoppingCartContract.ProductEntry;

/**
 * Created by bert on 6/05/2015.
 */
public class ProductsDataSource {

    private ShoppingCartDBHelper mShoppingCartDBHelper;
    private SQLiteDatabase mDatabase;

    String[] allColumns = {
            ProductEntry._ID,
            ProductEntry.COLUMN_NAME_NAME,
            ProductEntry.COLUMN_NAME_DESCRIPTION,
            ProductEntry.COLUMN_NAME_AMOUNT
    };


    public ProductsDataSource(Context c) {
        mShoppingCartDBHelper = new ShoppingCartDBHelper(c);
    }

    public void open() throws SQLException {
        mDatabase = mShoppingCartDBHelper.getWritableDatabase();
    }

    public void close(){
        mShoppingCartDBHelper.close();
    }

    public Product addProduct(String name, String description, int amount) {

        //Create map of values
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_NAME_NAME, name);
        values.put(ProductEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(ProductEntry.COLUMN_NAME_AMOUNT, amount);


        long insertId = mDatabase.insert(ProductEntry.TABLE_NAME, null, values);


        Cursor c = mDatabase.query(ProductEntry.TABLE_NAME,
                allColumns,
                ProductEntry._ID + " = " + insertId,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        Product p = cursorToProduct(c);
        c.close();

        return p;

    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();

        Cursor cursor = mDatabase.query(ProductEntry.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product p = cursorToProduct(cursor);
            products.add(p);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return products;
    }

    private Product cursorToProduct(Cursor c) {
        Product p = new Product();
        p.setName(c.getString(c.getColumnIndex(ProductEntry.COLUMN_NAME_NAME)));
        p.setDescription(c.getString(c.getColumnIndex(ProductEntry.COLUMN_NAME_DESCRIPTION)));
        p.setAmount(c.getInt(c.getColumnIndex(ProductEntry.COLUMN_NAME_AMOUNT)));
        return p;
    }

}
