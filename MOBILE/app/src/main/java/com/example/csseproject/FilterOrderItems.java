package com.example.csseproject;

import android.widget.Filter;

import com.example.csseproject.Model.Order;
import com.example.csseproject.Model.OrderItem;
import com.example.csseproject.ViewHolder.OrderIViewHolder;
import com.example.csseproject.ViewHolder.OrderItemVIewHolder;

import java.util.ArrayList;

public class FilterOrderItems extends Filter {
    private OrderItemVIewHolder adapter;
    private ArrayList<OrderItem> filterList;

    public FilterOrderItems(OrderItemVIewHolder adapter, ArrayList<OrderItem> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint !=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<OrderItem> filterOrders =new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getItemName().toUpperCase().contains(constraint)||
                        filterList.get(i).getQty().toUpperCase().contains(constraint) ||
                        filterList.get(i).getSubTotal().toUpperCase().contains(constraint)){
                    filterOrders.add(filterList.get(i));
                }
            }
            results.count=filterOrders.size();
            results.values=filterOrders;
        }else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.orderItemsList= (ArrayList<OrderItem>) results.values;
        adapter.notifyDataSetChanged();
    }
}
