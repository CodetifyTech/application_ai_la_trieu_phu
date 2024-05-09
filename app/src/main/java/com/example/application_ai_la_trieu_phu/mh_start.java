package com.example.application_ai_la_trieu_phu;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


public class mh_start extends AppCompatActivity {
    Button btn;
    Button btn_start, btn_hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout_start);

        anhXa();
        xuly();
    }

    private void anhXa() {
        btn_start = findViewById(R.id.btn_start);
        btn_hd = findViewById(R.id.btn_hd);
    }

    private void xuly() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Toast.makeText(mh_start.this,
                        "Bạn Đã Sẵn Sàng Đến Với Chương Trình Của Chúng Tôi",
                        Toast.LENGTH_SHORT).show();

            startGame();
            }
        });

        btn_hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 huongDan();
            }
        });
    }

    public void huongDan(){
        function_DialogHuongDan dialogFragment = new function_DialogHuongDan();
        dialogFragment.show(getSupportFragmentManager(), "myDialog");
    }



    public void startGame(){
        Intent intent = new Intent(this, mh_player.class);
        startActivity(intent);
    }
}