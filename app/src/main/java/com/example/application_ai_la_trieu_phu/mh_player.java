package com.example.application_ai_la_trieu_phu;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.application_ai_la_trieu_phu.tickclock.TimeCounter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mh_player extends AppCompatActivity implements View.OnClickListener {

    private ImageView btn_showscore;
    private TextView txt_time;
    private TextView Case[];
    private ImageButton btn_5050, btn_callfr, btn_audience, btn_change;
    private boolean isPlaying;
    private boolean isReady;
    private TimeCounter timeCounterLiveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout_play);
        first_activity();
        findbyID();
        setEvent();
        executeTime();
    }

    private void executeTime(){
        timeCounterLiveData = new ViewModelProvider(this).get(TimeCounter.class);
        timeCounterLiveData.timeRemaining().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                txt_time.setText(String.valueOf(integer));
            }
        });
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Perform background task here

                while(timeCounterLiveData.getTime() > 0){
                    // For example, let's simulate a long-running task
                    try {
                        Thread.sleep(1000); // Simulate a 3-second task
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Once the background task is done, update the UI using the Handler
                    if(timeCounterLiveData.getTime() >0){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Update UI here
                                timeCounterLiveData.timeTick();
                                // For example, update a TextView with the result
                            }
                        });
                    }
                }

            }
        });
    }
    private void first_activity() {
        isPlaying = false;
        isReady = false;
        Case = new TextView[4];
    }
    public void listCauHoi(){
        function_DialogListCauHoi dialogFragment = new function_DialogListCauHoi();
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }

    private void setEvent() {
        for (TextView caseView : Case) {
            caseView.setOnClickListener(this);
        }
        Case[0].setBackgroundResource(R.drawable.pngplayer_answer_background_normal);
        Case[1].setBackgroundResource(R.drawable.pngplayer_answer_background_selected);
        Case[2].setBackgroundResource(R.drawable.pngplayer_answer_background_true);
        Case[3].setBackgroundResource(R.drawable.pngplayer_answer_background_wrong);
        btn_showscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( );
                startActivity(intent);
            }
        });
        btn_showscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCauHoi();
            }
        });

        btn_5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOpenDialogClicked();
            }
        });
        btn_callfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonOpenDialogClicked2();
            }
        });
        btn_audience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonDialogClick3();
            }
        });
        Case[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Case[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Case[0].setBackgroundResource(R.drawable.pngplayer_answer_background_selected);
                Case[1].setText("Dap an chua biet dung sai");
            }
        });
        Case[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Case[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    private void findbyID(){
        btn_showscore = (ImageView) findViewById(R.id.btn_showscore);
        txt_time = (TextView) findViewById(R.id.btn_time);
        Case[0] = (TextView) findViewById(R.id.case_A);
        Case[1] = (TextView) findViewById(R.id.case_B);
        Case[2] = (TextView) findViewById(R.id.case_C);
        Case[3] = (TextView) findViewById(R.id.case_D);
        btn_5050 = (ImageButton) findViewById(R.id.btn_5050);
        btn_callfr = (ImageButton) findViewById(R.id.btn_callfr);
        btn_audience = (ImageButton) findViewById(R.id.btn_audience);
        btn_change = (ImageButton) findViewById(R.id.btn_change);


    }

    @Override
    public void onClick(View v) {
        // Xử lý sự kiện click cho các Case (TextView)
    }

    private void buttonOpenDialogClicked()  {

        final function_CustomDialog5050 dialog = new function_CustomDialog5050(this);

        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    // User clicked OK
                    btn_5050.setBackgroundResource(R.drawable.pngplayer_button_image_help_5050_x);
                }
            }
        });
    }

    private void buttonOpenDialogClicked2()  {

        final function_CustomDialogCallfr dialog = new function_CustomDialogCallfr(this);

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    // User clicked OK
                    btn_callfr.setBackgroundResource(R.drawable.pngplayer_button_image_help_call_x);
                }
            }
        });
    }

    private void buttonDialogClick3(){
        final function_CustomDialogAudience dialog = new function_CustomDialogAudience(this);

        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.isButtonClicked()) {
                    // User clicked OK
                    btn_audience.setBackgroundResource(R.drawable.pngplayer_button_image_help_audience_x);
                }
            }
        });
    }
}