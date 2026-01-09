package com.restaurant.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TablesFragment extends Fragment implements TableAdapter.OnItemClickListener {

    private TableViewModel tableViewModel;
    private TableAdapter tableAdapter;
    private String userType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tables, container, false);

        // Get user type from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("app_prefs", Activity.MODE_PRIVATE);
        userType = prefs.getString("usertype", "guest");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_tables);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        tableAdapter = new TableAdapter(new ArrayList<>(), userType.equals("staff"));
        recyclerView.setAdapter(tableAdapter);
        tableAdapter.setOnItemClickListener(this);

        tableViewModel = new ViewModelProvider(this).get(TableViewModel.class);
        tableViewModel.getAllTables().observe(getViewLifecycleOwner(), tables -> {
            tableAdapter.setTables(tables);
        });

        FloatingActionButton fab = view.findViewById(R.id.fab_add_table);
        
        // Only staff can add tables
        if (userType.equals("staff")) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> showAddTableDialog());
        } else {
            fab.setVisibility(View.GONE);
        }

        return view;
    }

    private void showAddTableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_table, null);
        builder.setView(dialogView);

        android.widget.EditText etTableName = dialogView.findViewById(R.id.etTableName);
        android.widget.EditText etCapacity = dialogView.findViewById(R.id.etCapacity);

        builder.setTitle("Add New Table")
                .setPositiveButton("Add", (dialog, which) -> {
                    String tableName = etTableName.getText().toString().trim();
                    String capacityStr = etCapacity.getText().toString().trim();

                    if (tableName.isEmpty() || capacityStr.isEmpty()) {
                        Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int capacity = Integer.parseInt(capacityStr);
                    Table table = new Table(tableName, capacity, "Available");
                    tableViewModel.insert(table);
                    Toast.makeText(getContext(), "Table added", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onItemClick(Table table) {
        // Show table details
        new AlertDialog.Builder(getContext())
                .setTitle(table.getTableName())
                .setMessage("Capacity: " + table.getCapacity() + " people\n" +
                        "Status: " + table.getStatus())
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onStatusChange(Table table) {
        if (!userType.equals("staff")) {
            return;
        }

        String[] statuses = {"Available", "Occupied", "Reserved"};
        new AlertDialog.Builder(getContext())
                .setTitle("Change Table Status")
                .setItems(statuses, (dialog, which) -> {
                    table.setStatus(statuses[which]);
                    tableViewModel.update(table);
                    Toast.makeText(getContext(), "Status updated", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}
