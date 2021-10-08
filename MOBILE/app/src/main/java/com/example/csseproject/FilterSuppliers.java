package com.example.csseproject;

import com.example.csseproject.Model.Order;
import com.example.csseproject.Model.Supplier;
import com.example.csseproject.ViewHolder.SupplierViewHolder;
import android.widget.Filter;

import java.util.ArrayList;

public class FilterSuppliers extends Filter {
    private SupplierViewHolder adapter;
    private ArrayList<Supplier> filterList;

    public FilterSuppliers(SupplierViewHolder adapter, ArrayList<Supplier> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint !=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Supplier> filterSuppliers =new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getSupplierName().toUpperCase().contains(constraint)||
                        filterList.get(i).getSupplierCompany().toUpperCase().contains(constraint)){
                    filterSuppliers.add(filterList.get(i));
                }
            }
            results.count=filterSuppliers.size();
            results.values=filterSuppliers;
        }else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.SupplierList= (ArrayList<Supplier>) results.values;
        adapter.notifyDataSetChanged();
    }
}
