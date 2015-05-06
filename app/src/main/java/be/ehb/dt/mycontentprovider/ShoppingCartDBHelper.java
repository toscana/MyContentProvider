package be.ehb.dt.mycontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import be.ehb.dt.mycontentprovider.ShoppingCartContract;

/**
 * Created by bert on 6/05/2015.
 */
public class ShoppingCartDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "Shoppingcart.db";

    public ShoppingCartDBHelper(Context context){
        //super()
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ShoppingCartContract.SQL_CREATE_PRODUCT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ShoppingCartContract.SQL_DELETE_PRODUCT_ENTRIES);
        onCreate(db);
    }
}
