package com.example.root.gogetitserver;

import android.app.PendingIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.root.gogetitserver.common.Common;
import com.example.root.gogetitserver.viewHolder.OrderDetailRecyclerAdapter;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderID,orderPhone, orderAddress, orderTotal, orderComment;
    String order_id_value = "";
    RecyclerView lstFoods;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        orderID = findViewById(R.id.order_id_order_detail_activity);
        orderAddress = findViewById(R.id.order_address_order_detail_activity);
        orderPhone = findViewById(R.id.order_phone_order_detail_activity);
        orderTotal = findViewById(R.id.order_total_order_detail_activity);
        orderComment = findViewById(R.id.order_comment_order_detail_activity);

        lstFoods = findViewById(R.id.lstFoods);
        lstFoods.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstFoods.setLayoutManager(layoutManager);

        if (getIntent() != null){
            order_id_value = getIntent().getStringExtra("OrderId");
        }

        //set value

        orderID.setText(order_id_value);
        orderPhone.setText(Common.current_request.getPhone());
        orderTotal.setText(Common.current_request.getTotal());
        orderAddress.setText(Common.current_request.getAddress());
        orderComment.setText(Common.current_request.getComment());

        OrderDetailRecyclerAdapter adapter = new OrderDetailRecyclerAdapter(Common.current_request.getFoods());

        adapter.notifyDataSetChanged();
        lstFoods.setAdapter(adapter);
    }
}
