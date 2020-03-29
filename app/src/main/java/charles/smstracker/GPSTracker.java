package charles.smstracker;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by charles on 28/03/20.
 */

    public class GPSTracker extends Service implements LocationListener {

        private final Context mContext;
        // flag for GPS status
        boolean isGPSEnabled = false;
        // flag for GPS status
        boolean canGetLocation = false;
        Location location = null; // location
        double latitude; // latitude
        double longitude; // longitude
        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // in meters
        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 1000; // in milliseconds
        // Declaring a Location Manager
        protected LocationManager locationManager;
        public GPSTracker(Context context) {
            this.mContext = context;
            getLocation();
        }
        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(LOCATION_SERVICE);
                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (isGPSEnabled && location == null) {
                    this.canGetLocation = true;

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Thread.sleep(30000); //pausar 30s para receber updates, e nao apenas lastKnownLocation
                    Log.d("GPS", "obteve gps");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            locationManager.removeUpdates(this); //parar apos ter a localizacao, economizar bateria
            return location;
        }

        public void stopUsingGPS() {
            if (locationManager != null) {
                locationManager.removeUpdates(GPSTracker.this);
            }
        }

        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }
            return latitude;
        }

        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }
            return longitude;
        }

        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            // Setting Dialog Title
            alertDialog.setTitle("GPS is settings");

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable(){
                @Override
                public void run(){
                    Toast.makeText(mContext,"GPS desativado, favor ativar no modo 'Satélites'",Toast.LENGTH_LONG).show();
                }
            });
            // Setting Dialog Message
            alertDialog
                    .setMessage("GPS is not enabled. Do you want to go to settings menu?");
            // On pressing Settings button
            alertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(intent);
                        }
                    });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }
        @Override
        public void onLocationChanged(Location location) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }
    }

