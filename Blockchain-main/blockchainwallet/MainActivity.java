package com.example.blockchainwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Convert;
import java.math.BigDecimal;
import java.security.Provider;
import java.security.Security;

public class MainActivity extends AppCompatActivity {

    Web3j web3;
    Credentials credentials;
    Button btnsend;
    Animation up,down,indown,inup,blink;
    TextView textView8,textView9,textView10,textView11,txtbalance;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsend = findViewById(R.id.btntransfer);
        txtbalance=findViewById(R.id.text_balance);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        layout = findViewById(R.id.constraintLayout);
        web3 = MainActivity2.web3;
        credentials = MainActivity2.credentials;

        up = AnimationUtils.loadAnimation(MainActivity.this,R.anim.up);
        down = AnimationUtils.loadAnimation(MainActivity.this,R.anim.down);
        indown = AnimationUtils.loadAnimation(MainActivity.this,R.anim.indown);
        inup = AnimationUtils.loadAnimation(MainActivity.this,R.anim.inup);
        blink = AnimationUtils.loadAnimation(MainActivity.this,R.anim.blink);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnsend.startAnimation(inup);
        txtbalance.startAnimation(indown);
        textView8.startAnimation(indown);
        textView9.startAnimation(inup);
        textView10.startAnimation(inup);
        textView11.startAnimation(inup);
        layout.startAnimation(inup);
        retrieveBalance();
        setupBouncyCastle();
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

    public void ShowToast(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }

    public void gotoTransaction(View v)
    {
        animation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,MainActivity3.class));
            }
        },700);
    }

    public void gotoRetrive(View v)
    {
        animation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,MainActivity4.class));
            }
        },700);
    }

    public void retrieveBalance ()  {
        try {
            String balanceWei = web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString();
            BigDecimal balance = Convert.fromWei(balanceWei, Convert.Unit.ETHER);
            txtbalance.setText(balance.setScale(2,BigDecimal.ROUND_UP) + " ETH" );
            txtbalance.startAnimation(blink);
        }
        catch (Exception e){
            ShowToast("Check your Internet connectivity");
            txtbalance.clearAnimation();
        }
    }

    public void refresh (View v) {
        retrieveBalance();
    }

    public void animation()
    {
        btnsend.startAnimation(down);
        txtbalance.startAnimation(up);
        textView8.startAnimation(up);
        textView9.startAnimation(down);
        textView10.startAnimation(down);
        textView11.startAnimation(down);
        layout.startAnimation(down);
    }

}