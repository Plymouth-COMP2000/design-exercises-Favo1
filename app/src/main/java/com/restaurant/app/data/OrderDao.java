package com.restaurant.app.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.restaurant.app.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("SELECT * FROM orders ORDER BY id DESC")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM orders WHERE id = :orderId")
    LiveData<Order> getOrderById(int orderId);

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY id DESC")
    LiveData<List<Order>> getOrdersByStatus(String status);

    @Query("SELECT * FROM orders WHERE orderDate = :date ORDER BY id DESC")
    LiveData<List<Order>> getOrdersByDate(String date);

    @Query("SELECT SUM(totalPrice) FROM orders WHERE orderDate = :date AND status != 'Cancelled'")
    LiveData<Double> getDailyRevenue(String date);
}
