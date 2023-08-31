package com.example.firebase.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.Activity.OTPActivity;
import com.example.firebase.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.concurrent.TimeUnit;

public class PhoneNumberLoginFragment extends Fragment {

    EditText et_phone_no;
    MaterialButton btn_send,btn_cancel;
    MKLoader fish_spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate( R.layout.fragment_phone_number_login, container, false );

        et_phone_no = view.findViewById(R.id.et_phone_no);
        btn_send = view.findViewById(R.id.btn_send);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        fish_spinner = view.findViewById(R.id.fish_spinner);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone_no = et_phone_no.getText().toString();

                if(!phone_no.trim().isEmpty()){

                    if(phone_no.trim().length() ==10){

                        fish_spinner.setVisibility(View.VISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + phone_no,
                                60,
                                TimeUnit.SECONDS,
                                getActivity(),
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                                        fish_spinner.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {

                                        fish_spinner.setVisibility(View.GONE);

                                        Toast.makeText( getContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT ).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                        fish_spinner.setVisibility(View.GONE);

                                        Intent intent = new Intent(getContext(), OTPActivity.class );
                                        intent.putExtra("phone",phone_no);
                                        intent.putExtra("backendotp",backendotp);
                                        Toast.makeText( getContext(), "Your Phone Number = " + phone_no, Toast.LENGTH_SHORT ).show();
                                        startActivity(intent);

                                    }
                                }
                        );

                    }else{

                        Toast.makeText(getContext(), "Please Enter Only 10 Digit Phone Number", Toast.LENGTH_SHORT ).show();
                    }
                }else{

                    Toast.makeText( getContext(), "Please Enter Your Phone Number", Toast.LENGTH_SHORT ).show();
                }
            }
        });

        btn_cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );

        return view;
    }
}