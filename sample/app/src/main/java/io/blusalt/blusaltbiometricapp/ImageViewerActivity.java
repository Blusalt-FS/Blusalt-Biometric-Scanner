package io.blusalt.blusaltbiometricapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

import io.blusalt.blusalt_biometric_sdk.Constants;

public class ImageViewerActivity extends AppCompatActivity {

    private static final String TAG = "ImageViewerActivity";;
    ImageView img1, img2, img3;
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        img1 = findViewById(R.id.image1);
        img2 = findViewById(R.id.image2);
        img3 = findViewById(R.id.image3);

        start = findViewById(R.id.buttonStart);
        start.setOnClickListener(view -> {
           startActivityForResult(new Intent(ImageViewerActivity.this, MainActivity.class),
                   Constants.__CAPTURE_BIOMETRIC_REQUEST_CODE__);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "completed", Toast.LENGTH_SHORT).show();
        if (requestCode == Constants.__CAPTURE_BIOMETRIC_REQUEST_CODE__ && resultCode == RESULT_OK){
            if (data!=null) {
                ArrayList<Bitmap> bitmaps = (ArrayList<Bitmap>) data.getExtras().getSerializable(Constants.CAPTURE_SEQ_DATA);
                Log.d(TAG, "onActivityResult: " + bitmaps.toString());
                if (bitmaps!=null && !bitmaps.isEmpty()) {
                    img1.setImageBitmap(bitmaps.get(0));
                    img2.setImageBitmap(bitmaps.get(1));
                    img3.setImageBitmap(bitmaps.get(2));
                }
            }
        }
    }
}
