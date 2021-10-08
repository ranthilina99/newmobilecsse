package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseproject.Model.OrderItem;
import com.example.csseproject.ViewHolder.OrderIViewHolder;
import com.example.csseproject.ViewHolder.OrderItemVIewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewOrderItem extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<OrderItem> orderItemsList;
    private OrderItemVIewHolder orderItemsViewHolder;
    private String OrderId;
    private TextView SupplierName,Total,Status;
    private EditText search;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_item);

        getSupportActionBar().setTitle("View Order Items");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        OrderId = getIntent().getStringExtra("OrderId");
        search=findViewById(R.id.searchItems_all);
        SupplierName=findViewById(R.id.Order_item_supplier_name);
        Total=findViewById(R.id.Order_item_total);
        Status=findViewById(R.id.Order_item_status);
        recyclerView=findViewById(R.id.card_recycleView_Order_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firestore=FirebaseFirestore.getInstance();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    orderItemsViewHolder.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        orderItemsList=new ArrayList<>();

        LoadOrderDetail();
        OrderItemsDetails();

    }

    private void OrderItemsDetails() {
        firestore.collection("orderItems").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot ds:task.getResult()){
                            String id =ds.getId();
                            String orderId=ds.getString("orderId");

                            if(OrderId.equals(orderId)) {
                                String itemId = ds.getString("itemId");
                                String qty = ds.getString("qty");
                                String subTotal = ds.getString("subTotal");
                                String received = ds.getString("received");

                                LoadItemDetails(orderId,itemId,qty,subTotal,received);
                            }
                        }
                    }
                });
    }

    private void LoadItemDetails(String orderId, String itemId, String qty, String subTotal, String received) {
            firestore.collection("items").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot ds:task.getResult()){
                                String item =ds.getId();
                                if(itemId.equals(item)){
                                    String id =ds.getId();
                                    String itemName=ds.getString("itemName");
                                    String itemPic=ds.getString("itemPic");

                                    OrderItem item1=new OrderItem(id,orderId,itemId,qty,subTotal,itemName,itemPic,received);
                                    orderItemsList.add(item1);

                                }
                                orderItemsViewHolder = new OrderItemVIewHolder(ViewOrderItem.this, orderItemsList);
                                recyclerView.setAdapter(orderItemsViewHolder);
                            }
                        }
                    });

        }

    private void LoadOrderDetail() {
        firestore.collection("orders").document(OrderId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String supplierId=documentSnapshot.getString("supplierId");
                            String status=documentSnapshot.getString("status");
                            String total=documentSnapshot.getString("Total");

                            if(status.equals("Accepted") || status.equals("accepted")){
                                Status.setText("Accepted ");
                                Status.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            }else if(status.equals("Declined")||status.equals("declined")) {
                                Status.setText("Declined");
                                Status.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            }else if(status.equals("pending")||status.equals("Pending")) {
                                Status.setText("Pending");
                                Status.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
                            }
                                Total.setText("Rs:"+total+".00");

                            LoadSupplierDetails(supplierId);
                        }
                    }
                });
    }

    private void LoadSupplierDetails(String supplierId) {
        firestore.collection("suppliers").document(supplierId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String supplierName=documentSnapshot.getString("supplierName");
                            SupplierName.setText(supplierName);
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_Order:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure?");
                builder.setMessage("This will delete order permanently....");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteOrderItems();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
                ;
                break;
        }
    }

    private void DeleteOrderItems() {
        firestore.collection("orderItems").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot ds:task.getResult()){

                            String orderId=ds.getString("orderId");

                            if(OrderId.equals(orderId)) {
                                String id =ds.getId();
                                DeleteOrder(orderId);
                                DeleteOrderItems(id);
                            }
                        }
                    }
                });
    }

    private void DeleteOrder(String orderId) {
        firestore.collection("orders").document(orderId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ViewOrderItem.this, "Order Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewOrder.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(ViewOrderItem.this, "Delete Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void DeleteOrderItems(String id) {
        firestore.collection("orderItems").document(id).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ViewOrderItem.this, "Order items Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ViewOrder.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(ViewOrderItem.this, "Delete Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}