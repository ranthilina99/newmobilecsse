package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgotPassword extends AppCompatActivity {
    EditText email;
    Button btn,btn1;
    FirebaseAuth FAuth;
    boolean valid = true;
    FirebaseFirestore FStore;

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //set the title in the tool bar----------------------------------------------------------------------------------
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //---------------------------------------------------------------------------------------------------------------

        email = findViewById(R.id.txtemail);
        btn = findViewById(R.id.btnResetpassword);

        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        btn1=findViewById(R.id.btncancel);

        //click the cancel button----------------------------------------------------------------------------------------
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        //click the forgot password button------------------------------------------------------------------------------
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(email);
                final String userEmail = email.getText().toString().trim();
                if (userEmail.equals(" ")) {
                    Toast.makeText(ForgotPassword.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else {
                    FAuth.sendPasswordResetEmail(userEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            FAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(ForgotPassword.this, "Email sent Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        finish();
                                    } else {
                                        Toast.makeText(ForgotPassword.this, "Email Not sent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
        ;
    }

    //check the validation---------------------------------------------------------------------------------------------------------------
    public boolean checkField(EditText textFiled) {
        if (textFiled == email) {
            String val = email.getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (val.isEmpty()) {
                email.setError("Field cannot be empty");
                valid = false;
            } else if (!val.matches(emailPattern)) {
                email.setError("Invalid email address");
                valid = false;
            } else {
                email.setError(null);
                valid = true;
            }
            return valid;
        }
        return  valid;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------
}
