package com.restaurant.app;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.restaurant.app.data.TableRepository;

import java.util.List;

public class TableViewModel extends AndroidViewModel {
    private final TableRepository repository;
    private final LiveData<List<Table>> allTables;

    public TableViewModel(Application application) {
        super(application);
        repository = new TableRepository(application);
        allTables = repository.getAllTables();
    }

    public LiveData<List<Table>> getAllTables() {
        return allTables;
    }

    public LiveData<List<Table>> getTablesByStatus(String status) {
        return repository.getTablesByStatus(status);
    }

    public void insert(Table table) {
        repository.insert(table);
    }

    public void update(Table table) {
        repository.update(table);
    }

    public void delete(Table table) {
        repository.delete(table);
    }
}
