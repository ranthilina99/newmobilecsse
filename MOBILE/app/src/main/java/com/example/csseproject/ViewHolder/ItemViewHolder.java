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
import com.example.csseproject.FilterViewItem;
import com.example.csseproject.ItemView;
import com.example.csseproject.Model.Item;
import com.example.csseproject.Model.Supplier;
import com.example.csseproject.R;
import com.example.csseproject.ViewItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemViewHolder extends RecyclerView.Adapter<ItemViewHolder.ItemHolder>  implements Filterable {

    private final Context context;
    public ArrayList<Item> ItemList,filterList;
    private FilterViewItem filter;

    public ItemViewHolder(Context context, ArrayList<Item> itemList) {
        this.context = context;
        ItemList = itemList;
        this.filterList=ItemList;
    }

    @Override
    public ItemViewHolder.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_card,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ItemViewHolder.ItemHolder holder, int position) {
        Item item=ItemList.get(position);
        String id=item.getId();
        String itemName=item.getItemName();
        String itemPrice=item.getItemPrice();
        String itemImage=item.getItemPic();
        String policy=item.getItemPolicyFlag();
        String sid=item.getItemSupplierId();

        holder.txtItemName.setText(itemName);
        holder.txtItemPrice.setText("Rs:"+itemPrice+".00");
        Picasso.get().load(itemImage).into(holder.imageItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewItem.class);
                intent.putExtra("itemId", item.getId());
                intent.putExtra("supplierId", item.getItemSupplierId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterViewItem(this,filterList);
        }
        return filter;
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView txtItemName,txtItemPrice;
        ImageView imageItem;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            txtItemName=itemView.findViewById(R.id.item_name);
            txtItemPrice=itemView.findViewById(R.id.item_price);
            imageItem=itemView.findViewById(R.id.item_image);

        }
    }
}
