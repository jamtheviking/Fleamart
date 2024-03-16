package com.csis3175.fleamart.features;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;

public class SellPage extends AppCompatActivity {

    Button btnPlaceForSelling;
    EditText etItemName;
    EditText etItemPrice;
    EditText etItemDescription;
    EditText etItemLocation;
    Spinner etItemCategory;
    EditText etItemTags;

    Button btnUpload;
    ImageView ivUploadedImage;

    private ActivityResultLauncher<String> getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        btnPlaceForSelling = findViewById(R.id.btnPlaceForSelling);
        etItemName = findViewById(R.id.etItemName);
        etItemPrice = findViewById(R.id.etItemPrice);
        etItemDescription = findViewById(R.id.etItemDescription);
        etItemLocation = findViewById(R.id.etItemLocation);
        etItemCategory = findViewById(R.id.spinnerCategories);
        etItemTags = findViewById(R.id.etItemTags);

        btnUpload = findViewById(R.id.btnUploadImage);
        ivUploadedImage = findViewById(R.id.ivItemImage);

        btnPlaceForSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemName = etItemName.getText().toString();
                String itemPrice = etItemPrice.getText().toString();
                String itemDescription = etItemDescription.getText().toString();
                String itemLocation = etItemLocation.getText().toString();
                String itemCategory = etItemCategory.getSelectedItem().toString();
                String itemTags = etItemTags.getText().toString();

                DatabaseHelper dbHelper = new DatabaseHelper(SellPage.this);
                dbHelper.insertItem(itemName, itemPrice, itemDescription, itemLocation, itemCategory, itemTags);

                Toast.makeText(SellPage.this, "Item placed for selling", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        /**
         * This Activity allows the user to get image from the Android Device Storage
         */
        getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    // Process the selected image URI
                    try {
                        Bitmap bitmap = null;
                        if (Build.VERSION.SDK_INT < 28) { //Checks if the build is below SDK 28 since the Bitmap is deprecated on SDK 29 (Android 10)
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                        } else {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), result);
                            bitmap = ImageDecoder.decodeBitmap(source);
                        }
                        ivUploadedImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage.launch("image/*");
            }
        });
    }
}