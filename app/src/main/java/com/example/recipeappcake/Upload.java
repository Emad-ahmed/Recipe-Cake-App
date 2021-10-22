package com.example.recipeappcake;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Upload extends AppCompatActivity {
    Uri uri;
    EditText edName, edDesc, edPrice;

    ImageView  imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        imageview = (ImageView) findViewById(R.id.imageview);
        edName = (EditText) findViewById(R.id.tvname);
        edDesc = (EditText) findViewById(R.id.tvDesc);
        edPrice = (EditText) findViewById(R.id.tvPrice);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        } else
        {
            Toast.makeText(this, "Permission Allow", Toast.LENGTH_SHORT).show();
        }
    }

    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            uri = data.getData();
            imageview.setImageURI(uri);

        }
        else
        {
            Toast.makeText(this, "You Have Not Picked Image", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadRecipe(View view) {
    }
}