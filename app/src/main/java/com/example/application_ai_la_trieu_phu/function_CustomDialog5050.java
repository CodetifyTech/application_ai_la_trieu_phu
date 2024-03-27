package com.example.application_ai_la_trieu_phu;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class function_CustomDialog5050 extends Dialog {


    public Context context;

    private Button buttonOK;
    private Button buttonCancel;
    private boolean isButtonClicked = false;


    public function_CustomDialog5050(Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_custom_dialog_5050);


        this.buttonOK = (Button) findViewById(R.id.button_ok);
        this.buttonCancel  = (Button) findViewById(R.id.button_cancel);

        this.buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isButtonClicked = true; // Set to true when OK is clicked
                dismiss();
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isButtonClicked = false; // Set to false when Cancel is clicked
                dismiss();
            }
        });
    }




    public boolean isButtonClicked(){

        return isButtonClicked;
    }

}
