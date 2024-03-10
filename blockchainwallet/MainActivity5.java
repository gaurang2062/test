package com.example.blockchainwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity5 extends AppCompatActivity {
    TextView textView,textView1;
    View layout;
    LottieAnimationView lottie;
    Animation up,down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        textView =findViewById(R.id.textView3);
        textView1 = findViewById(R.id.textView5);
        layout =findViewById(R.id.constraintLayout2);
        lottie = findViewById(R.id.lottieAnimationView);
        up = AnimationUtils.loadAnimation(MainActivity5.this,R.anim.up);
        down = AnimationUtils.loadAnimation(MainActivity5.this,R.anim.down);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.startAnimation(up);
                layout.startAnimation(down);
                textView1.startAnimation(down);
                lottie.startAnimation(up);

            }
        },3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.INVISIBLE);
                textView1.setVisibility(View.INVISIBLE);
                layout.setVisibility(View.INVISIBLE);
                lottie.setVisibility(View.INVISIBLE);
                startActivity(new Intent(MainActivity5.this, MainActivity2.class));
                finish();
            }
        },3700);

    }
}