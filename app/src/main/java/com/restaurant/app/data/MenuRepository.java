package com.restaurant.app.data;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.restaurant.app.MenuItem;

import java.util.List;

public class MenuRepository {
    private MenuItemDao menuItemDao;
    private LiveData<List<MenuItem>> allMenuItems;

    public MenuRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        menuItemDao = database.menuItemDao();
        allMenuItems = menuItemDao.getAllMenuItems();
    }

    public LiveData<List<MenuItem>> getAllMenuItems() {
        return allMenuItems;
    }

    public LiveData<List<MenuItem>> getItemsNeedingRestock() {
        return menuItemDao.getItemsNeedingRestock();
    }

    public void insert(MenuItem menuItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            menuItemDao.insert(menuItem);
        });
    }

    public void update(MenuItem menuItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            menuItemDao.update(menuItem);
        });
    }

    public void delete(MenuItem menuItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            menuItemDao.delete(menuItem);
        });
    }
}