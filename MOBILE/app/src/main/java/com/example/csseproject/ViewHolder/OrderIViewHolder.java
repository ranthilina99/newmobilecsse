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


import com.example.csseproject.FilterOrders;
import com.example.csseproject.FilterSuppliers;
import com.example.csseproject.ItemView;
import com.example.csseproject.Model.Item;
import com.example.csseproject.Model.Order;
import com.example.csseproject.R;
import com.example.csseproject.SpecificOrderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderIViewHolder extends RecyclerView.Adapter<OrderIViewHolder.OrderHolder> implements Filterable {
    private final Context context;
    public ArrayList<Order> OrderList,filterList;
    private FilterOrders filter;

    public OrderIViewHolder(Context context, ArrayList<Order> orderList) {
        this.context = context;
        OrderList = orderList;
        this.filterList=OrderList;
    }

    @Override
    public OrderIViewHolder.OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_card,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderIViewHolder.OrderHolder holder, int position) {
        Order order=OrderList.get(position);
        String id=order.getId();
        String orderName=order.getOrderName();
        String deliveryStatus=order.getDeliveryStatus();
        String status=order.getStatus();
        String total=order.getTotal();
        String supplierName=order.getSupplierName();
        String confirmed=order.getConfirmation();
        String supplierId=order.getSupplierId();

        if(status.equals("Accepted")){
            holder.imageView_order.setImageResource(R.drawable.green);
        }else if(status.equals("Declined")) {
            holder.imageView_order.setImageResource(R.drawable.red);
        }else if(status.equals("Pending")) {
            holder.imageView_order.setImageResource(R.drawable.orange);
        }
        if(deliveryStatus.equals("delivered") || deliveryStatus.equals("Delivered")){
            holder.txtOrderDelivery.setText("Delivered");
            holder.txtOrderDelivery.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        }else if(deliveryStatus.equals("not delivered")||deliveryStatus.equals("Not Delivered")) {
            holder.txtOrderDelivery.setText("Not Delivered");
            holder.txtOrderDelivery.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
        }
        holder.txtOrderName.setText(orderName);
        holder.txtOrderTotal.setText("Rs:"+total+".00");
        holder.txtOrderSupplierName.setText(supplierName);
        holder.txtOrderConfirmed.setText(confirmed);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpecificOrderItem.class);
                intent.putExtra("OrderId", order.getId());
                intent.putExtra("OrderName", order.getOrderName());
                intent.putExtra("delivery", order.getDeliveryStatus());
                intent.putExtra("status", order.getStatus());
                intent.putExtra("total", order.getTotal());
                intent.putExtra("confirmed", order.getConfirmation());
                intent.putExtra("supplierName", order.getSupplierName());
                intent.putExtra("supplierId", order.getSupplierId());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterOrders(this,filterList);
        }
        return filter;
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        TextView txtOrderConfirmed,txtOrderName,txtOrderTotal,txtOrderDelivery,txtOrderStatus,txtOrderSupplierName;
        ImageView imageView_order;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            txtOrderName=itemView.findViewById(R.id.Order_name);
            txtOrderConfirmed=itemView.findViewById(R.id.Order_confirm);
            txtOrderTotal=itemView.findViewById(R.id.Order_total);
            txtOrderDelivery=itemView.findViewById(R.id.Order_delivery_status);
            imageView_order=itemView.findViewById(R.id.order_status);
            txtOrderSupplierName=itemView.findViewById(R.id.Order_supplier_name);


        }
    }
}
