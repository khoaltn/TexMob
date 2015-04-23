package com.ebookfrenzy.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

/**
 * Created by KhoaNguyen on 4/22/15.
 */
public class DialogAskNewFileName extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText input = new EditText(builder.getContext());
        final Intent intent = new Intent(builder.getContext(), Edit.class);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent.putExtra(Home.EXTRA_MESSAGE, input.getText().toString());
                startActivity(intent);
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setMessage("Enter file's name: ");

        return builder.create();
    }
}
