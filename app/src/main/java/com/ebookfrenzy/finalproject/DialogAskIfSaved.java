package com.ebookfrenzy.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by KhoaNguyen on 4/22/15.
 */
public class DialogAskIfSaved extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Intent intent = new Intent(builder.getContext(), Home.class);

        builder.setPositiveButton("Yes >> Home", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(intent);
            }
        })
                .setNegativeButton("No. Go back.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setMessage("Are you sure you've saved everything?");
        return builder.create();
    }
}
