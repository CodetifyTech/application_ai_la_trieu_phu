package com.example.application_ai_la_trieu_phu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mh_player extends AppCompatActivity implements View.OnClickListener {

    private Button btnshowscore, btntime;
    private TextView Case[];
    private ImageButton btn_5050, btn_callfr, btn_askpl, btn_advise;
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

    private void setEvent() {
        for (TextView caseView : Case) {
            caseView.setOnClickListener(this);
        }
        Case[0].setBackgroundResource(R.drawable.pngplayer_answer_background_normal);
        Case[1].setBackgroundResource(R.drawable.pngplayer_answer_background_selected);
        Case[2].setBackgroundResource(R.drawable.pngplayer_answer_background_true);
        Case[3].setBackgroundResource(R.drawable.pngplayer_answer_background_wrong);
    }


    private void findbyID(){
        btnshowscore = (Button) findViewById(R.id.btnshowscore);
        btntime = (Button) findViewById(R.id.btntime);
        Case[0] = (TextView) findViewById(R.id.caseA);
        Case[1] = (TextView) findViewById(R.id.caseB);
        Case[2] = (TextView) findViewById(R.id.caseC);
        Case[3] = (TextView) findViewById(R.id.caseD);

        btn_5050 = (ImageButton) findViewById(R.id.btn_5050);
        btn_callfr = (ImageButton) findViewById(R.id.btn_callfr);
        btn_askpl = (ImageButton) findViewById(R.id.btn_askpl);
        btn_advise = (ImageButton) findViewById(R.id.btn_advise);


    }

    @Override
    public void onClick(View v) {
        // Xử lý sự kiện click cho các Case (TextView)
    }

}