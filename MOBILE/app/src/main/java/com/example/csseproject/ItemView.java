package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.csseproject.Model.Item;
import com.example.csseproject.Model.Supplier;
import com.example.csseproject.ViewHolder.ItemViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ItemView extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<Item> itemList;
    private ItemViewHolder itemViewHolder;
    private String supplierID;
    private TextView textView;
    private EditText search;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        getSupportActionBar().setTitle("View Items");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        search=findViewById(R.id.searchItems);
        supplierID = getIntent().getStringExtra("supplierId");
        recyclerView=findViewById(R.id.card_recycleView_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore=FirebaseFirestore.getInstance();
        textView=findViewById(R.id.search_item);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    itemViewHolder.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        itemList=new ArrayList<>();
//        firestore.collection("items").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for(QueryDocumentSnapshot ds:task.getResult()){
//                            String Category =""+ds.getString("itemSupplierId");
//                            if(supplierID.equals(Category)){
//                                Item item=ds.toObject(Item.class);
//                                itemList.add(item);
//                            }
//                        }
//                        itemViewHolder = new ItemViewHolder(ItemView.this, itemList);
//                        recyclerView.setAdapter(itemViewHolder);
//                    }
//                });

        firestore.collection("items").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot ds:task.getResult()){
                            String Category =""+ds.getString("itemSupplierId");
                            if(supplierID.equals(Category)){
                                String id =ds.getId();
                                String itemName=ds.getString("itemName");
                                String itemPic=ds.getString("itemPic");
                                String itemPolicyFlag=ds.getString("itemPolicyFlag");
                                String itemPrice=ds.getString("itemPrice");
                                String itemSupplierId=ds.getString("itemSupplierId");

                                Item item=new Item(id,itemName,itemPic,itemPolicyFlag,itemPrice,itemSupplierId);
                                itemList.add(item);
                            }
                        }
                        itemViewHolder = new ItemViewHolder(ItemView.this, itemList);
                        recyclerView.setAdapter(itemViewHolder);
                    }
                });

    }
}