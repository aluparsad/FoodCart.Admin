package com.icmi.foodcartadmin.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.icmi.foodcartadmin.Home.MainActivity;
import com.icmi.foodcartadmin.R;


public class AuthActivity extends AppCompatActivity implements Contractor.View {

    private Contractor.Presenter presenter;

    private final String TAG = "authactivity";
    private String otp;
    private EditText number_field, otp_field;
    private CheckBox checkBox;
    private Button sendOtp, verifyBtn;

    private View numberView, otpView;

    private void InitializeView() {
        presenter = new Presenter(this);
        number_field = findViewById(R.id.et_number_field);
        otp_field = findViewById(R.id.et_otp_field);
        checkBox = findViewById(R.id.checkBox);
        sendOtp = findViewById(R.id.btn_sendOtp);
        verifyBtn = findViewById(R.id.btn_verify_otp);

        numberView = findViewById(R.id.phone_num_view);
        otpView = findViewById(R.id.otp_view);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        InitializeView();
        buttonClickListeners();

    }


    private void buttonClickListeners() {
        sendOtp.setOnClickListener(v -> {
            final String num = number_field.getText().toString().trim();

            if (!TextUtils.isEmpty(num) && num.length() == 10 && checkBox.isChecked()) {
                presenter.loginWithPhoneNumber(this, num);
            } else {
                try {
                    Exception e = new Exception(new Throwable("conditions not matched"));
                    onError(e);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

        });

        verifyBtn.setOnClickListener(v -> {
            otp = otp_field.getText().toString().trim();
            if (!TextUtils.isEmpty(otp) && otp.length() == 6) {
                presenter.VerifyOTP(otp);
            }
        });
    }

    private void Login() {
        numberView.setVisibility(View.VISIBLE);
        otpView.setVisibility(View.GONE);
    }


    @Override
    public void onOtpSent() {
        numberView.setVisibility(View.GONE);
        otpView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "login Success", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onError(Exception e) {
        if (e.getMessage().equals("The sms code has expired. Please re-send the verification code to try again."))
            Toast.makeText(this, e.getMessage().substring(4, 26), Toast.LENGTH_SHORT).show();
        Login();
    }

}