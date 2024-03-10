package com.example.blockchainwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class MainActivity6 extends AppCompatActivity {

    MediaPlayer bell;
    TextView message;
    Animation down,inup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        bell = MediaPlayer.create(MainActivity6.this,R.raw.bell);
        message = findViewById(R.id.message);
        down = AnimationUtils.loadAnimation(MainActivity6.this,R.anim.down);
        inup = AnimationUtils.loadAnimation(MainActivity6.this,R.anim.inup);

        message.setText(getIntent().getStringExtra("message"));
        message.startAnimation(inup);
        bell.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity6.this, MainActivity.class));
                finish();
            }
        },1800);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bell.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bell.release();
    }

}