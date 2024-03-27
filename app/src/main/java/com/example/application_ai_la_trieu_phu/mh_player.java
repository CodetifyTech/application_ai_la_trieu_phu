package com.example.application_ai_la_trieu_phu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mh_player extends AppCompatActivity implements View.OnClickListener {

    private Button btn_showscore, btn_time;
    private TextView Case[];
    private ImageButton btn_5050, btn_callfr, btn_audience, btn_change;
    private boolean isPlaying;
    private boolean isReady;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        first_activity();
        findbyID();
        setEvent();
    }

    private void first_activity() {
        isPlaying = false;
        isReady = false;
        Case = new TextView[4];
    }
    public void listCauHoi(){
        DialogListCauHoi dialogFragment = new DialogListCauHoi();
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
        btn_showscore = (Button) findViewById(R.id.btn_showscore);
        btn_time = (Button) findViewById(R.id.btn_time);
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

        final CustomDialog5050 dialog = new CustomDialog5050(this);

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

        final CustomDialogCallfr dialog = new CustomDialogCallfr(this);

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
        final CustomDialogAudience dialog = new CustomDialogAudience(this);

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