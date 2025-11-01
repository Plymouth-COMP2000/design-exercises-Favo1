//package com.restaurant.restaurantmanagementstaff;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DashboardActivity extends AppCompatActivity {
//
//    private RecyclerView rvMenuItems;
//    private MenuAdapter menuAdapter;
//    private List<MenuItem> menuItemList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//
//        // Initialize RecyclerView
//        rvMenuItems = findViewById(R.id.rvMenuItems);
//        rvMenuItems.setLayoutManager(new LinearLayoutManager(this));
//
//        // Create sample menu items
//        menuItemList = new ArrayList<>();
//        menuItemList.add(new MenuItem("Menu Item (Dessert)"));
//        menuItemList.add(new MenuItem("Menu Item (Main)"));
//        menuItemList.add(new MenuItem("Menu Item (Main)"));
//        menuItemList.add(new MenuItem("Menu Item (Main)"));
//
//        // Set adapter
//        menuAdapter = new MenuAdapter(menuItemList);
//        rvMenuItems.setAdapter(menuAdapter);
//    }
//}
