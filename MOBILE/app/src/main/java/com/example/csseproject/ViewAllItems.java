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
import com.example.csseproject.ViewHolder.ItemAllViewHolder;
import com.example.csseproject.ViewHolder.ItemViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllItems extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<Item> itemAllList;
    private ItemAllViewHolder itemViewHolder;
    private TextView textView;
    private EditText search;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_items);

        getSupportActionBar().setTitle("All Items");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        search=findViewById(R.id.search_Items_all);
        recyclerView=findViewById(R.id.card_recycleView_items_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firestore=FirebaseFirestore.getInstance();
        textView=findViewById(R.id.search_item_all);

        itemAllList=new ArrayList<>();

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

        firestore.collection("items").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:list){
                            String id =ds.getId();
                            String itemName=ds.getString("itemName");
                            String itemPic=ds.getString("itemPic");
                            String itemPolicyFlag=ds.getString("itemPolicyFlag");
                            String itemPrice=ds.getString("itemPrice");
                            String itemSupplierId=ds.getString("itemSupplierId");

                            Item item=new Item(id,itemName,itemPic,itemPolicyFlag,itemPrice,itemSupplierId);
                            itemAllList.add(item);

                        }
                        itemViewHolder = new ItemAllViewHolder(ViewAllItems.this, itemAllList);
                        recyclerView.setAdapter(itemViewHolder);
                    }
                });
    }
}