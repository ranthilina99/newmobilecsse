package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.csseproject.Model.Item;
import com.example.csseproject.Model.Order;
import com.example.csseproject.ViewHolder.ItemViewHolder;
import com.example.csseproject.ViewHolder.OrderIViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AcceptOrders extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<Order> OrderList;
    private OrderIViewHolder orderIViewHolder;
    private String supplierID;
    private TextView textView;
    private EditText search;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_orders);

        getSupportActionBar().setTitle("Accept Orders");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        search=findViewById(R.id.search_Accept_orders);
        recyclerView=findViewById(R.id.card_recycleView_Accept_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore=FirebaseFirestore.getInstance();
        textView=findViewById(R.id.search_order);

        OrderList=new ArrayList<>();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    orderIViewHolder.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        firestore.collection("orders").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot ds:task.getResult()){

                            String status=ds.getString("status");
                            if(status.equals("Accepted")){
                                String id =ds.getId();
                                String Total=ds.getString("Total");
                                String deliveryStatus=ds.getString("deliveryStatus");
                                String orderName=ds.getString("orderName");

                                String supplierName=ds.getString("supplierName");
                                String confirmation=ds.getString("confirmation");
                                String supplierId=ds.getString("supplierId");


                                Order order=new Order(id,Total,deliveryStatus,orderName,status,supplierName,confirmation,supplierId);
                                OrderList.add(order);
                            }
                        }
                        orderIViewHolder = new OrderIViewHolder(AcceptOrders.this, OrderList);
                        recyclerView.setAdapter(orderIViewHolder);
                    }
                });
    }
}