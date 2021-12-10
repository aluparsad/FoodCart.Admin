package com.icmi.foodcartadmin.Home.Fragments.Orders;

import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.icmi.foodcartadmin.Model.Order;
import com.icmi.foodcartadmin.Model.Status;
import com.icmi.foodcartadmin.R;

import com.icmi.foodcartadmin.Utils.Database;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.icmi.foodcartadmin.Model.Status.*;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private List<Order> ordersData;

    public OrdersAdapter(List<Order> orders) {
        ordersData = new ArrayList<>();
        if (orders != null)
            ordersData.addAll(orders);
    }


    public void addItem(Order order) {
        if (!Search(order.getOrderId())) {
            ordersData.add(0, order);
            notifyItemInserted(0);
        }
    }

    private Boolean Search(String id) {
        for (Order o : ordersData) {
            return o.getOrderId().toLowerCase().equals(id.toLowerCase());
        }
        return false;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order o = ordersData.get(position);
        if (o != null)
            holder.setOrder(o);
    }

    @Override
    public int getItemCount() {
        return ordersData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton btnDone;
        private TextView orderId, name, quantity;
        private ImageView imageFood;
        private Order order;

        private void setOrder(@NonNull Order o) {
            order = o;
            orderId.setText(o.getOrderId());
            name.setText(o.getFood().getName());
            quantity.setText("QTY : " + o.getQuantity());
            Picasso.get().load(Uri.parse(o.getFood().getImage())).into(imageFood);
            setBtnListener();
        }

        public ViewHolder(@NonNull View view) {
            super(view);

            orderId = view.findViewById(R.id.orderIdTV);
            name = view.findViewById(R.id.foodnameTV);
            quantity = view.findViewById(R.id.quantityTV);
            imageFood = view.findViewById(R.id.imageOrder);
            btnDone = view.findViewById(R.id.btnOrderDone);

        }

        void setBtnListener() {
            if (Status.DELIVERED == order.getStatus()) {
                btnDone.setVisibility(View.GONE);
            } else {
                btnDone.setOnClickListener(v -> {
                    switch (order.getStatus()) {
                        case PROCESSING:
                            order.setStatus(ON_THE_WAY);
                            break;
                        case ON_THE_WAY:
                            order.setStatus(DELIVERED);
                            break;
                        default:
                            break;
                    }
                    onStateChanged();
                });
            }
        }


        private void onStateChanged() {
            new Database().updateStatus(order.getOrderId(), order.getStatus());
            int pos = getAdapterPosition();
            ordersData.remove(pos);
            notifyItemRemoved(pos);
        }

    }
}
