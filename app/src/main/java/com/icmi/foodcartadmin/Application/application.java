package com.icmi.foodcartadmin.Application;

import android.app.Application;
import android.content.Intent;

import com.icmi.foodcartadmin.Auth.AuthActivity;


public class application extends Application implements Contractor.View {

    private Contractor.Presenter presenter;

    private void Init() {
        presenter = new Presenter(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Init();
        presenter.setUser();
    }

    @Override
    public void Login() {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
