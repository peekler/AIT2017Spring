package hu.ait.android.myplaces.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

public class AITLocationManager implements LocationListener {

    private Context context;
    private LocationManager locMan;

    public AITLocationManager(Context aContext) {
        context = aContext;
        locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startLocationMonitoring() throws SecurityException {
        locMan.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                100, 100, this);
        // NETWORK PROVIDER IS NOT WORKING ON EMULATOR - THROWS AN EXCEPTION!!!
        locMan.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0, 0, this);
    }

    public void stopLocationMonitoring() {
        if (locMan != null) {
            locMan.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        EventBus.getDefault().post(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}