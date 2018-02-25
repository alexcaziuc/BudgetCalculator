package com.example.alex.budgetcalculator.data;

import android.provider.BaseColumns;

/**
 * Created by Alex on 25-Feb-18.
 */

public class BalanceContract {

    private BalanceContract() {
    }

    /**
     * Inner class that defines constant values for the transactions database table.
     * The entry in the table represents the balance.
     */
    public static final class BalanceEntry implements BaseColumns {

        /**
         * Name of database table for transactions
         */
        public final static String TABLE_NAME = "balance";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_BALANCE = "total";
    }
}