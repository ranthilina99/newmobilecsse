package com.example.csseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpecificOrderItem extends AppCompatActivity {

    private String orderName,total,confirmation,delivery,status,supplierName,OrderId,supplierId;
    private TextView OName,tot,conf,deli,sta,suppName;
    private Button btn1,btn2,btn3;
    private LinearLayout layout;
    private ImageView imageView;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_order_item);

        getSupportActionBar().setTitle("Specific Order Item");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        OrderId = getIntent().getStringExtra("OrderId");
        orderName = getIntent().getStringExtra("OrderName");
        total = getIntent().getStringExtra("total");
        confirmation = getIntent().getStringExtra("confirmed");
        delivery = getIntent().getStringExtra("delivery");
        status = getIntent().getStringExtra("status");
        supplierName = getIntent().getStringExtra("supplierName");
        supplierId = getIntent().getStringExtra("supplierId");

        OName=findViewById(R.id.Order_name_spec);
        tot=findViewById(R.id.Order_Total_spec);
        conf=findViewById(R.id.Order_Confirm_spec);
        deli=findViewById(R.id.Order_delivery_spec);
        imageView=findViewById(R.id.Order_status_spec);
        suppName=findViewById(R.id.supplier_name_spec);
        layout=findViewById(R.id.order_specific_item);
        btn1=findViewById(R.id.btn_view_order_items);
        btn2=findViewById(R.id.btn_inquiry);

        if(status.equals("Accepted")){
            imageView.setImageResource(R.drawable.green);
        }else if(status.equals("Declined")) {
            imageView.setImageResource(R.drawable.red);
        }else if(status.equals("Pending")) {
            imageView.setImageResource(R.drawable.orange);
        }
        if(delivery.equals("delivered") || delivery.equals("Delivered")){
            deli.setText("Delivered");
            deli.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        }else if(delivery.equals("not delivered")||delivery.equals("Not Delivered")) {
            deli.setText("Not Delivered");
            deli.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
        OName.setText(orderName);
        tot.setText(total);
        conf.setText(confirmation);
        suppName.setText(supplierName);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ViewOrderItem.class);
                intent.putExtra("OrderId", OrderId);
                intent.putExtra("supplierId", supplierId);
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CheckItemsOrder.class);
                intent.putExtra("OrderId", OrderId);
                intent.putExtra("OrderName", orderName);
                intent.putExtra("total", total);
                intent.putExtra("confirmed", confirmation);
                intent.putExtra("delivery", delivery);
                intent.putExtra("status", status);
                intent.putExtra("supplierName", supplierName);
                intent.putExtra("supplierId", supplierId);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddInquiry.class);
                intent.putExtra("OrderId", OrderId);
                intent.putExtra("OrderName", orderName);
                intent.putExtra("total", total);
                intent.putExtra("confirmed", confirmation);
                intent.putExtra("delivery", delivery);
                intent.putExtra("status", status);
                intent.putExtra("supplierName", supplierName);
                intent.putExtra("supplierId", supplierId);
                startActivity(intent);
            }
        });


    }

}