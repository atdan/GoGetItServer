package com.example.root.gogetitserver.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.root.gogetitserver.R;
import com.example.root.gogetitserver.common.Common;
import com.example.root.gogetitserver.interfaces.ItemClickListener;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
       // View.OnLongClickListener, disable this
        View.OnCreateContextMenuListener{

    public TextView textOrderId, textOrderStatus,textOrderPhone,textOrderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        textOrderAddress = itemView.findViewById(R.id.order_address);
        textOrderId = itemView.findViewById(R.id.order_id);
        textOrderPhone = itemView.findViewById(R.id.order_phone);
        textOrderStatus = itemView.findViewById(R.id.order_status);

        itemView.setOnClickListener(this);
        //itemView.setOnLongClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        contextMenu.setHeaderTitle("Select the action");

        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(),Common.DELETE);
    }

//    @Override
//    public boolean onLongClick(View view) {
//        itemClickListener.onClick(view,getAdapterPosition(),true);
//        return true;
//    }
}
