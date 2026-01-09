package com.restaurant.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class AddEditMenuItemActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "com.restaurant.restaurantmanagementstaff.EXTRA_ID";
    public static final String EXTRA_NAME = "com.restaurant.restaurantmanagementstaff.EXTRA_NAME";
    public static final String EXTRA_PRICE = "com.restaurant.restaurantmanagementstaff.EXTRA_PRICE";
    public static final String EXTRA_IMAGE_PATH = "com.restaurant.restaurantmanagementstaff.EXTRA_IMAGE_PATH";

    private EditText editTextName;
    private EditText editTextPrice;
    private ImageView imageViewItem;

    private String currentImagePath;

    // ActivityResultLauncher for selecting an image
    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    currentImagePath = uri.toString();
                    Glide.with(this).load(uri).into(imageViewItem);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_menu_item);

        editTextName = findViewById(R.id.editTextMenuItemName);
        editTextPrice = findViewById(R.id.editTextMenuItemPrice);
        imageViewItem = findViewById(R.id.imageViewMenuItem);
        Button buttonSave = findViewById(R.id.buttonSaveMenuItem);
        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Menu Item");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextPrice.setText(String.valueOf(intent.getDoubleExtra(EXTRA_PRICE, 0.0)));
            currentImagePath = intent.getStringExtra(EXTRA_IMAGE_PATH);
            if (currentImagePath != null && !currentImagePath.isEmpty()) {
                Glide.with(this).load(Uri.parse(currentImagePath)).into(imageViewItem);
            } else {
                imageViewItem.setImageResource(R.drawable.ic_menu);
            }
        } else {
            setTitle("Add Menu Item");
            imageViewItem.setImageResource(R.drawable.ic_menu);
        }

        buttonSelectImage.setOnClickListener(v -> mGetContent.launch("image/*"));

        buttonSave.setOnClickListener(v -> saveMenuItem());
    }

    private void saveMenuItem() {
        String name = editTextName.getText().toString();
        String priceStr = editTextPrice.getText().toString();

        if (name.trim().isEmpty() || priceStr.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name and price", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_PRICE, price);
        data.putExtra(EXTRA_IMAGE_PATH, currentImagePath);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(Activity.RESULT_OK, data);
        finish();
    }
}