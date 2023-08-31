package com.example.firebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    EditText no_1,no_2,no_3,no_4,no_5,no_6;
    TextView tt_resend_otp;
    MaterialButton btn_verify;
    MKLoader whirlpool;

    String otp_backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_otp);

        no_1 = findViewById(R.id.no_1);
        no_2 = findViewById(R.id.no_2);
        no_3 = findViewById(R.id.no_3);
        no_4 = findViewById(R.id.no_4);
        no_5 = findViewById(R.id.no_5);
        no_6 = findViewById(R.id.no_6);

        tt_resend_otp = findViewById(R.id.tt_resend_otp);
        btn_verify = findViewById(R.id.btn_verify);
        whirlpool = findViewById(R.id.whirlpool);

        otp_backend = getIntent().getStringExtra("backendotp");

        btn_verify.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String no1 = no_1.getText().toString();
                String no2 = no_2.getText().toString();
                String no3 = no_3.getText().toString();
                String no4 = no_4.getText().toString();
                String no5 = no_5.getText().toString();
                String no6 = no_6.getText().toString();

                if(!no1.trim().isEmpty() && !no2.trim().isEmpty() && !no3.trim().isEmpty() &&! no4.trim().isEmpty() && !no5.trim().isEmpty() && !no6.trim().isEmpty()){

                    String enter_otp = no1 + no2 + no3 + no4 + no5 + no6;

                    if(otp_backend != null){

                        whirlpool.setVisibility(View.VISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                               otp_backend, enter_otp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        whirlpool.setVisibility(View.GONE);

                                        if(task.isSuccessful()){

                                            Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
                                            Toast.makeText( OTPActivity.this, "Login SuccessFully", Toast.LENGTH_SHORT ).show();
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                        } else {

                                            Toast.makeText( OTPActivity.this, "Enter The Correct OTP", Toast.LENGTH_SHORT ).show();
                                        }
                                    }
                                });

                    }else {

                        Toast.makeText( OTPActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT ).show();
                    }

//                    Toast.makeText( OTPActivity.this, "OTP Verifyed", Toast.LENGTH_SHORT ).show();

                }else{

                    Toast.makeText( OTPActivity.this, "Please Enter Correct OTP", Toast.LENGTH_SHORT ).show();

                }
            }
        } );

        tt_resend_otp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("phone"),
                        60,
                        TimeUnit.SECONDS,
                        OTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText( OTPActivity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT ).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                otp_backend = newbackendotp;

                                Toast.makeText( OTPActivity.this, "OTP Re-Send SuccessFully", Toast.LENGTH_SHORT ).show();
                            }
                        }
                );

            }
        } );

        numberotpmove();

    }

    private void numberotpmove() {

        no_1.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    no_2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        no_2.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    no_3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        no_3.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    no_4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        no_4.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    no_5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        no_5.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().isEmpty()){
                    no_6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
    }
}