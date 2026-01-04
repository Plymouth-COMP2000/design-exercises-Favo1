package com.restaurant.app.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.restaurant.app.Table;

import java.util.List;

@Dao
public interface TableDao {
    @Insert
    void insert(Table table);

    @Update
    void update(Table table);

    @Delete
    void delete(Table table);

    @Query("SELECT * FROM tables ORDER BY tableName ASC")
    LiveData<List<Table>> getAllTables();

    @Query("SELECT * FROM tables WHERE id = :tableId")
    LiveData<Table> getTableById(int tableId);

    @Query("SELECT * FROM tables WHERE status = :status ORDER BY tableName ASC")
    LiveData<List<Table>> getTablesByStatus(String status);
}
