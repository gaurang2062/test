package com.example.blockchainwallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import java.math.BigDecimal;

public class MainActivity3 extends AppCompatActivity {

    Button btnsend;
    EditText ethvalue;
    LottieAnimationView progressBar;
    ConstraintLayout constraintLayout;
    double value;
    private TextView balance;
    Animation up,down,indown,inup,blink,blinkinup;
    TextView textView,textView1,loadingtxt;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btnsend = findViewById(R.id.btnsend);
        ethvalue = findViewById(R.id.ethvalue);
        progressBar = findViewById(R.id.transactions);
        balance=findViewById(R.id.textView2);
        constraintLayout = findViewById(R.id.constraintLayout01);
        layout = findViewById(R.id.constraintLayout3);
        textView = findViewById(R.id.textView4);
        textView1 = findViewById(R.id.textView12);
        loadingtxt = findViewById(R.id.loadingtxt1);

        up = AnimationUtils.loadAnimation(MainActivity3.this,R.anim.up);
        down = AnimationUtils.loadAnimation(MainActivity3.this,R.anim.down);
        indown = AnimationUtils.loadAnimation(MainActivity3.this,R.anim.indown);
        inup = AnimationUtils.loadAnimation(MainActivity3.this,R.anim.inup);
        blink = AnimationUtils.loadAnimation(MainActivity3.this,R.anim.blink);
        blinkinup = AnimationUtils.loadAnimation(MainActivity3.this,R.anim.blinkinup);

        ethvalue.startAnimation(inup);
        btnsend.startAnimation(inup);
        balance.startAnimation(indown);
        textView.startAnimation(indown);
        textView1.startAnimation(inup);
        layout.startAnimation(inup);
        retrieveBalance ();
        ethvalue.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public void makeTransaction(View v) throws Exception {

        try {
            if(ethvalue.getText().toString().length()==0)
                throw new Exception();

            value  = Double.parseDouble(ethvalue.getText().toString());
            if(value<=0.0 && value>Double.parseDouble(balance.getText().toString().substring(0,balance.getText().length()-4)))
            {
                ethvalue.setError("Enter valid value!!");
                ethvalue.requestFocus();
                return;
            }
            else {
                AsyncTask asyncTask = new AsyncTask();
                asyncTask.execute();
            }
        }catch (Exception e)
        {
            ethvalue.setError("Enter How many Ethereum you want to send!!");
            ethvalue.requestFocus();
            return;
        }
    }

    public void ShowToast(String message) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void retrieveBalance ()  {
        try {
            String balanceWei = MainActivity2.web3.ethGetBalance(MainActivity2.credentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance().toString();
            BigDecimal eth = Convert.fromWei(balanceWei, Convert.Unit.ETHER);
            balance.setText(eth.setScale(2,BigDecimal.ROUND_UP) + " ETH" );
            balance.startAnimation(blink);
        }
        catch (Exception e){
            ShowToast("Check your Internet connectivity!!");
            balance.clearAnimation();
        }
    }

    private class AsyncTask extends android.os.AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ethvalue.startAnimation(down);
            btnsend.startAnimation(down);
            balance.startAnimation(up);
            textView.startAnimation(up);
            textView1.startAnimation(down);
            layout.startAnimation(down);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnsend.setVisibility(View.GONE);
                    ethvalue.setVisibility(View.GONE);
                    balance.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    textView1.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    loadingtxt.setVisibility(View.VISIBLE);
                    progressBar.startAnimation(indown);
                    loadingtxt.startAnimation(blinkinup);
                }
            },700);

        }

        @Override
        protected String doInBackground(String... strings) {

            String message;

            try{
                TransactionReceipt receipt = Transfer.sendFunds(MainActivity2.web3, MainActivity2.credentials,"0x67C759fCF0C36002c8D494dE59A60d76F4E043B2", BigDecimal.valueOf(value), Convert.Unit.ETHER).send();
                message = "Transaction Successful!!";
            }
            catch(Exception e) {
                message = "Transaction Failed!!";
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ShowToast(s);

            if(s.equals("Transaction Successful!!")) {
                progressBar.startAnimation(up);
                loadingtxt.startAnimation(down);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent= new Intent(MainActivity3.this, MainActivity6.class);
                        intent.putExtra("message","Transaction Completed Successfully!!");
                        startActivity(intent);
                        finish();
                    }
                },700);
            }
            else if(s.equals("Transaction Failed!!")){

                        progressBar.setVisibility(View.GONE);
                        loadingtxt.setVisibility(View.GONE);
                        btnsend.setVisibility(View.VISIBLE);
                        ethvalue.setVisibility(View.VISIBLE);
                        balance.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.VISIBLE);
                        textView1.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        constraintLayout.setVisibility(View.VISIBLE);
            }
        }
    }

}
