package com.example.root.gogetitserver.viewHolder;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.gogetitserver.R;
import com.example.root.gogetitserver.model.Order;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView name, quantity, price, discount;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.product_name_order_detail_activity);
        quantity = itemView.findViewById(R.id.product_quantity_order_detail_activity);
        price = itemView.findViewById(R.id.product_price_order_detail_activity);
        discount = itemView.findViewById(R.id.product_discount_order_detail_activity);
    }
}
public class OrderDetailRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Order> myOrders;

    public OrderDetailRecyclerAdapter(List<Order> foods) {

        this.myOrders = foods;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_detail_recycler_layout,viewGroup,false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        Order order = myOrders.get(i);

        viewHolder.name.setText(String.format("Name : %s",order.getProductName()));
        viewHolder.quantity.setText(String.format("Quantity : %s",order.getQuantity()));
        viewHolder.discount.setText(String.format("Discount : %s",order.getDiscount()));
        viewHolder.price.setText(String.format("Price : %s",order.getPrice()));


    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
}
