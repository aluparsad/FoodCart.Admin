package com.icmi.foodcartadmin.Auth;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.icmi.foodcartadmin.Model.User;
import com.icmi.foodcartadmin.Utils.Authentication;
import com.icmi.foodcartadmin.Utils.Constants;


import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Presenter implements Contractor.Presenter {
    private final Contractor.View mView;
    private String mVerificationId;

    public Presenter(Contractor.View mView) {
        this.mView = mView;
    }


    private void loginWithCredentials(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance()
                .signInWithCredential(phoneAuthCredential)
                .addOnSuccessListener(authResult -> {
                    userExists(Objects.requireNonNull(authResult.getUser()));

                })
                .addOnFailureListener(mView::onError);
    }

    private void userExists(FirebaseUser user) {
        FirebaseFirestore.getInstance()
                .collection("USERS")
                .whereEqualTo("number", user.getPhoneNumber())
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        return;
                    }
                    assert value != null;
                    if (!value.isEmpty()) {
                        Constants.setUser(Objects.requireNonNull(value.getDocuments().get(0).toObject(User.class)));
                        mView.onSuccess();
                    } else {
                        Authentication.RegisterUser(user);
                    }
                });
    }


    @Override
    public void loginWithPhoneNumber(Activity activity, String number) {
        PhoneAuthOptions options = new PhoneAuthOptions.Builder(FirebaseAuth.getInstance())
                .setActivity(activity)
                .setPhoneNumber("+91" + number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callback)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    public void VerifyOTP(String otpCode) {
        if (!TextUtils.isEmpty(mVerificationId)) {
            PhoneAuthCredential cred = PhoneAuthProvider.getCredential(mVerificationId, otpCode);
            loginWithCredentials(cred);
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            mView.onOtpSent();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            loginWithCredentials(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            mView.onError(e);
        }
    };

}
