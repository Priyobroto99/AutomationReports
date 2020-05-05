package com.priyo.automationreports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView run;
    TextView log;
    TextView report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        run = (TextView) findViewById(R.id.run);
        log = (TextView) findViewById(R.id.log);
        report = (TextView) findViewById(R.id.report);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

        /*new CountDownTimer(1000, 100) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                loadAnimation(run);

                new CountDownTimer(1000, 100) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        loadAnimation(log);
                        new CountDownTimer(1000, 100) {

                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {
                                loadAnimation(report);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }.start();
                    }
                }.start();
            }
        }.start();
*/




    }

    public void loadAnimation(TextView textView){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fadein);
        textView.startAnimation(animation);
    }


}
