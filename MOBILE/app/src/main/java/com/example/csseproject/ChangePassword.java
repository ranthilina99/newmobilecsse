package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    //Intialize objects------------------------------
    Button btn;
    EditText oldPassword, NewPassword, comPassword;
    FirebaseAuth FAuth;
    TextView show, s2, s3;
    FirebaseFirestore FStore;
    boolean valid = true;

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //Toolbar set the name---------------------------
        getSupportActionBar().setTitle("Change Password");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //ids-------------------------------------------
        btn = findViewById(R.id.btnchgpassword);

        oldPassword = findViewById(R.id.txtoldpassword);
        NewPassword = findViewById(R.id.txtnewpassowrd);
        comPassword = findViewById(R.id.txtnewpassowrd2);

        //Show and hide textViews----------------------
        show = findViewById(R.id.show1);
        s2 = findViewById(R.id.show2);
        s3 = findViewById(R.id.show3);

        //DataBase------------------------------------
        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();

        //Edit Text Data visible-------------------------------------------------------------
        oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        NewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        comPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //Text View show hiding----------------------------------------------------------
        s2.setVisibility(View.GONE);
        show.setVisibility(View.GONE);
        s3.setVisibility(View.GONE);


        //Hide and Show -------------------------------------------------------------------
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (oldPassword.getText().length() > 0) {
                    show.setVisibility(View.VISIBLE);
                } else {
                    show.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //Click the show and hie text view---------------------------------------------------------------------------------------
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show.getText() == "SHOW") {
                    show.setText("HIDE");
                    oldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    oldPassword.setSelection(oldPassword.length());
                } else {
                    show.setText("SHOW");
                    oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    oldPassword.setSelection(oldPassword.length());
                }
            }
        });

        //show and hide new password----------------------------------------------------------------------------------------------------
        NewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (NewPassword.getText().length() > 0) {
                    s2.setVisibility(View.VISIBLE);
                } else {
                    s2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //click the show and hide new password text view---------------------------------------------------------------------------------------
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s2.getText() == "SHOW") {
                    s2.setText("HIDE");
                    NewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    NewPassword.setSelection(NewPassword.length());
                } else {
                    s2.setText("SHOW");
                    NewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    NewPassword.setSelection(NewPassword.length());
                }
            }
        });
        //show and hide confirms password--------------------------------------------------------------------------------------------
        comPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (comPassword.getText().length() > 0) {
                    s3.setVisibility(View.VISIBLE);
                } else {
                    comPassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //click the show and hide confirms password-------------------------------------------------------------------------------------------
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s3.getText() == "SHOW") {
                    s3.setText("HIDE");
                    comPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    comPassword.setSelection(comPassword.length());
                } else {
                    s3.setText("SHOW");
                    comPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    comPassword.setSelection(comPassword.length());

                }
            }
        });

        //Update button click the event----------------------------------------------------------------------------------------------------------
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(oldPassword);
                checkField(NewPassword);
                checkField(comPassword);
                String oldPa = oldPassword.getText().toString();
                String newPa = NewPassword.getText().toString();
                if (TextUtils.isEmpty(oldPa)) {
                    Toast.makeText(ChangePassword.this, "Enter Your Current Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (NewPassword.length() < 6) {
                    Toast.makeText(ChangePassword.this, "Password length at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                updatePassword(oldPa, newPa);

            }
        });
    }

    //update password-----------------------------------------------------
    private void updatePassword(String oldPa, final String newPa) {
        final FirebaseUser user = FAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPa);
        user.reauthenticate(credential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        user.updatePassword(newPa).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DocumentReference dooRef = FStore.collection("Users").document(user.getUid());
                                Map<String, Object> edited = new HashMap<>();
                                edited.put("Password", newPa);
                                dooRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ChangePassword.this, "password Updated...", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Changeprofile.class));
                                        finish();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ChangePassword.this, "Not updated" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //validation-------------------------------------------------------------
    public boolean checkField(EditText textFiled) {
        if (textFiled == NewPassword) {
            String val = NewPassword.getText().toString();

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
                NewPassword.setError("Field cannot be empty");
                valid = false;
            } else if (!val.matches(passwordVal)) {
                NewPassword.setError("Password is too weak");
                valid = false;
            } else if (val.length() < 6) {
                NewPassword.setError("At least 6 characters");
                valid = false;
            } else {
                NewPassword.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == comPassword) {
            String val = comPassword.getText().toString();
            String val2 = NewPassword.getText().toString();

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
                comPassword.setError("Field cannot be empty");
                valid = false;
            } else if (!val.matches(passwordVal)) {
                comPassword.setError("Password is too weak");
                valid = false;
            } else if (val.length() < 6) {
                comPassword.setError("At least 6 characters");
                valid = false;
            } else if (!val2.equals(val)) {
                comPassword.setError("Does to match the password");
                valid = false;
            } else {
                comPassword.setError(null);
                valid = true;
            }
            return valid;
        } else if (textFiled == oldPassword) {
            String val = oldPassword.getText().toString();


             /*String passwordVal = "^" +
                     //"(?=.*[0-9])" +         //at least 1 digit
                     //"(?=.*[a-z])" +         //at least 1 lower case letter
                     //"(?=.*[A-Z])" +         //at least 1 upper case letter
                     "(?=.*[a-zA-Z])" +      //any letter
                     "(?=.*[@#$%^&+=])" +    //at least 1 special character
                     "(?=\\S+$)" +           //no white spaces
                     ".{4,}" +               //at least 4 characters
                     "$";*/

            if (val.isEmpty()) {
                oldPassword.setError("Field cannot be empty");
                valid = false;
            } else {
                oldPassword.setError(null);
                valid = true;
            }
            return valid;
        }

        return valid;
    }//end the validation----------------------------------------------------------------------------------------------------------------
}