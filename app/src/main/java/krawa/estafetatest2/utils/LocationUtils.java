package krawa.estafetatest2.utils;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationUtils {

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;

    public LocationUtils(Context context) {
        mContext = context;
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void getCurrentLocation(final OnCurrentLocationCallback callback) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setExpirationTime(60 * 1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            callback.onCurrentLocationResult(null);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                callback.onCurrentLocationResult(location);
            }
        });
    }

    public interface OnCurrentLocationCallback {
        void onCurrentLocationResult(Location location);
    }

}
