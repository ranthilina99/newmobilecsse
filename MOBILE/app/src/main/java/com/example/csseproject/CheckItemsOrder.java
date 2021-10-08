package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseproject.Model.OrderItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class CheckItemsOrder extends AppCompatActivity implements View.OnClickListener{
    private String orderName, total, confirmation, delivery, status, status1, OrderId, supplierId, supplierName, received, OrderItemId;
    private TextView OName, tot, conf, deli, sta, suppName;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<OrderItem> orderItemsList;
    //    private CheckItemViewHolder adapter;
    private RecyclerView.Adapter adapter1;
    private ArrayList<OrderItem> nameList1;
    private ImageView imageView;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_items_order);

        getSupportActionBar().setTitle("Check Item");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        supplierId = getIntent().getStringExtra("supplierId");
        OrderId = getIntent().getStringExtra("OrderId");
        orderName = getIntent().getStringExtra("OrderName");
        total = getIntent().getStringExtra("total");
        confirmation = getIntent().getStringExtra("confirmed");
        delivery = getIntent().getStringExtra("delivery");
        status = getIntent().getStringExtra("status");
        supplierName = getIntent().getStringExtra("supplierName");

        OName = findViewById(R.id.Order_name_chk);
        tot = findViewById(R.id.Order_Total_chk);
        conf = findViewById(R.id.Order_Confirm_chk);
        deli = findViewById(R.id.Order_delivery_chk);
        imageView = findViewById(R.id.Order_status_chk);
        suppName = findViewById(R.id.supplier_name_chk);
        recyclerView = findViewById(R.id.card_recycleView_check_items_chk);


        if (status.equals("Accepted")) {
            imageView.setImageResource(R.drawable.green);
        } else if (status.equals("Declined")) {
            imageView.setImageResource(R.drawable.red);
        } else if (status.equals("Pending")) {
            imageView.setImageResource(R.drawable.orange);
        }
        if (delivery.equals("delivered") || delivery.equals("Delivered")) {
            deli.setText("Delivered");
            deli.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else if (delivery.equals("not delivered") || delivery.equals("Not Delivered")) {
            deli.setText("Not Delivered");
            deli.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        orderItemsList = new ArrayList<>();
        nameList1 = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firestore = FirebaseFirestore.getInstance();

       OName.setText(orderName);
        tot.setText(total);
        conf.setText(confirmation);
        suppName.setText(supplierName);

        OrderItemsDetails();



        adapter1 = new RecyclerView.Adapter<CheckItemsOrder.CheckItemHolder>() {

            @NonNull
            @NotNull
            @Override
            public CheckItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkorder_item_card, parent, false);
                return new CheckItemHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull @NotNull CheckItemHolder holder, int position) {
                OrderItem order = orderItemsList.get(position);
                String id = order.getId();
                String itemName = order.getItemName();
                String itemPic = order.getItemPic();
                String qty = order.getQty();
                String subTotal = order.getSubTotal();
                String itemId = order.getItemId();
                String orderId = order.getOrderId();
                String received = order.getReceived();

                if (received.equals("yes")) {
                    holder.checkBox.setChecked(true);
                } else if (received.equals("no")) {
                    holder.checkBox.setChecked(false);
                }

                holder.txtOrderChkItemName.setText(itemName);
                holder.txtOrderChkItemQty.setText(qty);
                holder.txtOrderChkItemSubtotal.setText("Rs:" + subTotal + ".00");
                Picasso.get().load(itemPic).into(holder.imageOrderChkItem);

                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            status1 = "yes";
                        } else {
                            status1 = "no";
                        }
                        String id = orderItemsList.get(position).getId();
                        updateCheckItemDetails(id, status1);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return orderItemsList.size();
            }
        };
    }



    private class CheckItemHolder extends RecyclerView.ViewHolder {
        TextView txtOrderChkItemName, txtOrderChkItemQty, txtOrderChkItemSubtotal;
        ImageView imageOrderChkItem;
        public CheckBox checkBox;

        public CheckItemHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderChkItemName = itemView.findViewById(R.id.Order_Item_Name_check);
            txtOrderChkItemQty = itemView.findViewById(R.id.Order_Item_qty_check);
            txtOrderChkItemSubtotal = itemView.findViewById(R.id.Order_Item_subtotal_check);
            imageOrderChkItem = itemView.findViewById(R.id.Order_Item_Image_check);
            checkBox = itemView.findViewById(R.id.checkbox_item);


        }
    }

    private void updateCheckItemDetails(String id, String status1) {
        firestore.collection("orderItems").document(id).update("received", status1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CheckItemsOrder.this, "update successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckItemsOrder.this, "update Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void OrderItemsDetails() {
        firestore.collection("orderItems").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot ds : task.getResult()) {

                            String orderId = ds.getString("orderId");

                            if (OrderId.equals(orderId)) {
                                String OrderItemId = ds.getId();
                                String itemId = ds.getString("itemId");
                                String qty = ds.getString("qty");
                                String subTotal = ds.getString("subTotal");
                                String received = ds.getString("received");

                                LoadItemDetails(OrderItemId, orderId, itemId, qty, subTotal, received);
                                //updateCheckItemDetails(id);
                            }
                        }
                    }
                });
    }

    private void LoadItemDetails(String OrderItemId, String orderId, String itemId, String qty, String subTotal, String received) {
        firestore.collection("items").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot ds : task.getResult()) {
                            String item = ds.getId();
                            if (itemId.equals(item)) {
                                String id = ds.getId();
                                String itemName = ds.getString("itemName");
                                String itemPic = ds.getString("itemPic");

                                OrderItem item1 = new OrderItem(OrderItemId, orderId, itemId, qty, subTotal, itemName, itemPic, received);
                                orderItemsList.add(item1);
                            }
                            recyclerView.setAdapter(adapter1);
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_Order:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure?");
                builder.setMessage("This will confirm order permanently....");

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ConfirmOrderItems();
                    }
                });
                builder.setNegativeButton("Not Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotConfirmOrderItems();
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            case R.id.Delivery_Order:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Are you sure?");
                builder1.setMessage("This will Delivery order permanently....");

                builder1.setPositiveButton("Delivery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeliveryOrderItems();
                    }
                });
                builder1.setNegativeButton("Not Delivery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotDeliveryOrderItems();
                    }
                });
                AlertDialog ad1 = builder1.create();
                ad1.show();
                break;
             }

    }

    private void NotDeliveryOrderItems() {
        firestore.collection("orders").document(OrderId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String Confirm = documentSnapshot.getString("deliveryStatus");

                            String confirm = "Not Delivered";
                            Map<String, Object> Details = new HashMap<>();
                            Details.put("deliveryStatus", confirm);
                            firestore.collection("orders").document(OrderId).update(Details)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(CheckItemsOrder.this, "update successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CheckItemsOrder.this, "update Failed", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                });
    }

    private void DeliveryOrderItems() {
        firestore.collection("orders").document(OrderId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String Confirm = documentSnapshot.getString("deliveryStatus");

                            if (Confirm.equals("Not Delivered")) {
                                String Delivered = "Delivered";
                                Map<String, Object> Details = new HashMap<>();
                                Details.put("deliveryStatus", Delivered);
                                firestore.collection("orders").document(OrderId).update(Details)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(CheckItemsOrder.this, "update successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CheckItemsOrder.this, "update Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            } else if (Confirm.equals("Confirmed")) {
                                Toast.makeText(CheckItemsOrder.this, "This is Confirmed Order", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    private void ConfirmOrderItems() {
        firestore.collection("orders").document(OrderId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String Confirm = documentSnapshot.getString("confirmation");

                            if (Confirm.equals("NotConfirmed")) {
                                String confirm = "Confirmed";
                                Map<String, Object> Details = new HashMap<>();
                                Details.put("confirmation", confirm);
                                firestore.collection("orders").document(OrderId).update(Details)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(CheckItemsOrder.this, "update successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CheckItemsOrder.this, "update Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            } else if (Confirm.equals("Confirmed")) {
                                Toast.makeText(CheckItemsOrder.this, "This is Confirmed Order", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void NotConfirmOrderItems() {
        firestore.collection("orders").document(OrderId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String Confirm = documentSnapshot.getString("confirmation");

                                String confirm = "NotConfirmed";
                                Map<String, Object> Details = new HashMap<>();
                                Details.put("confirmation", confirm);
                                firestore.collection("orders").document(OrderId).update(Details)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(CheckItemsOrder.this, "update successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CheckItemsOrder.this, "update Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                        }
                    }
                });
    }
}
