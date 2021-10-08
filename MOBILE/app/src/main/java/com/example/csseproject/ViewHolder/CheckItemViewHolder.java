//package com.example.csseproject.ViewHolder;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.csseproject.Model.OrderItem;
//import com.example.csseproject.R;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class CheckItemViewHolder extends RecyclerView.Adapter<CheckItemViewHolder.CheckItemHolder> {
//
//    private final Context context;
//    public ArrayList<OrderItem> CheckItemList;
//    private OnItemClickListener mListener;
//
//    public interface OnItemClickListener{
//        void onItemClick(Boolean checked);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener){
//        mListener=listener;
//    }
//
//    public CheckItemViewHolder(Context context, ArrayList<OrderItem> checkItemList) {
//        this.context = context;
//        CheckItemList = checkItemList;
//    }
//
//    @Override
//    public CheckItemViewHolder.CheckItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(context).inflate(R.layout.checkorder_item_card,parent,false);
//        return new CheckItemHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull  CheckItemViewHolder.CheckItemHolder holder, int position) {
//
//        OrderItem order = CheckItemList.get(position);
//        String id = order.getId();
//        String itemName = order.getItemName();
//        String itemPic = order.getItemPic();
//        String qty = order.getQty();
//        String subTotal = order.getSubTotal();
//        String itemId = order.getItemId();
//        String orderId = order.getOrderId();
//        String received = order.getReceived();
//
//        if (received.equals("yes")) {
//            holder.checkBox.setChecked(true);
//        } else if (received.equals("no")) {
//            holder.checkBox.setChecked(false);
//        }
//
//        holder.txtOrderChkItemName.setText(itemName);
//        holder.txtOrderChkItemQty.setText(qty);
//        holder.txtOrderChkItemSubtotal.setText("Rs:" + subTotal + ".00");
//        Picasso.get().load(itemPic).into(holder.imageOrderChkItem);
//
//
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mListener.onItemClick(isChecked);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return CheckItemList.size();
//    }
//    public static class CheckItemHolder extends RecyclerView.ViewHolder{
//        TextView txtOrderChkItemName,txtOrderChkItemQty,txtOrderChkItemSubtotal;
//        ImageView imageOrderChkItem;
//        public CheckBox checkBox;
//
//        public CheckItemHolder(@NonNull View itemView) {
//            super(itemView);
//
//            txtOrderChkItemName=itemView.findViewById(R.id.Order_Item_Name_check);
//            txtOrderChkItemQty=itemView.findViewById(R.id.Order_Item_qty_check);
//            txtOrderChkItemSubtotal=itemView.findViewById(R.id.Order_Item_subtotal_check);
//            imageOrderChkItem=itemView.findViewById(R.id.Order_Item_Image_check);
//            checkBox=itemView.findViewById(R.id.checkbox_item);
//
//
//        }
//    }
//}
//
