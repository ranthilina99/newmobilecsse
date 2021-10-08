package com.example.csseproject;

import android.widget.Filter;

import com.example.csseproject.Model.Item;
import com.example.csseproject.Model.Supplier;
import com.example.csseproject.ViewHolder.ItemViewHolder;
import com.example.csseproject.ViewHolder.SupplierViewHolder;

import java.util.ArrayList;

public class FilterViewItem extends Filter {
    private ItemViewHolder adapter;
    private ArrayList<Item> filterList;

    public FilterViewItem(ItemViewHolder adapter, ArrayList<Item> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint !=null && constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<Item> filterItems =new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getItemName().toUpperCase().contains(constraint)||
                        filterList.get(i).getItemPrice().toUpperCase().contains(constraint)){
                    filterItems.add(filterList.get(i));
                }
            }
            results.count=filterItems.size();
            results.values=filterItems;
        }else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.ItemList= (ArrayList<Item>) results.values;
        adapter.notifyDataSetChanged();
    }
}
