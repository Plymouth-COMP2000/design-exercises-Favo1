package com.restaurant.app.data;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.restaurant.app.Order;

import java.util.List;

public class OrderRepository {
    private OrderDao orderDao;
    private LiveData<List<Order>> allOrders;

    public OrderRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        orderDao = database.orderDao();
        allOrders = orderDao.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public LiveData<List<Order>> getOrdersByStatus(String status) {
        return orderDao.getOrdersByStatus(status);
    }

    public LiveData<List<Order>> getOrdersByDate(String date) {
        return orderDao.getOrdersByDate(date);
    }

    public LiveData<Double> getDailyRevenue(String date) {
        return orderDao.getDailyRevenue(date);
    }

    public void insert(Order order) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.insert(order);
        });
    }

    public void update(Order order) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.update(order);
        });
    }

    public void delete(Order order) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.delete(order);
        });
    }
}
