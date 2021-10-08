package com.example.csseproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Changeprofile extends AppCompatActivity {

    TextView firstName, LastName, Email, Mobile;
    Button change, can,btnChangePassword;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;
    String userId, FirstName1, lastName1, Email1, Mobile1;
    private ImageView profilePic;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);

        //Set the title in tool bar---------------------------------------------------------------------------------------------------
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //----IDS-------------------------------------------------------------------------------------------------------------------
        firstName = findViewById(R.id.lblfirstname);
        LastName = findViewById(R.id.lbllastname);
        Email = findViewById(R.id.lblemail);
        Mobile = findViewById(R.id.lblmobile);
        profilePic=findViewById(R.id.profile_image);

        change = findViewById(R.id.btnedit);
        can = findViewById(R.id.btncancel);
        btnChangePassword= findViewById(R.id.btnChangePassword);
        //Firebase needs------------------------------------------------------------------------------------------------------------
        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();

        //get current user ids-----------------------------------------------------------------------------------------------------
        userId = FAuth.getCurrentUser().getUid();

        profilePic = findViewById(R.id.profile_image);
        storage =FirebaseStorage.getInstance();
        storageReference =storage.getReference();

        //get the firebase values-----------------------------------------------------------------------------------------------
        final DocumentReference documentReference = FStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Mobile.setText(value.getString("Mobile"));
                firstName.setText(value.getString("FirstName"));
                LastName.setText(value.getString("LastName"));
                Email.setText(value.getString("Email"));
            }
        });

        //Add current user profile picture
        StorageReference riversRef = storageReference.child("users/"+FAuth.getCurrentUser().getUid()+"/profile.jpg");

        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
            }
        });

        //Click the change profile button-----------------------------------------------------------------------------------------
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateProfile.class);

                FirstName1 = firstName.getText().toString();
                i.putExtra("firstName", FirstName1);

                lastName1 = LastName.getText().toString();
                i.putExtra("LastName", lastName1);

                Email1 = Email.getText().toString();
                i.putExtra("email", Email1);

                Mobile1 = Mobile.getText().toString();
                i.putExtra("mobile", Mobile1);

                startActivity(i);
            }
        });
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChangePassword.class));
            }
        });
    }
}