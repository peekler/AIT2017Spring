package hu.ait.dialogfragmentdemo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import hu.ait.dialogfragmentdemo.MainActivity;
import hu.ait.dialogfragmentdemo.R;

/**
 * Created by peter on 2016. 11. 28..
 */

public class MessageFragment extends DialogFragment {

    private OnMessageFragmentAnswer onMessageFragmentAnswer = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnMessageFragmentAnswer) {
            onMessageFragmentAnswer = (OnMessageFragmentAnswer) context;
        } else {
            throw new RuntimeException(
                    "This Activity is not implementing the " +
                    "OnMessageFragmentAnswer interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MainActivity.KEY_MSG);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.layout_dialog,null);
        final EditText etName = (EditText) dialogLayout.findViewById(R.id.etName);
        alertDialogBuilder.setView(dialogLayout);


        alertDialogBuilder.setTitle("Please read this message");
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onPositiveSelected();
            }
        });
        alertDialogBuilder.setNegativeButton("Nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onMessageFragmentAnswer.onNegativeSelected();
            }
        });


        return alertDialogBuilder.create();
    }
}
