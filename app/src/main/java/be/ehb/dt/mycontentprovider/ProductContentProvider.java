package be.ehb.dt.mycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import static be.ehb.dt.mycontentprovider.ShoppingCartContract.ProductEntry;

public class ProductContentProvider extends ContentProvider {

    private static final String AUTHORITY = "be.ehb.dt.mycontentprovider.productcontentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ProductEntry.TABLE_NAME);

    public static final int PRODUCTS = 1; //whole products table
    public static final int PRODUCTS_ID = 2; //specific product

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private ShoppingCartDBHelper mDatabaseHelper;

    static{
        sURIMatcher.addURI(AUTHORITY,ProductEntry.TABLE_NAME,PRODUCTS);
        sURIMatcher.addURI(AUTHORITY,ProductEntry.TABLE_NAME + "/#",PRODUCTS_ID);

    }


    public ProductContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsDeleted = sqlDB.delete(ProductEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case PRODUCTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ProductEntry.TABLE_NAME,
                            ProductEntry._ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(ProductEntry.TABLE_NAME,
                            ProductEntry._ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();

        long id = 0;
        switch(uriType){
            case PRODUCTS:
                id = sqlDB.insert(ProductEntry.TABLE_NAME,null,values);
                break;

            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return Uri.parse(ProductEntry.TABLE_NAME + "/" + id);


    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        mDatabaseHelper = new ShoppingCartDBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(ProductEntry.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);

        switch (uriType){
            case PRODUCTS_ID:
                queryBuilder.appendWhere(ProductEntry._ID + "="
                        + uri.getLastPathSegment());
                break;

            case PRODUCTS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        Cursor c = queryBuilder.query(mDatabaseHelper.getReadableDatabase(),projection,selection,selectionArgs,null,null,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case PRODUCTS:
                rowsUpdated =
                        sqlDB.update(ProductEntry.TABLE_NAME,
                                values,
                                selection,
                                selectionArgs);
                break;
            case PRODUCTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated =
                            sqlDB.update(ProductEntry.TABLE_NAME,
                                    values,
                                    ProductEntry._ID + "=" + id,
                                    null);
                } else {
                    rowsUpdated =
                            sqlDB.update(ProductEntry.TABLE_NAME,
                                    values,
                                    ProductEntry._ID + "=" + id
                                            + " and "
                                            + selection,
                                    selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " +
                        uri);
        }
        getContext().getContentResolver().notifyChange(uri,
                null);
        return rowsUpdated;
    }
}
