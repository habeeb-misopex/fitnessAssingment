package com.example.bethenics;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private Button btStart,btSkip, btWork, btSkill;
    private CountDownTimer countDownTimer;
    private int stretchIndex = 0; // Index for stretches

    private final int[] stretches = { R.drawable.sc1, R.drawable.sc2, R.drawable.sc3,R.drawable.sc4,R.drawable.sc5 };
    private final int STRETCH_TIME = 10000; // 10 seconds in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Bind views
        tvTimer = findViewById(R.id.tvTimer);
        btStart = findViewById(R.id.btStart);
        btSkip = findViewById(R.id.btSkip);
        btWork = findViewById(R.id.btWork);
        btSkill = findViewById(R.id.btSkill);
        ImageView stretchImageView = findViewById(R.id.imvStret);

        // Set initial stretch image
        stretchImageView.setImageResource(stretches[stretchIndex]);

        // Skip button functionality
        btSkip.setOnClickListener(view -> {
            moveToNextStretch(stretchImageView);

        });


        // Workout and Skills buttons functionality (just placeholders for now)
        btWork.setOnClickListener(v ->  {
            Intent intent = new Intent(this,WorkActivity.class);
            startActivity(intent);
        });

        btSkill.setOnClickListener(view -> {

        });

        btStart.setOnClickListener(view -> {
            // Start timer logic
            startTimer();
        });
    }



    private void startTimer() {
        countDownTimer = new CountDownTimer(STRETCH_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btStart.setEnabled(false);
                tvTimer.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                moveToNextStretch(findViewById(R.id.imvStret));
            }
        }.start();
    }



    private void moveToNextStretch(ImageView stretchImageView) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (stretchIndex < stretches.length - 1) {
            stretchIndex++;
            stretchImageView.setImageResource(stretches[stretchIndex]);
            startTimer();
        } else {
            // All stretches complete
            btSkip.setEnabled(false);
            btWork.setEnabled(true);
            btSkill.setEnabled(true);
            tvTimer.setText("Done!");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }




//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }
}