package com.restaurant.app;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.restaurant.app.data.MenuRepository;

import java.util.List;

public class MenuViewModel extends AndroidViewModel {
    private final MenuRepository repository;
    private final LiveData<List<MenuItem>> allMenuItems;

    public MenuViewModel(Application application) {
        super(application);
        repository = new MenuRepository(application);
        allMenuItems = repository.getAllMenuItems();
    }

    public LiveData<List<MenuItem>> getAllMenuItems() {
        return allMenuItems;
    }

    public void insert(MenuItem menuItem) {
        repository.insert(menuItem);
    }

    public void update(MenuItem menuItem) {
        repository.update(menuItem);
    }

    public void delete(MenuItem menuItem) {
        repository.delete(menuItem);
    }
}