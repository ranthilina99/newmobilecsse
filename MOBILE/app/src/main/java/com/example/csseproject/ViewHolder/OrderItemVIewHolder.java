package com.example.csseproject.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csseproject.FilterOrderItems;
import com.example.csseproject.FilterOrders;
import com.example.csseproject.Model.OrderItem;
import com.example.csseproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderItemVIewHolder extends RecyclerView.Adapter<OrderItemVIewHolder.OrderItemHolder>  implements Filterable {

    private final Context context;
    public ArrayList<OrderItem> orderItemsList,filterList;
    private FilterOrderItems filter;

    public OrderItemVIewHolder(Context context, ArrayList<OrderItem> orderItemsList) {
        this.context = context;
        this.orderItemsList = orderItemsList;
        this.filterList=orderItemsList;
    }
    @Override
    public OrderItemVIewHolder.OrderItemHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_item_card,parent,false);
        return new OrderItemHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull  OrderItemVIewHolder.OrderItemHolder holder, int position) {
        OrderItem order=orderItemsList.get(position);
        String id=order.getId();
        String itemName=order.getItemName();
        String itemPic=order.getItemPic();
        String qty=order.getQty();
        String subTotal=order.getSubTotal();
        String itemId=order.getItemId();
        String orderId=order.getOrderId();
        String received=order.getReceived();

        holder.txtOrderItemName.setText(itemName);
        holder.txtOrderItemQty.setText(qty);
       Picasso.get().load(itemPic).into(holder.imageOrderItem);
        holder.txtOrderItemSubtotal.setText("Rs:"+subTotal+".00");
    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterOrderItems(this,filterList);
        }
        return filter;
    }

    class OrderItemHolder extends RecyclerView.ViewHolder{
        TextView txtOrderItemName,txtOrderItemQty,txtOrderItemSubtotal;
        ImageView imageOrderItem;

        public OrderItemHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderItemName=itemView.findViewById(R.id.Order_Item_name);
            txtOrderItemQty=itemView.findViewById(R.id.Order_Item_qty);
            txtOrderItemSubtotal=itemView.findViewById(R.id.Order_Item_subtotal);
            imageOrderItem=itemView.findViewById(R.id.Order_Item_Image);

        }
    }
}
