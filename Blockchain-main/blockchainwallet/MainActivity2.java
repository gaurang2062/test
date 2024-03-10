package com.example.blockchainwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.security.Provider;
import java.security.Security;

public class MainActivity2 extends AppCompatActivity {

    static Web3j web3;
    static Credentials credentials,credentials1;
    Animation up,down,indown,inup;
    LottieAnimationView lottie;
    View layout;
    TextView textView,textView1;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/266fe287b84e44d783d411b1037a1c53"));
        setupBouncyCastle();

        lottie = findViewById(R.id.lottieAnimationView1);
        btn = findViewById(R.id.buttonsconnect);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView7);
        layout = findViewById(R.id.constraintLayout5);
        up = AnimationUtils.loadAnimation(MainActivity2.this,R.anim.up);
        down = AnimationUtils.loadAnimation(MainActivity2.this,R.anim.down);
        indown = AnimationUtils.loadAnimation(MainActivity2.this,R.anim.indown);
        inup = AnimationUtils.loadAnimation(MainActivity2.this,R.anim.inup);

        lottie.startAnimation(indown);
        layout.startAnimation(inup);
        textView.startAnimation(inup);
        textView1.startAnimation(inup);
        btn.startAnimation(inup);
    }

    private void setupBouncyCastle() {
        final Provider provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME);
        if (provider == null) {
            return;
        }
        if (provider.getClass().equals(BouncyCastleProvider.class)) {
            return;
        }
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    public void createWallet(View v)
    {
        try {
            String  privetKey= "4464f404695a6bd5267e8a4d535ac880fcd72eafcac2adb9c1cc077e95076dfe";
            credentials = Credentials.create(privetKey);
            privetKey = "d0bfb864b1b0c99ce96505b20f59f6c11e32c9e888a9e907b94316039f197259";
            credentials1 = Credentials.create(privetKey);
            lottie.startAnimation(up);
            layout.startAnimation(down);
            textView.startAnimation(down);
            textView1.startAnimation(down);
            btn.startAnimation(down);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lottie.setVisibility(View.INVISIBLE);
                    layout.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    textView1.setVisibility(View.INVISIBLE);
                    btn.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(MainActivity2.this, MainActivity6.class);
                    intent.putExtra("message","Connection Established Successfully!!");
                    startActivity(intent);
                    finish();
                }
            },800);
        }
        catch(Exception e){
            ShowToast(e.getMessage().toString());
        }
    }
    public void ShowToast(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }
}
