package com.example.application_ai_la_trieu_phu;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class function_CustomDialogCallfr extends Dialog{
    public Context context;
    private Button btncall1, btncall2, btncall3, btncall4, btncancel;

    private Boolean isPress;

    public function_CustomDialogCallfr(Context context){
        super(context);
        this.context = context;
    }

    protected void onCreate(Bundle savadInstanceState){
        super.onCreate(savadInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom_dialog_callfr);


        btncall1 = (Button) findViewById(R.id.bntcall1);
        btncall2 = (Button) findViewById(R.id.bntcall2);
        btncall3 = (Button) findViewById(R.id.bntcall3);
        btncall4 = (Button) findViewById(R.id.bntcall4);
        btncancel = (Button) findViewById(R.id.bntcancel);

        btncall1.setBackgroundResource(R.drawable.bg_ask);
        btncall2.setBackgroundResource(R.drawable.bg_ask);
        btncall3.setBackgroundResource(R.drawable.bg_ask);
        btncall4.setBackgroundResource(R.drawable.bg_ask);
        btncall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btncall1.setBackgroundResource(R.drawable.btn_help);
                btncall2.setBackgroundResource(R.drawable.bg_ask);
                btncall3.setBackgroundResource(R.drawable.bg_ask);
                btncall4.setBackgroundResource(R.drawable.bg_ask);
                isPress =true;
            }
        });

        btncall2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btncall1.setBackgroundResource(R.drawable.bg_ask);
                btncall2.setBackgroundResource(R.drawable.btn_help);
                btncall3.setBackgroundResource(R.drawable.bg_ask);
                btncall4.setBackgroundResource(R.drawable.bg_ask);
                isPress =true;
            }
        });

        btncall3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btncall1.setBackgroundResource(R.drawable.bg_ask);
                btncall2.setBackgroundResource(R.drawable.bg_ask);
                btncall3.setBackgroundResource(R.drawable.btn_help);
                btncall4.setBackgroundResource(R.drawable.bg_ask);
                isPress =true;
            }
        });

        btncall4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btncall1.setBackgroundResource(R.drawable.bg_ask);
                btncall2.setBackgroundResource(R.drawable.bg_ask);
                btncall3.setBackgroundResource(R.drawable.bg_ask);
                btncall4.setBackgroundResource(R.drawable.btn_help);
                isPress =true;
            }
        });


        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    public boolean isButtonClicked() {
        return isPress ;
    }
}
