package com.example.csseproject.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csseproject.FilterSuppliers;
import com.example.csseproject.ItemView;
import com.example.csseproject.Model.Supplier;
import com.example.csseproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SupplierViewHolder extends RecyclerView.Adapter<SupplierViewHolder.SupplierHolder> implements Filterable {


    private final Context context;
    public ArrayList<Supplier> SupplierList,filterList;
    private FilterSuppliers filter;

    public SupplierViewHolder(Context context, ArrayList<Supplier> supplierList) {
        this.context = context;
        SupplierList = supplierList;
        this.filterList=SupplierList;
    }

    @Override
    public SupplierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.supplier_card,parent,false);
        return new SupplierHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder.SupplierHolder holder, int position) {
        Supplier item=SupplierList.get(position);
        String id=item.getId();
        String supplierName=item.getSupplierName();
        String supplierCompany=item.getSupplierCompany();
        String supplierImage=item.getSupplierPic();
        String supplierSpeciality=item.getSupplierSpeciality();

        holder.txtSupplierName.setText(supplierName);
        holder.txtSupplierCompany.setText(supplierCompany);
        holder.txtSupplierSpeciality.setText(supplierSpeciality);
        Picasso.get().load(supplierImage).into(holder.imageSupplier);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemView.class);
                intent.putExtra("supplierId", item.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return SupplierList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new FilterSuppliers(this,filterList);
        }
        return filter;
    }

    class SupplierHolder extends RecyclerView.ViewHolder{
        TextView txtSupplierName,txtSupplierCompany,txtSupplierSpeciality;
        ImageView imageSupplier;

        public SupplierHolder(@NonNull View itemView) {
            super(itemView);

            txtSupplierName=itemView.findViewById(R.id.supplier_name);
            txtSupplierCompany=itemView.findViewById(R.id.supplier_company);
            txtSupplierSpeciality=itemView.findViewById(R.id.supplier_specialist);
            imageSupplier=itemView.findViewById(R.id.supplier_image);

        }
    }
}
