package com.icmi.foodcartadmin.Home.Fragments.Orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.icmi.foodcartadmin.Model.Order;
import com.icmi.foodcartadmin.Model.Status;
import com.icmi.foodcartadmin.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment implements ContractorOrdersFragment.View {
    private ContractorOrdersFragment.Presenter presenter;
    private final Status textTitle;
    private TextView title;
    private RecyclerView rv;
    private final OrdersAdapter adapter;

    private List<String> ids;

    public OrdersFragment(Status title) {
        textTitle = title;
        adapter = new OrdersAdapter(null);
        ids = new ArrayList<>();

        presenter = new Presenter(this);
    }


    private void Init(View v) {
        title = v.findViewById(R.id.orders_titleTV);
        rv = v.findViewById(R.id.rv_orders);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
        title.setText(textTitle.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Init(v);
        presenter.requestOrders(textTitle);
    }


    @Override
    public void addData(Order o) {
        adapter.addItem(o);
    }

}