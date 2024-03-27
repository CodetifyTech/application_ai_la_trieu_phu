package com.example.application_ai_la_trieu_phu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class function_DialogHuongDan extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Sử dụng Builder class cho AlertDialog để tạo dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.huongdan)
                .setPositiveButton("Đã Hiểu", (dialog, id) -> {
                    // Hành động khi nhấn "OK"
                });
        // Tạo AlertDialog và trả về
        return builder.create();
    }
}
