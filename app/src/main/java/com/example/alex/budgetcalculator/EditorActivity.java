package com.example.alex.budgetcalculator;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.budgetcalculator.data.BalanceContract;
import com.example.alex.budgetcalculator.data.TransactionsContract.TransactionEntry;
import com.example.alex.budgetcalculator.data.BalanceContract.BalanceEntry;
import com.example.alex.budgetcalculator.data.TransactionsDbHelper;

/**
 * Created by Alex on 24-Feb-18.
 */
    public class EditorActivity extends AppCompatActivity {

        private TransactionsDbHelper mDbHelper;

        /** EditText field to enter the category name */
        private EditText mNameEditText;

        /** EditText field to enter the amount */
        private EditText mAmountEditText;

        /** EditText field to enter the category type */
        private Spinner mCategorySpinner;

        private TextView mTotalBudget;

        private static int totalBudget = Balance.balance;

        /**
         * Category of the transaction. The possible values are:
         *  1 for income, 2 for expense.
         */
        private boolean mCategory = TransactionEntry.CATEGORY_EXPENSE;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_editor);

            // Find all relevant views that we will need to read user input from
            mNameEditText = (EditText) findViewById(R.id.edit_category_name);
            mAmountEditText = (EditText) findViewById(R.id.edit_amount);
            mCategorySpinner = (Spinner) findViewById(R.id.spinner_category_transaction);
            mTotalBudget = findViewById(R.id.editor_totalBudget_textView);

            setupSpinner();

            mDbHelper = new TransactionsDbHelper(this);

        }

    @Override
    protected void onStart() {
        super.onStart();
        mTotalBudget.setText(Integer.toString(Balance.balance));

    }

        /**
         * Setup the dropdown spinner that allows the user to select the type of the transaction.
         */
        private void setupSpinner() {
            // Create adapter for spinner. The list options are from the String array it will use
            // the spinner will use the default layout
            ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                    R.array.array_gender_options, android.R.layout.simple_spinner_item);

            // Specify dropdown layout style - simple list view with 1 item per line
            genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

            // Apply the adapter to the spinner
            mCategorySpinner.setAdapter(genderSpinnerAdapter);

            // Set the integer mSelected to the constant values
            mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selection = (String) parent.getItemAtPosition(position);
                    if (!TextUtils.isEmpty(selection)) {
                        if (selection.equals(getString(R.string.transaction_type_income))) {
                            mCategory = TransactionEntry.CATEGORY_INCOME; // Income true
                        } else if (selection.equals(getString(R.string.transaction_type_expense))) {
                            mCategory = TransactionEntry.CATEGORY_EXPENSE; // Expense false
                        } else
                        {
                            Toast.makeText(EditorActivity.this, "Select a category", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                // Because AdapterView is an abstract class, onNothingSelected must be defined
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mCategory = false; // Expense
                }
            });
        }



    private void insertIncomeTransaction() {
        String transName = mNameEditText.getText().toString().trim();
        String amountString = mAmountEditText.getText().toString().trim();
        int amount = Integer.parseInt(amountString);

        Balance.balance += amount;

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        ContentValues incomeValues = new ContentValues();
        incomeValues.put(TransactionEntry.COLUMN_TRANS_NAME, transName );
        incomeValues.put(TransactionEntry.COLUMN_CATEGORY, TransactionEntry.CATEGORY_INCOME);
        incomeValues.put(TransactionEntry.COLUMN_AMOUNT, amount);

        ContentValues balanceValues = new ContentValues();
        balanceValues.put(BalanceEntry.COLUMN_BALANCE, amount);

        long newRowId = db.insert(TransactionEntry.TABLE_NAME, null, incomeValues);

        long confirmBalanceUpdate = db.update(BalanceEntry.TABLE_NAME, balanceValues,
                null, null);



        if(confirmBalanceUpdate == -1)
            Toast.makeText(this,"Error when saving the balance", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Balance updated", Toast.LENGTH_SHORT).show();


        if(newRowId == -1)
            Toast.makeText(this,"Error when saving income transaction", Toast.LENGTH_LONG).show();
         else
            Toast.makeText(this,"Income Transaction saved", Toast.LENGTH_SHORT).show();
    }

    private void insertExpenseTransaction() {

        String transName = mNameEditText.getText().toString().trim();
        String amountString = mAmountEditText.getText().toString().trim();
        int amount = Integer.parseInt(amountString);

        Balance.balance -= amount;

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        ContentValues values = new ContentValues();
        values.put(TransactionEntry.COLUMN_TRANS_NAME, transName );
        values.put(TransactionEntry.COLUMN_CATEGORY, TransactionEntry.CATEGORY_EXPENSE);
        values.put(TransactionEntry.COLUMN_AMOUNT, amount);

        long newRowId = db.insert(TransactionEntry.TABLE_NAME, null, values);

        if(newRowId == -1)
            Toast.makeText(this,"Error when saving expense transaction", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Expense Transaction saved", Toast.LENGTH_SHORT).show();
    }















        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu options from the res/menu/menu_editor.xml file.
            // This adds menu items to the app bar.
            getMenuInflater().inflate(R.menu.menu_editor, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // User clicked on a menu option in the app bar overflow menu
            switch (item.getItemId()) {
                // Respond to a click on the "Save" menu option
                case R.id.action_save:

                    if(TransactionEntry.CATEGORY_INCOME)
                        insertIncomeTransaction();
                    else
                        insertExpenseTransaction();

                    finish();
                    return true;
                // Respond to a click on the "Delete" menu option
                case R.id.action_delete:
                    // Do nothing for now
                    return true;
                // Respond to a click on the "Up" arrow button in the app bar
                case android.R.id.home:
                    // Navigate back to parent activity (CatalogActivity)
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }


}
