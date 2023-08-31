package com.example.firebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.MainActivity;
import com.example.firebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText et_name,et_email_id,et_password;
    MaterialButton btn_register;

    String name,email_id,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );

        et_name = findViewById(R.id.et_name);
        et_email_id = findViewById(R.id.et_email_id);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = et_name.getText().toString();
                email_id = et_email_id.getText().toString();
                password = et_password.getText().toString();

                if(TextUtils.isEmpty(name)){

                    Toast.makeText( RegistrationActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT ).show();

                }else if(TextUtils.isEmpty(email_id)){

                    Toast.makeText( RegistrationActivity.this, "Please Enter Email Id", Toast.LENGTH_SHORT ).show();

                }else if(!email_id.contains("@") || !email_id.contains(".com")){

                    Toast.makeText( RegistrationActivity.this, "Please Enter Valid Email Id", Toast.LENGTH_SHORT ).show();

                }else if(TextUtils.isEmpty(password)){

                    Toast.makeText( RegistrationActivity.this, "Please Enter Password", Toast.LENGTH_SHORT ).show();

                }else if(password.length()<8){

                    Toast.makeText( RegistrationActivity.this, "Please Enter 8 Or More Than Digit Password", Toast.LENGTH_SHORT ).show();

                }else{

                    registration();

                }
            }
        } );
    }

    private void registration() {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_id,password)
                .addOnSuccessListener( new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("et_name",name);
                        map.put("et_email_id",email_id);
                        map.put("et_password",password);

                       FirebaseDatabase.getInstance().getReference()
                               .child("Users")
                               .child(FirebaseAuth.getInstance().getUid())
                               .setValue(map)
                               .addOnCompleteListener( new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {

                                       Toast.makeText( RegistrationActivity.this, "Registration SuccessFully", Toast.LENGTH_SHORT ).show();

                                       Intent intent = new Intent(RegistrationActivity.this,DashboardActivity.class);
                                       startActivity(intent);
                                       finish();

                                   }
                               });
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText( RegistrationActivity.this, "Register Failed = "+e.getMessage(), Toast.LENGTH_SHORT ).show();

                    }
                });
    }
}