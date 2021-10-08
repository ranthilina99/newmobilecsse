package com.example.csseproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateProfile extends AppCompatActivity implements View.OnClickListener {
    EditText firstName, LastName, Email, Mobile;
    Button btn, btn2;
    String f1, l1, e1, m1;
    FirebaseAuth FAuth;
    FirebaseFirestore FStore;
    FirebaseUser user;
    boolean valid = true;
    private ImageView profilePic;
    public Uri imageUri;
    private StorageReference storageReference;


    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        firstName = findViewById(R.id.Firstname);
        LastName = findViewById(R.id.lastname);
        Email = findViewById(R.id.Email);
        Mobile = findViewById(R.id.mobile);
        btn = findViewById(R.id.btnsave);
        btn2 = findViewById(R.id.btndelete);
        profilePic = findViewById(R.id.profile_image);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Intent i = getIntent();
        f1 = i.getStringExtra("firstName");
        l1 = i.getStringExtra("LastName");
        e1 = i.getStringExtra("email");
        m1 = i.getStringExtra("mobile");

        FAuth = FirebaseAuth.getInstance();
        FStore = FirebaseFirestore.getInstance();
        user = FAuth.getCurrentUser();


        // Call add profile picture function

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                choosePicture();

            }
        });

        //Add profile picture too current user
        StorageReference riversRef = storageReference.child("users/"+FAuth.getCurrentUser().getUid()+"/profile.jpg");

        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilePic);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(firstName);
                checkField(LastName);
                checkField(Email);
                checkField(Mobile);
                if (firstName.getText().toString().isEmpty() || LastName.getText().toString().isEmpty() || Email.getText().toString().isEmpty() || Mobile.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateProfile.this, "One or many Fires are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = Email.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference dooRef = FStore.collection("Users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("Email", email);
                        edited.put("FirstName", firstName.getText().toString());
                        edited.put("LastName", LastName.getText().toString());
                        edited.put("Mobile", Mobile.getText().toString());
                        dooRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Changeprofile.class));
                                finish();
                            }
                        });
                        Toast.makeText(UpdateProfile.this, "Email is Changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void DeleteUser() {
        FStore.collection("Users").document(user.getUid()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateProfile.this, "User Deleted", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btndelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure about field?");
                builder.setMessage("Delete is permanent....");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteUser();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
                ;
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firstName.setText(f1);
        LastName.setText(l1);
        Email.setText(e1);
        Mobile.setText(m1);
    }
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
        } else if (textFiled == LastName) {
            String val = LastName.getText().toString();
            if (val.isEmpty()) {
                LastName.setError("Field cannot be empty");
                valid = false;
            } else {
                LastName.setError(null);
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
        } else if (textFiled == Mobile) {
            String val = Mobile.getText().toString();

            if (val.isEmpty()) {
                Mobile.setError("Field cannot be empty");
                valid = false;
            } else {
                Mobile.setError(null);
                valid = true;
            }
            return valid;
        }
        return  valid;
    }


    //Addmage to database
    private void choosePicture(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey= UUID.randomUUID().toString();
//        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//        StorageReference riversRef = storageReference.child("images/");
        StorageReference riversRef = storageReference.child("users/"+FAuth.getCurrentUser().getUid()+"/profile.jpg");
        //StorageReference riversRef = storageReference.child("images/");

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content),"Image Uploaded",Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed to Upload",Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progressPresent= (100.00 * taskSnapshot.getBytesTransferred()/taskSnapshot.getBytesTransferred());
                pd.setMessage("Percentage: "+(int) progressPresent+"%");
            }
        });
    }






}