package com.csis3175.fleamart.features;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.*;
import com.csis3175.fleamart.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SellPage extends AppCompatActivity {

    Button btnConfirm,btnCancel,btnShare,btnSell,addDiscount;
    EditText etItemName,etItemPrice,etItemDescription,etItemLocation,etItemTags;
    Spinner etItemCategory;
    ImageView ivUploadedImage;
    byte[] imageBytes;
    private Transition slideRightTransition;
    private Scene scene3, scene2;
    String itemName,itemDescription,itemLocation,itemCategory,itemTags,currentDate;
    Double itemPrice;
    int userId;
    private ActivityResultLauncher<String> getImage;
    private User user;
    double dValue = 0.0;
    double discountMul = 0.0;
    double newPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            userId = user.getId();
        }
        //TODO need to implement discount feature. After user selects buy, user selects discount for item
        etItemName = findViewById(R.id.etItemName);
        etItemPrice = findViewById(R.id.etItemPrice);
        etItemDescription = findViewById(R.id.etItemDescription);
        etItemLocation = findViewById(R.id.etItemLocation);
        etItemCategory = findViewById(R.id.spinnerCategories);
        etItemTags = findViewById(R.id.etItemTagEdit);
        ivUploadedImage = findViewById(R.id.imageUploadEdit);

        DatabaseHelper dbHelper = new DatabaseHelper(SellPage.this);
        ViewGroup viewRoot = findViewById(android.R.id.content);


        scene2 = Scene.getSceneForLayout(viewRoot, R.layout.activity_sell2, this);
        scene3 = Scene.getSceneForLayout(viewRoot, R.layout.activity_sell3, this);
        slideRightTransition = new Slide(Gravity.START);
        slideRightTransition.setDuration(800);

        btnConfirm = findViewById(R.id.btConfirm);  //activity_sell
        btnCancel = findViewById(R.id.btCancel);    //activity_sell


        //Go to scene2
        btnConfirm.setOnClickListener(v -> {

            boolean isValid = true;

            String itemName = etItemName.getText().toString().trim();
            String itemPriceStr = etItemPrice.getText().toString().trim();
            String itemDescription = etItemDescription.getText().toString().trim();
            String itemLocation = etItemLocation.getText().toString().trim();
            String itemCategory = etItemCategory.getSelectedItem().toString().trim();
            String itemTags = etItemTags.getText().toString().trim();

            // Validation for itemName - Cannot be empty
            if (itemName.isEmpty()) {
                etItemName.setError("Item name is required!");
                isValid = false;
            }
            if (itemDescription.isEmpty()) {
                etItemDescription.setError("Item description is required!");
                isValid = false;
            }
            if (itemLocation.isEmpty()) {
                etItemLocation.setError("Item location is required!");
                isValid = false;
            }
            if (itemTags.isEmpty()) {
                etItemTags.setError("Item tags are required!");
                isValid = false;
            }


            // Validation for itemPrice - Must be a valid double and greater than 0
            double itemPrice = 0;
            try {
                itemPrice = Double.parseDouble(itemPriceStr);
                if (itemPrice <= 0) {
                    etItemPrice.setError("Price must be greater than 0!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etItemPrice.setError("Invalid price!");
                isValid = false;
            }


            // Check if all validations are passed
            if (isValid){
                TransitionManager.go(scene2, slideRightTransition);
                itemName = etItemName.getText().toString();
                itemPrice = Double.parseDouble(etItemPrice.getText().toString());
                itemDescription = etItemDescription.getText().toString();
                itemLocation = etItemLocation.getText().toString();
                itemCategory = etItemCategory.getSelectedItem().toString();
                itemTags = etItemTags.getText().toString();
            }


        });

        //finish
        btnCancel.setOnClickListener(v -> finish());
        transitionConfigFlow();

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

               //TODO: Input validation

                //TODO: confirm button should move to a different user asking the user if they want to share or sell the item
                //TODO: Provide user confirmation that item was uploaded



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

    public void transitionConfigFlow() {
        DatabaseHelper dbHelper = new DatabaseHelper(SellPage.this);
        ViewGroup viewRoot = findViewById(android.R.id.content);
        scene2 = Scene.getSceneForLayout(viewRoot, R.layout.activity_sell2, this);
        scene3 = Scene.getSceneForLayout(viewRoot, R.layout.activity_sell3, this);
        btnSell = findViewById(R.id.btnSell);  //activity_sell2
        btnShare = findViewById(R.id.btnShare);    //activity_sell2

        slideRightTransition = new Slide(Gravity.START);
        slideRightTransition.setDuration(800);

        scene2.setEnterAction(new Runnable() {
            @Override
            public void run() {
                btnSell = findViewById(R.id.btnSell);  //activity_sell2
                btnShare = findViewById(R.id.btnShare);    //activity_sell2
                //setup scene2
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                //go to scene3
                btnSell.setOnClickListener(v -> {
                    currentDate = dateFormat.format(new Date());

                    TransitionManager.go(scene3, slideRightTransition);
                });
                //finish()
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentDate = dateFormat.format(new Date());
                        //TODO default discount to 0
                        dbHelper.insertItem(itemName, null, itemDescription, itemLocation, itemCategory, itemTags, imageBytes, true,currentDate,user.getId(),0.0,"available");
                        finish();
                    }
                });

            }
        });
        scene3.setEnterAction(new Runnable() {

            @Override
            public void run() {
                LinearLayout linearLayout = findViewById(R.id.rootContainer);
                addDiscount = findViewById(R.id.btnAddDiscount);
                seekBarConfig(linearLayout,itemName,itemDescription, itemPrice,false,currentDate,imageBytes,user.getId(), itemLocation, itemCategory,addDiscount);
            }
        });


    }
    public void seekBarConfig(LinearLayout linearLayout,String itemName, String itemDescription, double itemPrice,
                              boolean isShareable,String date, byte[] imageData, int userID, String location, String category, Button b) {
//        https://tutorialwing.com/create-an-android-seekbar-programmatically-in-android/

        SeekBar seekBar = new SeekBar(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 30, 30);
        seekBar.setLayoutParams(layoutParams);
        DecimalFormat df = new DecimalFormat("#%");

        df.setMultiplier(1);
        TextView total = findViewById(R.id.total);
        TextView discount = findViewById(R.id.discount);
        total.setText(String.valueOf(itemPrice));

        Drawable customThumbDrawable = ResourcesCompat.getDrawable(getResources(),
                R.drawable.custom_thumb, null);
        seekBar.setThumb(customThumbDrawable);

        seekBar.setBackgroundColor(getResources().getColor(R.color.black));


        // Add SeekBar to LinearLayout
        if (linearLayout != null) {
            linearLayout.addView(seekBar);
        }




        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Write code to perform some action when progress is changed.
                discountMul = (double) progress;
                discount.setText(df.format(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is started.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Write code to perform some action when touch is stopped.
                DecimalFormat df = new DecimalFormat("#$");
                dValue = 1 - (discountMul/100);
                newPrice = itemPrice * dValue;
                total.setText(df.format(newPrice));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(SellPage.this);
                //Todo change DB to include discount and add to insert statement. We will be inserting entered price and discount.
                dbHelper.insertItem(itemName, itemPrice, itemDescription, itemLocation, itemCategory, itemTags, imageBytes, false,currentDate,user.getId(),dValue,"available");
                finish();
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