package com.icmi.foodcartadmin.Home.Fragments.Orders;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.icmi.foodcartadmin.Model.Order;
import com.icmi.foodcartadmin.Model.Status;
import com.icmi.foodcartadmin.Utils.Database;

public class Presenter implements ContractorOrdersFragment.Presenter {
    private final ContractorOrdersFragment.View mView;
    private Status status;

    public Presenter(ContractorOrdersFragment.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestOrders(Status status) {
        this.status = status;
        new Database().getOrders(status, this);
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if (error != null) return;
        assert value != null;

        if (!value.isEmpty()) {
            for (DocumentChange dc : value.getDocumentChanges()) {
                check(dc.getDocument().getReference());
            }
        }
    }

    private void check(DocumentReference dr) {
        dr.get().addOnSuccessListener(documentSnapshot -> {
            Order o = documentSnapshot.toObject(Order.class);
            if (o.getStatus() == status) {
                mView.addData(o);
            }
        });

    }

}
