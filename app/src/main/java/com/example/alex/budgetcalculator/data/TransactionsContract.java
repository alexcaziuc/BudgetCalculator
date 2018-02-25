package com.example.alex.budgetcalculator.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Budget app.
 */
public final class TransactionsContract {

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


    }
}