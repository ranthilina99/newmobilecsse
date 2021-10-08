package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText firstName, lastName, password, retypePassword, Email, mobile;
    Button createAccount;
    boolean valid = true;
    TextView text, sh1, sh2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("Register");
        //DatabaseTools-----------------------------------

        fAuth = FirebaseAuth.getInstance();
        fStory = FirebaseFirestore.getInstance();
        //Create id---------------------------------------

        firstName = findViewById(R.id.registerfirstname);
        lastName = findViewById(R.id.registerlastname);
        Email = findViewById(R.id.registeremail);
        mobile = findViewById(R.id.registermobile);
        password = findViewById(R.id.registerpassword);
        retypePassword = findViewById(R.id.registerretypepassowrd);
        sh1 = findViewById(R.id.logins1);
        sh2 = findViewById(R.id.s2);

        //button create ids----------------------------------------
        createAccount = findViewById(R.id.btnregcreateaccount);
        text = findViewById(R.id.txtsigiin);

        //Edit Text Data visible-------------------------------------------------------------
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        retypePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //Text View show hiding----------------------------------------------------------
        sh1.setVisibility(View.GONE);
        sh2.setVisibility(View.GONE);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().length() > 0) {
                    sh1.setVisibility(View.VISIBLE);
                } else {
                    sh1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sh1.getText() == "SHOW") {
                    sh1.setText("HIDE");
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password.setSelection(password.length());
                } else {
                    sh1.setText("SHOW");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                }
            }
        });
        retypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (retypePassword.getText().length() > 0) {
                    sh2.setVisibility(View.VISIBLE);
                } else {
                    sh2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sh2.getText() == "SHOW") {
                    sh2.setText("HIDE");
                    retypePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    retypePassword.setSelection(retypePassword.length());
                } else {
                    sh2.setText("SHOW");
                    retypePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    retypePassword.setSelection(retypePassword.length());
                }
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validation check-----------------------------------------------------------------------------------------------------------------------
                checkField(firstName);
                checkField(lastName);
                checkField(Email);
                checkField(mobile);
                checkField(password);
                checkField(retypePassword);
                //database input--------------------------------------------------------------------------------------------------------------------------
                if (valid) {
                    fAuth.createUserWithEmailAndPassword(Email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Registration.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStory.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FirstName", firstName.getText().toString());
                            userInfo.put("LastName", lastName.getText().toString());
                            userInfo.put("Email", Email.getText().toString());
                            userInfo.put("Mobile", mobile.getText().toString());
                            userInfo.put("Password", password.getText().toString());
                            //specify if the user is admin//

                            userInfo.put("isUser", "1");
                            df.set(userInfo);
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, "Failed to Create Account", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }

    //-----------------------------------------------validation-------------------------------------------------------------------------
    public boolean checkField(EditText textFiled) {

        if (textFiled == firstName) {
            String val = firstName.getText().toString();
            if (val.isEmpty()) {
                firstName.setError("Field cannot be empty");
                valid = false;
            } else {
                firstName.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == lastName) {
            String val = lastName.getText().toString();
            if (val.isEmpty()) {
                lastName.setError("Field cannot be empty");
                valid = false;
            } else {
                lastName.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == Email) {
            String val = Email.getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (val.isEmpty()) {
                Email.setError("Field cannot be empty");
                valid = false;
            } else if (!val.matches(emailPattern)) {
                Email.setError("Invalid email address");
                valid = false;
            } else {
                Email.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == mobile) {
            String val = mobile.getText().toString();
            String MobilePattern = "[0-9]{10}";

            if (val.isEmpty()) {
                mobile.setError("Field cannot be empty");
                valid = false;
            } else if (!val.matches(MobilePattern)) {
                mobile.setError("Invalid 10 digit mobile no ");
                valid = false;
            } else if (val.length()<10) {
                mobile.setError("Invalid 10 digit no");
                valid = false;
            } else {
                mobile.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == password) {
            String val = password.getText().toString();

            String passwordVal = "^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$";

            if (val.isEmpty()) {
                password.setError("Field cannot be empty");
                valid = false;
            } else if (!val.matches(passwordVal)) {
                password.setError("Password is too weak");
                valid = false;
            } else if (val.length() < 6) {
                password.setError("At least 6 characters");
                valid = false;
            } else {
                password.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == retypePassword) {
            String val = retypePassword.getText().toString();
            String val2 = password.getText().toString();

            String passwordVal = "^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$";

            if (val.isEmpty()) {
                retypePassword.setError("Field cannot be empty");
                valid = false;
            } else if (!val.matches(passwordVal)) {
                retypePassword.setError("Password is too weak");
                valid = false;
            } else if (val.charAt(0)=='0') {
                retypePassword.setError("At first 0 characters");
                valid = false;
            } else if (!val2.equals(val)) {
                retypePassword.setError("Does to match the password");
                valid = false;
            } else {
                retypePassword.setError(null);
                valid = true;
            }
            return valid;
        }


        return valid;
    }



}