package com.icmi.foodcartadmin.Utils;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.icmi.foodcartadmin.Model.User;


public class Authentication {

    public static void RegisterUser(@NonNull FirebaseUser user) {
        DocumentReference dr = Constants.getUsersRef().document();

        User u = new User();
        u.setUserId(user.getUid());
        u.setNumber(user.getPhoneNumber());

        dr.set(u)
                .addOnSuccessListener(aVoid -> {
                    Constants.setUser(u);
                });
    }

}
