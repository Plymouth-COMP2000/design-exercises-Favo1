package com.restaurant.app.data;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.restaurant.app.Table;

import java.util.List;

public class TableRepository {
    private TableDao tableDao;
    private LiveData<List<Table>> allTables;

    public TableRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        tableDao = database.tableDao();
        allTables = tableDao.getAllTables();
    }

    public LiveData<List<Table>> getAllTables() {
        return allTables;
    }

    public LiveData<List<Table>> getTablesByStatus(String status) {
        return tableDao.getTablesByStatus(status);
    }

    public void insert(Table table) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            tableDao.insert(table);
        });
    }

    public void update(Table table) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            tableDao.update(table);
        });
    }

    public void delete(Table table) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            tableDao.delete(table);
        });
    }
}
