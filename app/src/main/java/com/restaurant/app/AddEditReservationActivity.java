package com.restaurant.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEditReservationActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.restaurant.app.EXTRA_ID";
    public static final String EXTRA_CUSTOMER_NAME = "com.restaurant.app.EXTRA_CUSTOMER_NAME";
    public static final String EXTRA_DATE = "com.restaurant.app.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.restaurant.app.EXTRA_TIME";
    public static final String EXTRA_NUMBER_OF_GUESTS = "com.restaurant.app.EXTRA_NUMBER_OF_GUESTS";
    public static final String EXTRA_STATUS = "com.restaurant.app.EXTRA_STATUS";

    private EditText editTextCustomerName;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextNumberOfGuests;
    private Spinner spinnerStatus;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_reservation);

        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);
        editTextNumberOfGuests = findViewById(R.id.editTextNumberOfGuests);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        Button buttonSaveReservation = findViewById(R.id.buttonSaveReservation);

        calendar = Calendar.getInstance();

        // Set up spinner for reservation status
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reservation_statuses, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        // Date picker
        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddEditReservationActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        updateDateLabel();
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // Time picker
        editTextTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddEditReservationActivity.this,
                    (view, hourOfDay, minute) -> {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        updateTimeLabel();
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true); // true for 24-hour format
            timePickerDialog.show();
        });

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Reservation");
            editTextCustomerName.setText(intent.getStringExtra(EXTRA_CUSTOMER_NAME));
            editTextDate.setText(intent.getStringExtra(EXTRA_DATE));
            editTextTime.setText(intent.getStringExtra(EXTRA_TIME));
            editTextNumberOfGuests.setText(String.valueOf(intent.getIntExtra(EXTRA_NUMBER_OF_GUESTS, 1)));
            String currentStatus = intent.getStringExtra(EXTRA_STATUS);
            if (currentStatus != null) {
                int spinnerPosition = adapter.getPosition(currentStatus);
                spinnerStatus.setSelection(spinnerPosition);
            }
        } else {
            setTitle("Add Reservation");
            updateDateLabel(); // Set current date as default
            updateTimeLabel(); // Set current time as default
            spinnerStatus.setSelection(adapter.getPosition("Pending")); // Default status
        }

        buttonSaveReservation.setOnClickListener(v -> saveReservation());
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy"; // In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editTextDate.setText(sdf.format(calendar.getTime()));
    }

    private void updateTimeLabel() {
        String myFormat = "HH:mm"; // In which you need put here (24-hour format)
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editTextTime.setText(sdf.format(calendar.getTime()));
    }

    private void saveReservation() {
        String customerName = editTextCustomerName.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();
        String numberOfGuestsStr = editTextNumberOfGuests.getText().toString().trim();
        String status = spinnerStatus.getSelectedItem().toString();

        if (customerName.isEmpty() || date.isEmpty() || time.isEmpty() || numberOfGuestsStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int numberOfGuests = Integer.parseInt(numberOfGuestsStr);

        Intent data = new Intent();
        data.putExtra(EXTRA_CUSTOMER_NAME, customerName);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_TIME, time);
        data.putExtra(EXTRA_NUMBER_OF_GUESTS, numberOfGuests);
        data.putExtra(EXTRA_STATUS, status);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(Activity.RESULT_OK, data);
        finish();
    }
}