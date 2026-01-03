package com.restaurant.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    private MenuViewModel menuViewModel;
    private MenuAdapter menuAdapter;
    private String userType;
    private List<MenuItem> allMenuItems = new ArrayList<>();
    private SearchView searchView;

    public static final int ADD_MENU_ITEM_REQUEST = 1;
    public static final int EDIT_MENU_ITEM_REQUEST = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Get user type from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("app_prefs", Activity.MODE_PRIVATE);
        userType = prefs.getString("usertype", "guest");

        searchView = view.findViewById(R.id.searchViewMenu);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set adapter based on user type
        menuAdapter = new MenuAdapter(new ArrayList<>(), userType.equals("staff"));
        recyclerView.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(this);

        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        menuViewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
            allMenuItems = menuItems;
            menuAdapter.setMenuItems(menuItems);
        });

        // Setup search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMenuItems(newText);
                return true;
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_menu_item);
        
        // Only show FAB for staff
        if (userType.equals("staff")) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), AddEditMenuItemActivity.class);
                startActivityForResult(intent, ADD_MENU_ITEM_REQUEST);
            });
        } else {
            fab.setVisibility(View.GONE);
        }

        return view;
    }

    private void filterMenuItems(String query) {
        if (query.isEmpty()) {
            menuAdapter.setMenuItems(allMenuItems);
        } else {
            List<MenuItem> filtered = allMenuItems.stream()
                    .filter(item -> item.getName().toLowerCase().contains(query.toLowerCase()) ||
                            String.valueOf(item.getPrice()).contains(query))
                    .collect(Collectors.toList());
            menuAdapter.setMenuItems(filtered);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra(AddEditMenuItemActivity.EXTRA_NAME);
            double price = data.getDoubleExtra(AddEditMenuItemActivity.EXTRA_PRICE, 0.0);
            String imagePath = data.getStringExtra(AddEditMenuItemActivity.EXTRA_IMAGE_PATH);

            if (requestCode == ADD_MENU_ITEM_REQUEST) {
                MenuItem menuItem = new MenuItem(name, price, imagePath);
                menuViewModel.insert(menuItem);
                Toast.makeText(getContext(), "Menu Item added", Toast.LENGTH_SHORT).show();
            } else if (requestCode == EDIT_MENU_ITEM_REQUEST) {
                int id = data.getIntExtra(AddEditMenuItemActivity.EXTRA_ID, -1);
                if (id == -1) {
                    Toast.makeText(getContext(), "Menu Item can't be updated", Toast.LENGTH_SHORT).show();
                    return;
                }
                MenuItem menuItem = new MenuItem(name, price, imagePath);
                menuItem.setId(id);
                menuViewModel.update(menuItem);
                Toast.makeText(getContext(), "Menu Item updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(MenuItem menuItem) {
        if (userType.equals("staff")) {
            // Staff can edit
            Intent intent = new Intent(getContext(), AddEditMenuItemActivity.class);
            intent.putExtra(AddEditMenuItemActivity.EXTRA_ID, menuItem.getId());
            intent.putExtra(AddEditMenuItemActivity.EXTRA_NAME, menuItem.getName());
            intent.putExtra(AddEditMenuItemActivity.EXTRA_PRICE, menuItem.getPrice());
            intent.putExtra(AddEditMenuItemActivity.EXTRA_IMAGE_PATH, menuItem.getImagePath());
            startActivityForResult(intent, EDIT_MENU_ITEM_REQUEST);
        } else {
            // Guest can only view details
            new AlertDialog.Builder(getContext())
                    .setTitle(menuItem.getName())
                    .setMessage("Price: $" + String.format("%.2f", menuItem.getPrice()))
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    @Override
    public void onEditClick(MenuItem menuItem) {
        if (userType.equals("staff")) {
            onItemClick(menuItem);
        }
    }

    @Override
    public void onDeleteClick(MenuItem menuItem) {
        if (userType.equals("staff")) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete Menu Item")
                    .setMessage("Are you sure you want to delete " + menuItem.getName() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        menuViewModel.delete(menuItem);
                        Toast.makeText(getContext(), "Menu Item deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}