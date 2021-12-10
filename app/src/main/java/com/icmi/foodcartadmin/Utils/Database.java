package com.icmi.foodcartadmin.Utils;

import android.net.Uri;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.icmi.foodcartadmin.Model.Food;
import com.icmi.foodcartadmin.Model.Status;

public class Database {

    public Database() {
    }

    public void GetNewOrders(EventListener<QuerySnapshot> listener) {
        Constants.getOrdersRef().addSnapshotListener(listener);
    }

    public void uploadFood(Food food, Listener.OnResultListener<Boolean> listener) {
        DocumentReference dr = Constants.getProductsRef().document();
        food.setItemId(dr.getId());

        dr.set(food)
                .addOnSuccessListener(aVoid -> {
                    listener.onResult(true);
                })
                .addOnFailureListener(e -> {
                    listener.onResult(false);
                });
        UploadImage_Food(food, dr);

    }

    private void UploadImage_Food(Food food, DocumentReference dr) {
        Uri uriImage = Uri.parse(food.getImage());

        FirebaseStorage.getInstance()
                .getReference(food.getCategory())
                .child(uriImage.getLastPathSegment())
                .putFile(uriImage)

                .addOnSuccessListener(taskSnapshot -> {
                    taskSnapshot
                            .getStorage()
                            .getDownloadUrl()

                            .addOnSuccessListener(uri -> {
                                dr.update("image", uri.toString());
                            }).addOnFailureListener(e -> {
                    });
                });
    }

    public void updateStatus(String orderId, Status status) {
        Constants.getOrdersRef().document(orderId).update("status", status);
    }

    public void getOrders(Status status, EventListener<QuerySnapshot> listener) {
        Constants.getOrdersRef()
                .whereEqualTo("status", status)
                .addSnapshotListener(listener);
    }
}
