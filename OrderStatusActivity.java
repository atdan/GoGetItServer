package com.example.root.gogetitserver;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.root.gogetitserver.common.Common;
import com.example.root.gogetitserver.interfaces.ItemClickListener;
import com.example.root.gogetitserver.model.MyResponse;
import com.example.root.gogetitserver.model.Notifications;
import com.example.root.gogetitserver.model.Request;
import com.example.root.gogetitserver.model.Sender;
import com.example.root.gogetitserver.model.Token;
import com.example.root.gogetitserver.remote.APIService;
import com.example.root.gogetitserver.viewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderStatusActivity extends AppCompatActivity {

    private static final String TAG = "OrderStatusActivity";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    MaterialSpinner spinner;

    APIService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //init
        recyclerView = findViewById(R.id.list_orders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //init APIService
        mService = Common.getFCMClient();

        loadOrders();



    }

    private void loadOrders() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, int position) {

                viewHolder.textOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.textOrderAddress.setText(model.getAddress());
                viewHolder.textOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.textOrderPhone.setText(model.getPhone());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        if (!isLongClick){
                            Intent trackingOrder = new Intent(OrderStatusActivity.this, TrackingOrder.class);
                            Common.current_request = model;
                            startActivity(trackingOrder);
                        }/*

                        This line is commented because we used longClick
                        we can't get context menu to show
                        find another way ro view detail order

                            else {
                            Intent orderDetail = new Intent(OrderStatusActivity.this, OrderDetailActivity.class);
                            Common.current_request = model;
                            orderDetail.putExtra("OrderId",adapter.getRef(position).getKey());
                            startActivity(orderDetail);
                        }
                        */


                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE)){

            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if (item.getTitle().equals(Common.DELETE)){

            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {
        requests.child(key).removeValue();
    }

    private void showUpdateDialog(String key, final Request item) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatusActivity.this);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please choose status");

        LayoutInflater inflater = this.getLayoutInflater();

        final View view = inflater.inflate(R.layout.update_order_layout,null);
        spinner = findViewById(R.id.statusSpinner);

        spinner.setItems("Placed","On my Way", "Shipped");

        alertDialog.setView(view);

        final String localKey = key;
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey);

                //send notification when order has been confirmed
                sendOrderStatusToUser(localKey,  item);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    private void sendOrderStatusToUser(final String key ,final Request item) {
        DatabaseReference tokensRef = database.getReference("Tokens");
        tokensRef.orderByKey().equalTo(item.getPhone())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                            Token token = postSnapshot.getValue(Token.class);

                            //make raw [ayload
                            Notifications notifications = new Notifications("Your order "+ key+" was updated", "Atuma notify");

                            Sender content = new Sender(token.getToken(),notifications);

                            mService.sendNotification(content)
                                    .enqueue(new Callback<MyResponse>() {
                                        @Override
                                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                            if (response.body().success ==1){
                                                Toast.makeText(OrderStatusActivity.this, "Order Updated!", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(OrderStatusActivity.this, "Order was udated but failed to send notification", Toast.LENGTH_SHORT).show();
                                                
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<MyResponse> call, Throwable t) {

                                            Log.e(TAG, "onFailure: "+t.getMessage() );
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
