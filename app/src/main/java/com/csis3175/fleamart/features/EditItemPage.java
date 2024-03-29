package com.csis3175.fleamart.features;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csis3175.fleamart.R;
import com.csis3175.fleamart.database.DatabaseHelper;
import com.csis3175.fleamart.model.Item;
import com.csis3175.fleamart.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditItemPage extends AppCompatActivity {

    Item item;
    int itemId;


    EditText etItemNameEdit,etItemPriceEdit,etItemDescriptionEdit,etItemLocationEdit,etItemTagEdit;
    ImageView imageUploadEdit;
    Spinner etItemCategory;
    byte[] imageBytes;

    private Transition slideRightTransition;

    Button btnCancelEdit, btnConfirmEdit, btnSell, btnShare, btnAddDiscount;

    private Scene scene3, scene2;
    String itemName,itemDescription,itemLocation,itemCategory,itemTags,currentDate;

    double itemPrice;
    int userId;
    private ActivityResultLauncher<String> getImage;
    double dValue = 0.0;
    double discountMul = 0.0;
    double newPrice = 0;
    SharedPreferences sharedPreferences;
    DatabaseHelper dbHelper = new DatabaseHelper(EditItemPage.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemNameEdit = findViewById(R.id.etItemNameEdit);
        etItemPriceEdit = findViewById(R.id.etItemPriceEdit);
        etItemDescriptionEdit = findViewById(R.id.etItemDescriptionEdit);
        etItemLocationEdit = findViewById(R.id.etItemLocationEdit);
        etItemCategory = findViewById(R.id.spinnerCategoriesEdit);
        etItemTagEdit = findViewById(R.id.etItemTagEdit);
        imageUploadEdit = findViewById(R.id.imageUploadEdit);


        Intent intent = getIntent(); //Received from Card Adapter
        if (intent != null) {
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            userId = sharedPreferences.getInt("userId",0);
            item = (Item) intent.getSerializableExtra("item");
        }

        itemPrice = item.getItemPrice();
        item.setItemID(item.getItemID());
        itemId = item.getItemID();
        etItemNameEdit.setText(item.getItemName());
        etItemPriceEdit.setText(String.valueOf(itemPrice));
        etItemDescriptionEdit.setText(item.getItemDescription());
        etItemLocationEdit.setText(item.getLocation());
        etItemTagEdit.setText(item.getTag());
        imageBytes = item.getImageData();
        Glide.with(this).load(item.getImageData()).into(imageUploadEdit);



        ViewGroup viewRoot = findViewById(android.R.id.content);

        btnCancelEdit = findViewById(R.id.btCancelEdit);
        btnConfirmEdit = findViewById(R.id.btConfirmEdit);

        btnCancelEdit.setOnClickListener(v -> finish());
        transitionConfigFlow();

        scene2 = Scene.getSceneForLayout(viewRoot, R.layout.activity_sell2, this);
        scene3 = Scene.getSceneForLayout(viewRoot, R.layout.activity_sell3, this);
        slideRightTransition = new Slide(Gravity.START);
        slideRightTransition.setDuration(800);

        btnConfirmEdit.setOnClickListener(v ->{

            String itemName = etItemNameEdit.getText().toString().trim();
            String itemPriceStr = etItemPriceEdit.getText().toString().trim();
            String itemDescription = etItemDescriptionEdit.getText().toString().trim();
            String itemLocation = etItemLocationEdit.getText().toString().trim();
            String itemCategory = etItemCategory.getSelectedItem().toString().trim();
            String itemTags = etItemTagEdit.getText().toString().trim();

            if (validateInputs(itemName, itemPriceStr, itemDescription, itemLocation, itemCategory, itemTags)) {
                TransitionManager.go(scene2, slideRightTransition);

                // Continue with your logic after validation is successful
                // For example, saving the edited item details
            }
        });

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
                        imageUploadEdit.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /**
         * This method opens the image directory of the user's device to upload an image.
         */
        imageUploadEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getImage.launch("image/*");
            }
        });

    }

    public void transitionConfigFlow() {

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
                        dValue=0;
                        //TODO default discount to 0
                        dbHelper.updateItem(itemId, itemName, itemPrice, itemDescription, itemLocation, itemCategory, itemTags, imageBytes, true,currentDate,userId,dValue,"available");
                        startActivity(new Intent(EditItemPage.this, HomePage.class));
                        finish();
                    }
                });

            }
        });
        scene3.setEnterAction(new Runnable() {

            @Override
            public void run() {
                LinearLayout linearLayout = findViewById(R.id.rootContainer);
                btnAddDiscount = findViewById(R.id.btnAddDiscount);
                seekBarConfig(linearLayout,itemName,itemDescription, itemPrice,false,currentDate,imageBytes,userId, itemLocation, itemCategory, btnAddDiscount);
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

                //Todo change DB to include discount and add to insert statement. We will be inserting entered price and discount.
                dbHelper.updateItem(itemId, itemName, itemPrice, itemDescription, itemLocation, itemCategory, itemTags, imageBytes, false,currentDate,userID,dValue,"available");
                startActivity(new Intent(EditItemPage.this, HomePage.class));
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

    private boolean validateInputs(String itemName, String itemPriceStr, String itemDescription,
                                   String itemLocation, String itemCategory, String itemTags) {
        boolean isValid = true;
        if (itemName.isEmpty()) {
            etItemNameEdit.setError("Item name is required!");
            isValid = false;
        }
        if (itemDescription.isEmpty()) {
            etItemDescriptionEdit.setError("Item description is required!");
            isValid = false;
        }
        if (itemLocation.isEmpty()) {
            etItemLocationEdit.setError("Item location is required!");
            isValid = false;
        }
        if (itemTags.isEmpty()) {
            etItemTagEdit.setError("Item tags are required!");
            isValid = false;
        }

        try {
            double itemPrice = Double.parseDouble(itemPriceStr);
            if (itemPrice <= 0) {
                etItemPriceEdit.setError("Price must be greater than 0!");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            etItemPriceEdit.setError("Invalid price format!");
            isValid = false;
        }

        // Add more validations as needed

        return isValid;
    }

}