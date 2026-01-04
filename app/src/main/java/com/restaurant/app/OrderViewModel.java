package com.restaurant.app;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.restaurant.app.data.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {
    private final OrderRepository repository;
    private final LiveData<List<Order>> allOrders;

    public OrderViewModel(Application application) {
        super(application);
        repository = new OrderRepository(application);
        allOrders = repository.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return allOrders;
    }

    public LiveData<List<Order>> getOrdersByStatus(String status) {
        return repository.getOrdersByStatus(status);
    }

    public LiveData<List<Order>> getOrdersByDate(String date) {
        return repository.getOrdersByDate(date);
    }

    public LiveData<Double> getDailyRevenue(String date) {
        return repository.getDailyRevenue(date);
    }

    public void insert(Order order) {
        repository.insert(order);
    }

    public void update(Order order) {
        repository.update(order);
    }

    public void delete(Order order) {
        repository.delete(order);
    }
}
