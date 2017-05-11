package hu.ait.datetimepickerdemo.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by peter on 2016. 12. 01..
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private OnDateTimeSelected onDateTimeSelected;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof OnDateTimeSelected)) {
            throw new RuntimeException("The activity does not implement the" +
                    "OnDateTimeSelected interface");
        }

        onDateTimeSelected = (OnDateTimeSelected) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,
                year, month, day);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        onDateTimeSelected.onDateSelected(year,month,day);
    }
}
