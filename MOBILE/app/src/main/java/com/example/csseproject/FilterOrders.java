package com.example.csseproject;

import android.widget.Filter;

import com.example.csseproject.Model.Order;
import com.example.csseproject.Model.Supplier;
import com.example.csseproject.ViewHolder.OrderIViewHolder;
import com.example.csseproject.ViewHolder.SupplierViewHolder;

import java.util.ArrayList;

public class FilterOrders extends Filter {
    private OrderIViewHolder adapter;
    private ArrayList<Order> filterList;

    public FilterOrders(OrderIViewHolder adapter, ArrayList<Order> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint !=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Order> filterOrders =new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getOrderName().toUpperCase().contains(constraint)||
                        filterList.get(i).getTotal().toUpperCase().contains(constraint) ||
                        filterList.get(i).getStatus().toUpperCase().contains(constraint) ||
                        filterList.get(i).getConfirmation().toUpperCase().contains(constraint)){
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
        adapter.OrderList= (ArrayList<Order>) results.values;
        adapter.notifyDataSetChanged();
    }
}

