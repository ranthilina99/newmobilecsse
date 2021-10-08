package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseproject.Model.Order;
import com.example.csseproject.ViewHolder.OrderIViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ViewItem extends AppCompatActivity {

    private TextView name,price,suppName,suppCompany,suppSpec,policy,qty;
    private ImageView image;
    private String itemId,supplierId;
    private FirebaseFirestore firestore;
    private ImageView imageView;
    private LinearLayout mainLayout;
    private ImageView add,remove,cart;
    private Button addCart;

    int totQty = 1;
    int totalPrice = 0;
    int i;
    String flag;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        getSupportActionBar().setTitle("View Item");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        image=findViewById(R.id.item_view_Image);
        name=findViewById(R.id.item_view_name);
        price=findViewById(R.id.item_price_view);
        suppName=findViewById(R.id.Supplier_name_view);
        suppCompany=findViewById(R.id.Supplier_company_view);
        suppSpec=findViewById(R.id.Supplier_Speciality_view);
        policy=findViewById(R.id.Item_policy_view);
        imageView=findViewById(R.id.Image_policy);
        mainLayout=(LinearLayout)this.findViewById(R.id.ViewLayer3);
        addCart=findViewById(R.id.product_buy_button_user);
        cart=findViewById(R.id.shopping_cart);

        add = findViewById(R.id.addItem);
        remove = findViewById(R.id.removeItem);
        qty = findViewById(R.id.qty);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CartActivity.class));
            }
        });
        //Change qty
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totQty < 10){
                    totQty++;
                    qty.setText(String.valueOf(totQty));

                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totQty > 10){
                    totQty--;
                    qty.setText(String.valueOf(totQty));
                }

            }
        });

        //Add to cart
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });


        itemId = getIntent().getStringExtra("itemId");
        supplierId = getIntent().getStringExtra("supplierId");

        firestore=FirebaseFirestore.getInstance();
        name.setText(itemId);
        firestore.collection("items").document(itemId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String Iname=documentSnapshot.getString("itemName");
                            String Iflag=documentSnapshot.getString("itemPolicyFlag");
                            String Iprice=documentSnapshot.getString("itemPrice");
                            String Iimage = documentSnapshot.getString("itemPic");

                            flag = Iflag;

                            Picasso.get().load(Iimage).into(image);
                            name.setText(Iname);
                            price.setText(Iprice+"."+"00");
                            policy.setText(Iflag);
                            if(Iflag.equals("true")){
                                imageView.setImageResource(R.drawable.red);
                                policy.setText("Add Policy");
                                policy.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            }else if(Iflag.equals("false")){
                                imageView.setVisibility(View.INVISIBLE);
                                mainLayout.setVisibility(LinearLayout.GONE);
                            }
                        }
                    }
                });
        firestore.collection("suppliers").document(supplierId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){

                            String ISname=documentSnapshot.getString("supplierName");
                            String ISCompany=documentSnapshot.getString("supplierCompany");
                            String ISpeciality=documentSnapshot.getString("supplierSpeciality");

                            suppName.setText(ISname);
                            suppCompany.setText(ISCompany);
                            suppSpec.setText(ISpeciality);
                        }
                    }
                });
    }

    private void addingToCartList() {

//        String a = price.getText().toString();
//        String aaa = String.valueOf(price.getText().toString());

//        int price =Integer.parseInt(xxx);
////        totalPrice = Integer.parseInt(a) * totQty;
//
//        Toast.makeText(ViewItem.this,"Added to cart!" + price,Toast.LENGTH_SHORT).show();


        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForTime.getTime());

        totalPrice = 2500;

        String totQString=String.valueOf(totQty);
        String totPString=String.valueOf(totalPrice);

        //Put details in the Map
        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("cartItemID",itemId);
        cartMap.put("cartItemName",name.getText().toString());
        cartMap.put("cartItemPrice",price.getText().toString());
//        cartMap.put("cartItemImage",image);
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("cartItemQty",totQString);
        cartMap.put("cartItemTotal",totPString);
        cartMap.put("cartItemSupplierID",supplierId);
        cartMap.put("flag",flag);

        firestore.collection("AddToCart").add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(ViewItem.this,"Added to cart!",Toast.LENGTH_SHORT).show();
                    }
                });
    }

}