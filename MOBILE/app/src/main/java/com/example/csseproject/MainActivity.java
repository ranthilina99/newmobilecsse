package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.csseproject.Model.Supplier;
import com.example.csseproject.ViewHolder.SupplierViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private CardView supplier1,orders1,items1,acceptOrders1,Reject,Cart;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        supplier1=findViewById(R.id.img_supplier);
        orders1=findViewById(R.id.img_Orders);
        items1=findViewById(R.id.img_Items);
        acceptOrders1=findViewById(R.id.img_AcceptOrders);
        Reject=findViewById(R.id.img_RejectOrders);
        Cart=findViewById(R.id.img_Cart);


        supplier1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }
        });
        orders1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewOrder.class));
            }
        });
        items1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewAllItems.class));
            }
        });
        acceptOrders1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AcceptOrders.class));

            }
        });
        Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RejectOrders.class));

            }
        });
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CartActivity.class));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout_nav){
            SharedPreferences preferences=getSharedPreferences("Checkbox",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("remember","false");
            editor.apply();

            Toast.makeText(MainActivity.this, "Thank You Come Again Log Out...", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }else if(id==R.id.setting_nav){
            startActivity(new Intent(getApplicationContext(), Changeprofile.class));
        }
        return super.onOptionsItemSelected(item);
    }
}