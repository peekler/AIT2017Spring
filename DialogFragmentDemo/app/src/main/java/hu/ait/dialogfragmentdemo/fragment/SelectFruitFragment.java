package hu.ait.dialogfragmentdemo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by peter on 2016. 11. 28..
 */

public class SelectFruitFragment extends DialogFragment implements DialogInterface.OnClickListener{

    public static final String TAG = "OptionsFragment";

    public interface OptionsFragmentInterface {
        public void onOptionsFragmentResult(String fruit);
    }

    private String[] options = {"Apple", "Orange", "Lemon"};
    private OptionsFragmentInterface optionsFragmentInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            optionsFragmentInterface =
                    (OptionsFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OptionsFragmentInterface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Please select");
        builder.setItems(options, this);
        AlertDialog alert = builder.create();

        return alert;
    }

    @Override
    public void onClick(DialogInterface dialog,
                        int choice) {
        optionsFragmentInterface.onOptionsFragmentResult(
                options[choice]);
    }
}
