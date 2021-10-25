package com.example.recipeappcake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.recipeappcake.Adapter.DataAdapter;
import com.example.recipeappcake.Model.Recipe;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Recipe>arrayList;
    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    DataAdapter adapter;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Item Loading");
        databaseReference = FirebaseDatabase.getInstance().getReference("RecipeBook");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot ds:datasnapshot.getChildren())
                {
                    Recipe recipe = ds.getValue(Recipe.class);
                    arrayList.add(recipe);
                }
                adapter = new DataAdapter(Home.this, arrayList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Error" , Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        toolbar = findViewById(R.id.topAppBar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.nav_home:
                        startActivity(new Intent(Home.this, Home.class)); break;
                    case R.id.nav_EditProfile:
                        Toast.makeText(Home.this, "Message is Clicked",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(Home.this, "Settings is Clicked",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Home.this, MainActivity.class));
                        break;
                    case R.id.nav_share:
                        Toast.makeText(Home.this, "Share is clicked",Toast.LENGTH_SHORT).show();break;
                    case R.id.nav_rate:
                        Toast.makeText(Home.this, "Rate us is Clicked",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;

                }
                return true;
            }
        });
    }

    public void Uploadclick(View view) {
        startActivity(new Intent(Home.this, Upload.class));
    }
}