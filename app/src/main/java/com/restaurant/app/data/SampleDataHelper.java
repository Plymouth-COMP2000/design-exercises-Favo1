package com.restaurant.app.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.restaurant.app.MenuItem;
import com.restaurant.app.Table;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleDataHelper {

    private static final String PREF_NAME = "sample_data_prefs";
    private static final String KEY_SAMPLE_DATA_ADDED = "sample_data_added";

    public static void addSampleMenuItems(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        
        // Check if sample data has already been added
        if (prefs.getBoolean(KEY_SAMPLE_DATA_ADDED, false)) {
            return; // Already added
        }

        AppDatabase database = AppDatabase.getDatabase(context);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            // Add sample menu items
            database.menuItemDao().insert(new MenuItem("Margherita Pizza", 12.99, ""));
            database.menuItemDao().insert(new MenuItem("Cheeseburger", 10.99, ""));
            database.menuItemDao().insert(new MenuItem("Caesar Salad", 8.99, ""));
            database.menuItemDao().insert(new MenuItem("Grilled Salmon", 18.99, ""));
            database.menuItemDao().insert(new MenuItem("Spaghetti Carbonara", 14.99, ""));
            database.menuItemDao().insert(new MenuItem("Chocolate Cake", 6.99, ""));
            database.menuItemDao().insert(new MenuItem("Iced Coffee", 4.99, ""));
            database.menuItemDao().insert(new MenuItem("French Fries", 4.49, ""));

            // Add sample tables
            database.tableDao().insert(new Table("Table 1", 4, "Available"));
            database.tableDao().insert(new Table("Table 2", 4, "Available"));
            database.tableDao().insert(new Table("Table 3", 2, "Available"));
            database.tableDao().insert(new Table("Table 4", 2, "Available"));
            database.tableDao().insert(new Table("Table 5", 6, "Occupied"));
            database.tableDao().insert(new Table("Table 6", 6, "Available"));
            database.tableDao().insert(new Table("Table 7", 8, "Reserved"));
            database.tableDao().insert(new Table("Table 8", 8, "Available"));
            database.tableDao().insert(new Table("Table 9", 4, "Reserved"));
            database.tableDao().insert(new Table("Table 10", 4, "Available"));

            // Mark sample data as added
            prefs.edit().putBoolean(KEY_SAMPLE_DATA_ADDED, true).apply();
        });
    }
}
