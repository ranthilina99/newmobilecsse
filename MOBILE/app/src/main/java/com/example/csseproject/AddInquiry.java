package com.example.csseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AddInquiry extends AppCompatActivity {
    private String orderName,total,confirmation,delivery,status,supplierName,OrderId,supplierId;
    private TextView OName,tot,conf,deli,sta,suppName;
    private ImageView imageView;
    EditText editText,editText1;
    private FirebaseFirestore firestore;
    Button inquiry_button;
    String sEmail,sPassword,ToEmail,Subject;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inquiry);

        getSupportActionBar().setTitle("Add Inquiry");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        OrderId = getIntent().getStringExtra("OrderId");
        orderName = getIntent().getStringExtra("OrderName");
        total = getIntent().getStringExtra("total");
        confirmation = getIntent().getStringExtra("confirmed");
        delivery = getIntent().getStringExtra("delivery");
        status = getIntent().getStringExtra("status");
        supplierName = getIntent().getStringExtra("supplierName");
        supplierId = getIntent().getStringExtra("supplierId");

        OName=findViewById(R.id.Order_name_in);
        tot=findViewById(R.id.Order_Total_in);
        conf=findViewById(R.id.Order_Confirm_in);
        deli=findViewById(R.id.Order_delivery_in);
        imageView=findViewById(R.id.Order_status_in);
        suppName=findViewById(R.id.supplier_name_in);

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
        deli.setText(delivery);
        suppName.setText(supplierName);

        editText=findViewById(R.id.inquiry_message);
        editText1=findViewById(R.id.inquiry_TO);
        inquiry_button=findViewById(R.id.inquiry_button);

        firestore=FirebaseFirestore.getInstance();
        firestore.collection("suppliers").document(supplierId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snapshot) {
                        if(snapshot.exists()){
                            String email=snapshot.getString("supplierEmail");
                            editText1.setText(email);
                        }
                    }
                });

    }
    @SuppressLint("LongLogTag")
    public void SendMail(View view) {
        Log.i("Send email", "");

        String email = editText1.getText().toString();
        String[] TO = {email};
        //String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        // emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
        emailIntent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddInquiry.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}