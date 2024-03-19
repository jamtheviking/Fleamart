package com.csis3175.fleamart.features;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SellPage extends AppCompatActivity {

    Button btnConfirm,btnCancel;
    EditText etItemName;
    EditText etItemPrice;
    EditText etItemDescription;
    EditText etItemLocation;
    Spinner etItemCategory;
    EditText etItemTags;

    ImageView ivUploadedImage;
    Uri result;
    byte[] imageBytes;

    private ActivityResultLauncher<String> getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        btnConfirm = findViewById(R.id.btConfirm);
        btnCancel = findViewById(R.id.btCancel);
        etItemName = findViewById(R.id.etItemName);
        etItemPrice = findViewById(R.id.etItemPrice);
        etItemDescription = findViewById(R.id.etItemDescription);
        etItemLocation = findViewById(R.id.etItemLocation);
        etItemCategory = findViewById(R.id.spinnerCategories);
        etItemTags = findViewById(R.id.etItemTag);
        ivUploadedImage = findViewById(R.id.imageUpload);


        /**
         * This Activity allows the user to get image from the Android Device Storage
         * and store it to the "imageBytes" and displays the selected image
         * to the "ivUploadImage".
         * "imageBytes" is also directly used in the db.insertItem method and stores it as BLOB
         */
        getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uriRes) {
                if (uriRes != null) {
                    // Process the selected image URI
                    try {
                        imageBytes = convertImageToByteArray(uriRes);
                        ivUploadedImage.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Input validation
                String itemName = etItemName.getText().toString();
                Double itemPrice = Double.parseDouble(etItemPrice.getText().toString());
                String itemDescription = etItemDescription.getText().toString();
                String itemLocation = etItemLocation.getText().toString();
                String itemCategory = etItemCategory.getSelectedItem().toString();
                String itemTags = etItemTags.getText().toString();

                DatabaseHelper dbHelper = new DatabaseHelper(SellPage.this);
                dbHelper.insertItem(itemName, itemPrice, itemDescription, itemLocation, itemCategory, itemTags, imageBytes);

                Toast.makeText(SellPage.this, "Item placed for selling", Toast.LENGTH_SHORT).show();
                //TODO: confirm button should move to a different user asking the user if they want to share or sell the item
                //TODO: Provide user confirmation that item was uploaded
                finish();
            }
        });



        /**
         * This method opens the image directory of the user's device to upload an image.
         */
        ivUploadedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getImage.launch("image/*");
            }
        });
    }

    /**
     * This method converts the uploaded image to byte to store it as a BLOB
     * @param uri
     * @return
     * @throws IOException
     */
    private byte[] convertImageToByteArray(Uri uri) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = getContentResolver().openInputStream(uri);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }
}