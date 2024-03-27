package com.example.application_ai_la_trieu_phu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogListCauHoi extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.layout_custom_dialog_list_cau_hoi, null);
        builder.setView(rootView)
                .setTitle("Mốc Tiền Thưởng")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Xử lý khi nhấn nút "OK"
                });

        AlertDialog dialog = builder.create();

        // Thiết lập màu nền cho dialog
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

        return dialog;
    }
}
