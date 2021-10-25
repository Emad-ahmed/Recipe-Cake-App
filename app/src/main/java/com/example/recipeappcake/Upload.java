package com.example.recipeappcake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipeappcake.Model.Recipe;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Upload extends AppCompatActivity {
    Uri uri;
    EditText edName, edDesc, edPrice;
    String imageUrl;
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
        uploadimage();
    }

    public void uploadimage()
    {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Image Is Uploading");
        progressDialog.show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("RecipeBook").child(uri.getLastPathSegment());


        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isSuccessful());
                Uri uriimage = (Uri) task.getResult();
                imageUrl = uriimage.toString();
                uploadrecipe();
                Toast.makeText(Upload.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Upload.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();
    }

    public void uploadrecipe(){
        String name = edName.toString().trim();
        String description = edDesc.toString().trim();
        String price = edPrice.getText().toString().trim();
        String timeStamp = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Recipe recipe = new Recipe(name, description, price, imageUrl);

        FirebaseDatabase.getInstance().getReference("RecipeBook").child(timeStamp)
                .setValue(recipe).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Upload.this, "Sucess", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}