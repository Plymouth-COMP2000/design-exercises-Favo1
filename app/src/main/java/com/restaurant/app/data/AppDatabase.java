package com.restaurant.app.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.restaurant.app.MenuItem;
import com.restaurant.app.Order;
import com.restaurant.app.Reservation;
import com.restaurant.app.Table;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MenuItem.class, Reservation.class, Table.class, Order.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MenuItemDao menuItemDao();
    public abstract ReservationDao reservationDao();
    public abstract TableDao tableDao();
    public abstract OrderDao orderDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "restaurant_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}