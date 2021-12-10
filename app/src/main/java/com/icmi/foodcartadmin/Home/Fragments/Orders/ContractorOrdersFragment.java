package com.icmi.foodcartadmin.Home.Fragments.Orders;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.icmi.foodcartadmin.Model.Order;
import com.icmi.foodcartadmin.Model.Status;

public interface ContractorOrdersFragment {
    interface View {
        void addData(Order o);
    }

    interface Presenter extends EventListener<QuerySnapshot> {
        void requestOrders(Status status);
    }
}
