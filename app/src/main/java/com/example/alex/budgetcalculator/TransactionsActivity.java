package com.example.alex.budgetcalculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alex.budgetcalculator.data.BalanceContract;
import com.example.alex.budgetcalculator.data.BalanceContract.BalanceEntry;
import com.example.alex.budgetcalculator.data.TransactionsDbHelper;
import com.example.alex.budgetcalculator.data.TransactionsContract.TransactionEntry;

public class TransactionsActivity extends AppCompatActivity {

    private TransactionsDbHelper mDbHelper =  new TransactionsDbHelper(this);
    public static int totalBudget = Balance.balance;
    private TextView mTotalBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransactionsActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new TransactionsDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();

        //displaySTUF();
    }

//    private void displaySTUF() {
//    }
//    SQLiteDatabase db = mDbHelper.getReadableDatabase(); {
//
//    // Perform this raw SQL query "SELECT * FROM pets"
//    // to get a Cursor that contains all rows from the pets table.
//    Cursor cursor = db.rawQuery("SELECT * FROM " + BalanceEntry.TABLE_NAME, null);
//        try
//    {
//        // Display the number of rows in the Cursor (which reflects the number of rows in the
//        // pets table in the database).
//        TextView displayView = (TextView) findViewById(R.id.text_view_pet);
//        displayView.setText("Number of rows in pets database table: " + cursor.getCount());
//    } finally
//
//    {
//        // Always close the cursor when you're done reading from it. This releases all its
//        // resources and makes it invalid.
//        cursor.close();
//    }
//
//}

    private void displayDatabaseBalance() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BalanceContract.BalanceEntry.COLUMN_BALANCE };


        Cursor cursor = db.query(
                BalanceContract.BalanceEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null );

        TextView mTotalBudgetView = findViewById(R.id.transaction_totalBudget_textVIew);

        try {




        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.

            cursor.close();
        }

    }


    private void displayDatabaseInfo() {

        //TransactionsDbHelper mDbHelper = new TransactionsDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                TransactionEntry._ID,
                TransactionEntry.COLUMN_TRANS_NAME,
                TransactionEntry.COLUMN_CATEGORY,
                TransactionEntry.COLUMN_AMOUNT };

        Cursor cursor = db.query(
                TransactionEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null );

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.

            displayView.setText("The transactions table contains " + cursor.getCount() + " transactions.\n\n");
            displayView.append(TransactionEntry._ID + " - " +
                    TransactionEntry.COLUMN_TRANS_NAME + " - " +
                    TransactionEntry.COLUMN_CATEGORY + " - " +
                    TransactionEntry.COLUMN_AMOUNT + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(TransactionEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_TRANS_NAME);
            int categoryColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_CATEGORY);
            int amountColumnIndex = cursor.getColumnIndex(TransactionEntry.COLUMN_AMOUNT);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract  the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentTransName = cursor.getString(nameColumnIndex);
                int currentCategory = cursor.getInt(categoryColumnIndex);
                int currentAmount = cursor.getInt(amountColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentTransName + "  -  " + currentCategory + "  -  " +
                        + currentAmount ));

            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.

            cursor.close();
        }
    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_transactions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertTestTransaction();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertTestTransaction() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        totalBudget +=100;

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(TransactionEntry.COLUMN_TRANS_NAME, "Dummy data" );
        values.put(TransactionEntry.COLUMN_CATEGORY, TransactionEntry.CATEGORY_INCOME);
        values.put(TransactionEntry.COLUMN_AMOUNT, 100);


        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(TransactionEntry.TABLE_NAME, null, values);
    }

}
