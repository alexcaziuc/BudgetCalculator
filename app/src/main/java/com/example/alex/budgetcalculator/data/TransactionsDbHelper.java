package com.example.alex.budgetcalculator.data;

/**
 * Created by Alex on 24-Feb-18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.alex.budgetcalculator.data.TransactionsContract.TransactionEntry;
import com.example.alex.budgetcalculator.data.BalanceContract.BalanceEntry;

/**
 * Database helper for Budget app. Manages database creation and version management.
 */
public class TransactionsDbHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = TransactionsDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "budget.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link TransactionsDbHelper}.
     *
     * @param context of the app
     */
    public TransactionsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the transactions table
        String SQL_CREATE_TRANS_TABLE =  "CREATE TABLE " + TransactionEntry.TABLE_NAME + " ("
                + TransactionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TransactionEntry.COLUMN_TRANS_NAME + " TEXT NOT NULL, "
                + TransactionEntry.COLUMN_CATEGORY + " INTEGER NOT NULL DEFAULT 2, "
                + TransactionEntry.COLUMN_AMOUNT + " INTEGER NOT NULL );";

        // Create a String that contains the SQL statement to create the balance table
        String SQL_CREATE_BALANCE_TABLE =  "CREATE TABLE " + BalanceEntry.TABLE_NAME + " ("
                + BalanceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BalanceEntry.COLUMN_BALANCE + " INTEGER NOT NULL DEFAULT 0 );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRANS_TABLE);
        db.execSQL(SQL_CREATE_BALANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
