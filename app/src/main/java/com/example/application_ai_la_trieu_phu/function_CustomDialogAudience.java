package com.example.application_ai_la_trieu_phu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class function_CustomDialogAudience extends Dialog {
    public Context context;

    private Button buttonOK;
    private Button buttonCancel;

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;

    private Boolean ispress;

    public function_CustomDialogAudience(Context context){
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle saveInstaceState){
        super.onCreate(saveInstaceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custon_dialog_audience);


        this.buttonOK = (Button) findViewById(R.id.button_ok);
        this.buttonCancel  = (Button) findViewById(R.id.button_cancel);
        this.txt1  = (TextView) findViewById(R.id.txt1);
        this.txt2  = (TextView) findViewById(R.id.txt2);
        this.txt3  = (TextView) findViewById(R.id.txt3);
        active();
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clickOK();
                ispress = true;
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCancel();
                ispress = false;
            }
        });
    }

    private void active() {
        String a, b, c;
        a = "string a 99%";
        b = "string b 1%";
        c = "string c 0%";
        txt1.setText(a.toString());
        txt2.setText(b.toString());
        txt3.setText(c.toString());
    }

    private void clickCancel() {
        this.dismiss();
    }

    private void clickOK() {
        this.dismiss();
    }

    public boolean isButtonClicked() {
        return  ispress ;
    }
}
