package com.icmi.foodcartadmin.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.icmi.foodcartadmin.Model.User;


public class Constants {

    private static User user;

    private static FirebaseFirestore
            instance = FirebaseFirestore.getInstance();

    public static final String
            Product = "PRODUCT",
            Users = "USERS";

    public static void setUser(User u) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (u != null) {
                user = u;
            } else {
                user = new User();
                FirebaseUser usr = FirebaseAuth.getInstance().getCurrentUser();
                user.setUserId(usr.getUid());
                user.setName(usr.getDisplayName());
                user.setNumber(usr.getPhoneNumber());
            }
        }
    }

    public static CollectionReference getUsersRef() {
        return instance.collection(Constants.Users);
    }

    public static CollectionReference getProductsRef() {
        return instance.collection(Constants.Product);
    }
    public static CollectionReference getOrdersRef() {
        return instance.collection("ORDERS");
    }

}
