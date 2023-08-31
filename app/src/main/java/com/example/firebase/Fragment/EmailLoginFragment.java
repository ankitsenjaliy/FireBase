package com.example.firebase.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.Activity.DashboardActivity;
import com.example.firebase.Activity.FacebookAuthActivity;
import com.example.firebase.Activity.RegistrationActivity;
import com.example.firebase.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class EmailLoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 111;
    EditText et_email_id,et_password;
    MaterialButton btn_login,btn_cancel;
    SignInButton btn_google;
    MaterialButton btn_facebook;
    TextView tv_registration;

    String email_id,password;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    CallbackManager mCallbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_email_login, container, false);

        et_email_id = view.findViewById(R.id.et_email_id);
        et_password = view.findViewById(R.id.et_password);
        btn_login = view.findViewById(R.id.btn_login);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_google = view.findViewById(R.id.btn_google);

        btn_facebook = view.findViewById(R.id.btn_facebook);

        tv_registration = view.findViewById(R.id.tv_registration);

        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email_id = et_email_id.getText().toString();
                password = et_password.getText().toString();

                if(TextUtils.isEmpty(email_id)){

                    Toast.makeText( getContext(), "Please Enter Email Id", Toast.LENGTH_SHORT ).show();

                }else if(!email_id.contains("@") || !email_id.contains(".com")){

                    Toast.makeText( getContext(), "Please Enter Valid Email Id", Toast.LENGTH_SHORT ).show();

                }else if(TextUtils.isEmpty(password)){

                    Toast.makeText( getContext(), "Please Enter Password", Toast.LENGTH_SHORT ).show();

                }else if(password.length()<8){

                    Toast.makeText( getContext(), "Please Enter 8 Or More Than Digit Password", Toast.LENGTH_SHORT ).show();

                }else{

                    login();

                }
            }
        } );

        btn_cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        } );

        btn_google.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();
            }
        } );

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        btn_facebook.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), FacebookAuthActivity.class );
                startActivity(intent);

            }
        });

        tv_registration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void login() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email_id,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Toast.makeText(getContext(), "Login SuccessFully", Toast.LENGTH_SHORT ).show();

                        Intent intent = new Intent(getContext(), DashboardActivity.class);
                        startActivity(intent);

                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText( getContext(), "Login Failed = "+e.getMessage(), Toast.LENGTH_SHORT ).show();

                    }
                });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult( ApiException.class);
                Log.d("Ankit", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("Ankit", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity() , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("Ankit", "Google Sign In SuccessFully");

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText( getContext(), "Google Sign In SuccessFully", Toast.LENGTH_SHORT ).show();

                            Intent intent = new Intent(getContext(),DashboardActivity.class);
                            startActivity(intent);

                        } else {

                            Log.w("Ankit", "signInWithCredential:failure", task.getException());
                            Toast.makeText( getContext(), "Google Sign In Failed", Toast.LENGTH_SHORT ).show();

                        }
                    }
                });

    }
}