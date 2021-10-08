package com.example.csseproject.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.csseproject.CartActivity;
import com.example.csseproject.Model.Cart;
import com.example.csseproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartViewHolder extends RecyclerView.Adapter<CartViewHolder.ViewHolder>{

    Context context;
    List<Cart> cartList;
    int totalPrice = 0 ;
    FirebaseFirestore firestore;

    public CartViewHolder(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.cartItemName.setText(cartList.get(position).getCartItemName());
//        holder.cartItemPrice.setText(cartList.get(position).getCartItemPrice());
        holder.cartItemQty.setText(cartList.get(position).getCartItemQty());
//        holder.cartItemTotal.setText(cartList.get(position).getCartItemTotal());
        holder.cartItemTotal.setText(String.valueOf(cartList.get(position).getCartItemPrice()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setMessage("Item will be removed from the Cart!");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        firestore.collection("AddToCart").document(cartList.get(position).getId()).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(context,"Order Deleted", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, CartActivity.class);
//
                                        }
                                    }

                                });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
                ;
            }
        });

        String r = cartList.get(position).getCartItemTotal();
        int e = Integer.parseInt(r);

        totalPrice = totalPrice + e;
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalPrice);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);





    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView cartItemID,cartItemName,cartItemPrice,cartItemQty,cartItemSupplierID,cartItemTotal;
        ImageView imageItem,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartItemName=itemView.findViewById(R.id.cart_product_name);
            cartItemTotal=itemView.findViewById(R.id.cart_product_price);
            cartItemQty=itemView.findViewById(R.id.cart_product_quantity);
            delete=itemView.findViewById(R.id.removeCartItem);


        }
    }

}
