package hu.ait.datetimepickerdemo.fragment;

/**
 * Created by peter on 2016. 12. 01..
 */

public interface OnDateTimeSelected {
    public void onTimeSelected(int hour, int min);

    public void onDateSelected(int year, int month, int day);
}
