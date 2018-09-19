package health.app.Utilities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import health.app.R;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Developer Six on 9/26/2017.
 */
@RuntimePermissions
public abstract class LocationActivity extends BaseActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private boolean settingsChecked;
    public LatLng latLng;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        // only stop if it's connected, otherwise we crash
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

    }


    @Override
    public void onConnected(Bundle dataBundle) {
        checkForSettings();
    }

    private void checkForSettings() {
        settingsChecked = true;
        // notify user
        if (!isGPSEnabled()) {
            Log.d("TAG", "GPS not enabled");
            AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
            dialog.setTitle(getString(R.string.app_name));
            dialog.setIcon(R.drawable.ptlogo);
            dialog.setMessage(getResources().getString(R.string.location_disabled_message));
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    LocationActivity.this.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(this.getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                   paramDialogInterface.dismiss();

                }
            });
            dialog.show();
        } else {
            LocationActivityPermissionsDispatcher.fetchLastKnownLocationWithPermissionCheck(this);

        }
    }

    @SuppressWarnings("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void fetchLastKnownLocation() {
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        LocationActivityPermissionsDispatcher.startLocationUpdatesWithPermissionCheck(this);

        // Note that this can be NULL if last location isn't already known.
        if (currentLocation != null) {
            // Print current location if not null
//            Log.d("DEBUG", "current location: " + mCurrentLocation.toString());
            latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//
            onCurrentLocationAvailaible(latLng);
        }
        // Begin polling for new location updates.


    }

    boolean isGPSEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        return gps_enabled;

    }


    public abstract void onCurrentLocationAvailaible(LatLng latLng);


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void startLocationUpdates() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
    //    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        LocationActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onLocationChanged(Location location) {

//        mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//        if(showingCurrentLocations){
//            showCurrentLocation(false);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        onCurrentLocationAvailaible(latLng);
//        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
