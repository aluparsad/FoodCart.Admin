package com.icmi.foodcartadmin.Application;


import com.google.firebase.auth.FirebaseAuth;
import com.icmi.foodcartadmin.Utils.Constants;


public class Presenter implements Contractor.Presenter{
    private FirebaseAuth mAuth;
    private Contractor.View mView;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void setUser() {
        if (mAuth.getCurrentUser() != null) {
            Constants.setUser(null);
        } else {
            mView.Login();
        }
    }

}
