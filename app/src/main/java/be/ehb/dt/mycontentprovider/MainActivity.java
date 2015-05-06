package be.ehb.dt.mycontentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import static be.ehb.dt.mycontentprovider.ShoppingCartContract.ProductEntry;


public class MainActivity extends AppCompatActivity {


    private ProductsDataSource mProductsDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProductsDataSource = new ProductsDataSource(this);

        try {
            mProductsDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addClicked(View v){
        EditText nameField = (EditText) findViewById(R.id.editTextProductName);
        EditText descriptionField = (EditText) findViewById(R.id.editTextProductDescription);
        EditText amountField = (EditText) findViewById(R.id.editTextAmount);

        String name = nameField.getText().toString();
        String description = descriptionField.getText().toString();
        int amount = Integer.valueOf(amountField.getText().toString());

        Product p = mProductsDataSource.addProduct(name, description, amount);
        Toast toast = Toast.makeText(this, p.getName() + " was added", Toast.LENGTH_LONG);
        toast.show();

    }


    public void showClicked(View v){
        int i;

        List<Product> productList = mProductsDataSource.getAllProducts();

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(productList.get(0).getDescription());

    }

    public void addButtonCPClicked(View v){
        EditText nameField = (EditText) findViewById(R.id.editTextProductName);
        EditText descriptionField = (EditText) findViewById(R.id.editTextProductDescription);
        EditText amountField = (EditText) findViewById(R.id.editTextAmount);

        String name = nameField.getText().toString();
        String description = descriptionField.getText().toString();
        int amount = Integer.valueOf(amountField.getText().toString());

        Product p = mProductsDataSource.addProduct(name, description, amount);

        Toast toast = Toast.makeText(this, p.getName() + " was added", Toast.LENGTH_LONG);
        toast.show();
    }


    @Override
    protected void onResume() {
        try {
            mProductsDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        mProductsDataSource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
