package com.example.csseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME = 6000;
    ImageView image;
    TextView hi,welcome;
    Animation topanim,bottomanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        image = findViewById(R.id.imageView);
        hi = findViewById(R.id.txthi);
        welcome = findViewById(R.id.txtwelcm);

        //Animations
        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.botton_animation);

        image.setAnimation(topanim);
        hi.setAnimation(bottomanim);
        welcome.setAnimation(bottomanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseAuth.getInstance().signOut();
                Intent n = new Intent(SplashScreen.this, Login.class);
                startActivity(n);
                finish();

            }
        },SPLASH_TIME);
    }
}