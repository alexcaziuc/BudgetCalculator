package com.example.alex.budgetcalculator.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the Budget app.
 */
public final class TransactionsContract {

    public static final String CONTENT_AUTHORITY = "com.example.alex.budgetcalculator";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRANSACTIONS = "transactions";
    private TransactionsContract() {}

    /**
     * Inner class that defines constant values for the transactions database table.
     * Each entry in the table represents a single transactions.
     */
    public static final class TransactionEntry implements BaseColumns {

        /** Name of database table for transactions */
        public final static String TABLE_NAME = "transactions";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TRANS_NAME ="name";
        public final static String COLUMN_CATEGORY = "category";
        public final static String COLUMN_AMOUNT = "amount";

        public static final Boolean CATEGORY_INCOME = true;
        public static final Boolean CATEGORY_EXPENSE = false;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRANSACTIONS);
    }
}