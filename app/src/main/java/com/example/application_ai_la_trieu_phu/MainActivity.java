package com.example.application_ai_la_trieu_phu;

import static com.example.application_ai_la_trieu_phu.R.id.btn_hd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class  MainActivity extends AppCompatActivity {
    Button btn_start, btn_hd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mh_start);
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
                Toast.makeText(MainActivity.this, "Bạn Đã Sẵn Sàng Đến Với Chương Trình Của Chúng Tôi", Toast.LENGTH_SHORT).show();

            startGame();
            }
        });

    }

    public void startGame(){
    Intent intent = new Intent(this, mh_null.class);
    startActivity(intent);

    }
}