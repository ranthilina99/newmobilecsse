package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText Email, password;
    Button login;
    boolean valid;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView text1, text2, sh1;
    CheckBox remember;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Tool bar set the title---------------------------------------------------------------------------------------------------------------
        getSupportActionBar().setTitle("Login");
        //-IDS------------------------------------------------------------------------------------------------------------------------------------
        Email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        login = findViewById(R.id.btnlogin);
        text1 = findViewById(R.id.txtforgotpassword);
        text2 = findViewById(R.id.txtdonthaveacc);
        sh1 = findViewById(R.id.logins1);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        remember=findViewById(R.id.rememmber_me);

        //password visible and not visible-----------------------------------------------------------------------------------------------------
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        sh1.setVisibility(View.GONE);

        //remember me---------------------------------------------------------------------------------------------------------------------------
        sharedPreferences=getSharedPreferences("PRESS_NAME",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        saveLogin=sharedPreferences.getBoolean("SaveLogin",true);
        if (saveLogin = true) {
            Email.setText(sharedPreferences.getString("Email", null));
            password.setText(sharedPreferences.getString("password", null));
        }

        //------------------------------------------------------------------------------------------------------------------------------------

        //password visible and not visible-----------------------------------------------------------------------------------------------------
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
        //click the show text view in the password hide and show-------------------------------------------------------------------------
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

        //click the signUp button----------------------------------------------------------------------------------------------------------
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });
        //-----------------------------------------------------------------------------------------------------------------------------------

        //Click the login button------------------------------------------------------------------------------------------------------------
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkField(Email);
                checkField(password);

                if (valid) {
                    fAuth.signInWithEmailAndPassword(Email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this, "Logging Successfully", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                            //check the remember me checked------------------------------------------------------------------------------------------
                            if(remember.isChecked()){
                                editor.putBoolean("SaveLogin",true);
                                editor.putString("Email",Email.getText().toString());
                                editor.putString("password",password.getText().toString());

                                editor.commit();
                                Toast.makeText(Login.this, "Save Email and password", Toast.LENGTH_SHORT).show();
                            }else {
                                //Toast.makeText(Login.this, "Remember Not Save Email and password", Toast.LENGTH_SHORT).show();
                            }
                            //---------------------------------------------------------------------------------------------------------------------
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, "Logging Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });
        //------------------------------------------------------------------------------------------------------------------------------------------


    }//end the main-------------------------------------------------------------------------------------------------------------------------------

    //Define the user roles user and admin-------------------------------------------------------------------------------------------------------
    private void checkUserAccessLevel(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);
        //extract data from the users
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
                //identity user access level
                if (documentSnapshot.getString("isAdmin") != null) {
                    //user admin
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                if (documentSnapshot.getString("isUser") != null) {
                    //user users
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(i);
                    finish();
                }
            }
        });
    }//end user role-----

    //check the validation------------------------------------------------------------------------------------------------------------------------
    public boolean checkField(EditText textFiled) {
        if (textFiled == Email) {
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
        }
        else if(textFiled==password){
            String  val=password.getText().toString();
            if (val.isEmpty()) {
                password.setError("Field cannot be empty");
                valid= false;
            } else if (val.length()<6) {
                password.setError("At least 6 characters");
                valid= false;
            }else {
                password.setError(null);
                valid= true;
            }
            return  valid;
        }
        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }
    }//end validation-------------------------------------------------------------------------------
}// end the login activity---------------------------------------------------------------------------------------------------------------------------