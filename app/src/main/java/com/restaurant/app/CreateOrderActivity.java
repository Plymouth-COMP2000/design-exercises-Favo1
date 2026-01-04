package com.restaurant.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateOrderActivity extends AppCompatActivity implements OrderItemAdapter.OnQuantityChangeListener {

    private EditText etCustomerName;
    private EditText etOrderNotes;
    private RecyclerView recyclerMenuItems;
    private Button btnPlaceOrder;
    private OrderItemAdapter adapter;
    private MenuViewModel menuViewModel;
    private OrderViewModel orderViewModel;
    private List<OrderItem> orderItems = new ArrayList<>();
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Create Order");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        currentUsername = prefs.getString("username", "");

        etCustomerName = findViewById(R.id.etCustomerName);
        etOrderNotes = findViewById(R.id.etOrderNotes);
        recyclerMenuItems = findViewById(R.id.recyclerMenuItems);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        recyclerMenuItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderItemAdapter(orderItems, this);
        recyclerMenuItems.setAdapter(adapter);

        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);

        menuViewModel.getAllMenuItems().observe(this, menuItems -> {
            orderItems.clear();
            for (MenuItem item : menuItems) {
                orderItems.add(new OrderItem(item, 0));
            }
            adapter.notifyDataSetChanged();
        });

        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    @Override
    public void onQuantityChanged() {
        updateTotal();
    }

    private void updateTotal() {
        double total = 0;
        for (OrderItem item : orderItems) {
            total += item.getMenuItem().getPrice() * item.getQuantity();
        }
        btnPlaceOrder.setText(String.format(Locale.getDefault(), "Place Order - $%.2f", total));
    }

    private void placeOrder() {
        String customerName = etCustomerName.getText().toString().trim();
        
        if (customerName.isEmpty()) {
            Toast.makeText(this, "Please enter customer name", Toast.LENGTH_SHORT).show();
            return;
        }

        List<OrderItem> selectedItems = new ArrayList<>();
        for (OrderItem item : orderItems) {
            if (item.getQuantity() > 0) {
                selectedItems.add(item);
            }
        }

        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "Please select at least one item", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = 0;
        for (OrderItem item : selectedItems) {
            total += item.getMenuItem().getPrice() * item.getQuantity();
        }

        // Convert to JSON
        Gson gson = new Gson();
        String orderItemsJson = gson.toJson(selectedItems);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        Order order = new Order(customerName, orderItemsJson, total, "Pending", currentDate, currentTime);
        order.setCreatedBy(currentUsername);
        
        String notes = etOrderNotes.getText().toString().trim();
        order.setNotes(notes);

        orderViewModel.insert(order);
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static class OrderItem {
        private MenuItem menuItem;
        private int quantity;

        public OrderItem(MenuItem menuItem, int quantity) {
            this.menuItem = menuItem;
            this.quantity = quantity;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
