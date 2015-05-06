package be.ehb.dt.mycontentprovider;

import android.provider.BaseColumns;

/**
 * Created by bert on 6/05/2015.
 */
public final class ShoppingCartContract {

    //empty constructor
    public ShoppingCartContract(){}

    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_PRODUCT_ENTRIES =
            "CREATE TABLE " + ProductEntry.TABLE_NAME + " (" +
                    ProductEntry._ID + " INTEGER PRIMARY KEY," +
                    ProductEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ProductEntry.COLUMN_NAME_AMOUNT + INTEGER_TYPE +
                    " )";
            ;

    public static final String SQL_DELETE_PRODUCT_ENTRIES =
            "DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME;


    public static abstract class ProductEntry implements BaseColumns{
        public static final String TABLE_NAME = "products";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}
