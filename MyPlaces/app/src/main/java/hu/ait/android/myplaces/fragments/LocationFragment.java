package hu.ait.android.myplaces.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import hu.ait.android.myplaces.R;

public class LocationFragment extends Fragment {

    public static final String TAG = "LocationFragment";

    private TextView tvProviderValue;
    private TextView tvLatValue;
    private TextView tvLngValue;
    private TextView tvSpeedValue;
    private TextView tvAltValue;
    private TextView tvPosTimeValue;

    private Location lastLocation = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_dashboard, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initField(R.id.fieldProvider,
                getActivity().getString(R.string.txt_provider));
        initField(R.id.fieldLat, getActivity().getString(R.string.txt_latitude));
        initField(R.id.fieldLng, getActivity()
                .getString(R.string.txt_longitude));
        initField(R.id.fieldSpeed, getActivity().getString(R.string.txt_speed));
        initField(R.id.fieldAlt, getActivity().getString(R.string.txt_alt));
        initField(R.id.fieldPosTime,
                getActivity().getString(R.string.txt_position_time));

        Button btnGeocode = (Button) view.findViewById(R.id.btnGeocode);
        btnGeocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastLocation != null) {
                    String address = "";
                    try {
                        Geocoder gc = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addrs = null;
                        addrs = gc.getFromLocation(lastLocation.getLatitude(),
                                lastLocation.getLongitude(), 1);

                        address = addrs.get(0).getCountryName() + "\n" +
                                addrs.get(0).getAddressLine(0) + "\n" +
                                addrs.get(0).getAddressLine(1);
                    } catch (Exception e) {
                        address = "No address: " + e.getMessage();
                    }

                    Toast.makeText(getActivity(),
                            address,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initField(int fieldId, String headText) {
        View viewField = getView().findViewById(fieldId);
        TextView tvHead = (TextView) viewField.findViewById(R.id.tvHead);
        tvHead.setText(headText);

        switch (fieldId) {
            case R.id.fieldProvider:
                tvProviderValue = (TextView) viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldLat:
                tvLatValue = (TextView) viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldLng:
                tvLngValue = (TextView) viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldSpeed:
                tvSpeedValue = (TextView) viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldAlt:
                tvAltValue = (TextView) viewField.findViewById(R.id.tvValue);
                break;
            case R.id.fieldPosTime:
                tvPosTimeValue = (TextView) viewField.findViewById(R.id.tvValue);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();

    }

    @Subscribe
    public void onNewLocation(Location currentLocation) {
        lastLocation = currentLocation;

        tvLatValue.setText("" + currentLocation.getLatitude());
        tvLngValue.setText("" + currentLocation.getLongitude());
        tvAltValue.setText("" + currentLocation.getAltitude());
        tvSpeedValue.setText("" + currentLocation.getSpeed());
        tvProviderValue.setText(currentLocation.getProvider());
        tvPosTimeValue.setText(new Date(currentLocation.getTime()).toString());
    }

    public Location getLastLocation() {
        return lastLocation;
    }
}

