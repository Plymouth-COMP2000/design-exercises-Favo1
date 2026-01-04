package com.restaurant.app.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.restaurant.app.MenuItem;

import java.util.List;

@Dao
public interface MenuItemDao {
    @Insert
    void insert(MenuItem menuItem);

    @Update
    void update(MenuItem menuItem);

    @Delete
    void delete(MenuItem menuItem);

    @Query("SELECT * FROM menu_items ORDER BY name ASC")
    LiveData<List<MenuItem>> getAllMenuItems();

    @Query("SELECT * FROM menu_items WHERE id = :menuItemId")
    LiveData<MenuItem> getMenuItemById(int menuItemId);

    @Query("SELECT * FROM menu_items WHERE needsRestock = 1 ORDER BY name ASC")
    LiveData<List<MenuItem>> getItemsNeedingRestock();
}