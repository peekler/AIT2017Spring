package hu.ait.viewpagerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentDetails extends Fragment {

    public static final String TAG = "FragmentDetails";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(
                        R.layout.frament_details,
                        container, false);

        return rootView;
    }
}
