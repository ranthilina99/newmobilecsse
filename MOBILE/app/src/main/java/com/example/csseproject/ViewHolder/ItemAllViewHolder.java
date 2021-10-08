package com.example.csseproject.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csseproject.FilterItems;
import com.example.csseproject.FilterSuppliers;
import com.example.csseproject.Model.Item;
import com.example.csseproject.R;
import com.example.csseproject.ViewItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAllViewHolder extends RecyclerView.Adapter<ItemAllViewHolder.ItemHolder>  implements Filterable {

    private final Context context;
    public ArrayList<Item> ItemAllList,filterList;
    private FilterItems filter;

    public ItemAllViewHolder(Context context, ArrayList<Item> itemAllList) {
        this.context = context;
        ItemAllList = itemAllList;
        this.filterList=ItemAllList;
    }

    @Override
    public ItemAllViewHolder.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ItemAllViewHolder.ItemHolder holder, int position) {
        Item item1=ItemAllList.get(position);
        String id=item1.getId();
        String itemName=item1.getItemName();
        String itemPrice=item1.getItemPrice();
        String itemImage=item1.getItemPic();
        String policy=item1.getItemPolicyFlag();
        String sid=item1.getItemSupplierId();

        holder.txtItemName_all.setText(itemName);
        holder.txtItemPrice_all.setText("Rs:"+itemPrice+".00");
        Picasso.get().load(itemImage).into(holder.imageItem_all);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("itemId", item1.getId());
                intent.putExtra("supplierId", item1.getItemSupplierId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemAllList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterItems(this,filterList);
        }
        return filter;
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView txtItemName_all,txtItemPrice_all;
        ImageView imageItem_all;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            txtItemName_all=itemView.findViewById(R.id.item_name);
            txtItemPrice_all=itemView.findViewById(R.id.item_price);
            imageItem_all=itemView.findViewById(R.id.item_image);

        }
    }
}
