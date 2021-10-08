package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseproject.Model.Cart;
import com.example.csseproject.Model.Order;
import com.example.csseproject.ViewHolder.CartViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.Policy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount,overTotalAmount;
    private FirebaseFirestore firestore;
    private ArrayList<Cart> cartArrayList;
    private ArrayList<Order> orderArrayList;
    private ArrayList<Policy> policyArrayList;
    private CartViewHolder cartViewHolder;
    private Intent intent;
    private ImageView RCI;

    int tot,subTotal,policy;
    int count = 0;
    String cartmainId,orderMainId,cartItemSupplierID,policyOnePrice;
    String Confirmation = "NotConfirmed";
    String deliveryStatus,flag;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);
        RCI = findViewById(R.id.removeCartItem);

        //Load items to Cart
        cartArrayList=new ArrayList<>();

        firestore=FirebaseFirestore.getInstance();

        firestore.collection("AddToCart").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:list){
                            Cart cart=ds.toObject(Cart.class);
                            cartArrayList.add(cart);

                            cartmainId = ds.getId();
                            cart.setId(cartmainId);
                            cartItemSupplierID = cart.getCartItemSupplierID();
                            flag = cart.getFlag();


                        }
                        cartViewHolder = new CartViewHolder(CartActivity.this, cartArrayList);
                        recyclerView.setAdapter(cartViewHolder);
                    }



                });

        //Get Total price
        overTotalAmount = findViewById(R.id.total_price);

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(broadcastReceiver,new IntentFilter("MyTotalAmount"));

        //Order
        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                createOrder();
            }
        });

        policyArrayList=new ArrayList<>();
    }


    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tot = intent.getIntExtra("totalAmount",0);
            overTotalAmount.setText("Total Price ="  + tot);

        }
    };

    private void createOrder() {

//        Toast.makeText(CartActivity.this,"Cart   " + cartmainId,Toast.LENGTH_SHORT).show();

        firestore.collection("policyOne").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:list){
                            String id =ds.getId();
                            policyOnePrice=ds.getString("policyOnePrice");

                        }

                    }
                });

        String sup = "8yYmJCCyAflISucMx4ld";
        String status = "";
        String oName = "Order";
        Confirmation = "NotConfirmed";
        deliveryStatus = "";
        String sn = "bbbbbb";
        subTotal = 5000;
        policy = 1000;

        if(subTotal >= 1000){
            status = "Pending";
            deliveryStatus = "NotDelivered";
        }
        else if(flag == "true"){
            status = "Pending";
            deliveryStatus = "NotDelivered";
        }
        else{
            status = "Accepted";
            deliveryStatus = "Delivered";
        }



        String totPString=String.valueOf(tot);

        //Put details in the Map
        final HashMap<String,Object> orderMap = new HashMap<>();

        orderMap.put("orderName",oName + count);
        orderMap.put("status",status);
        orderMap.put("supplierId",cartItemSupplierID);
        orderMap.put("Total",totPString);
        orderMap.put("confirmation",Confirmation);
        orderMap.put("deliveryStatus",deliveryStatus);
        orderMap.put("supplierName",sn);

        //add to DB
        firestore.collection("orders").add(orderMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(CartActivity.this,"Order Items!",Toast.LENGTH_SHORT).show();
                    }
                });

        //get Order ID
        firestore.collection("orders").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:list){
//                            Order order=ds.toObject(Order.class);
//                            orderArrayList.add(order);

                            orderMainId = ds.getId();
                            //Add cart items to Order
                            AddToOrderCart();
                        }
                    }
                });


    }

    private void AddToOrderCart() {

        //Put details in the Map
        final HashMap<String,Object> orderItemMap = new HashMap<>();

        String qty = "5";
//        String subTotall = 5000;
        String received = "no";
        String itemID = "jjkkklflf5f5r5";
        String totPString=String.valueOf(subTotal);

        orderItemMap.put("orderId",orderMainId);
        orderItemMap.put("qty",qty);
        orderItemMap.put("subTotal",totPString);
        orderItemMap.put("itemId",cartmainId);
        orderItemMap.put("received",received);

        firestore.collection("orderItems").add(orderItemMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(CartActivity.this,"Order Items!",Toast.LENGTH_SHORT).show();
                    }
                });
    }



}