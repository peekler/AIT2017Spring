package hu.ait.datetimepickerdemo.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by peter on 2016. 12. 01..
 */

public class TimePickerDialogFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

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
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,
                hour, min, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        onDateTimeSelected.onTimeSelected(hour,min);
    }
}
