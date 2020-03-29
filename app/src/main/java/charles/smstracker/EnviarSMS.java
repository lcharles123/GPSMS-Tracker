package charles.smstracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.app.Service;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class EnviarSMS extends Service {
        public EnviarSMS() {
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onCreate() {
            Toast.makeText(this, "Invoke background service onCreate method.", Toast.LENGTH_LONG).show();
            super.onCreate();
        }


        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Toast.makeText(this, "Invoke background service onStartCommand method.", Toast.LENGTH_LONG).show();

            // codigo servico aqui




           /* LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); //acessar o servico;
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 5000, 10, locationListener);; // acessar os dados atraves do LocationListener
*/



            /*SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage("54554454555454545454545454", null, xy, null, null);*/

        for(;;) {
            Log.d("aaaababbbbb", "a mesage");
            /*SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage("54554454555454545454545454", null, "oioioi", null, null);*/

            GPSTracker gps = new GPSTracker(this);
            String xy = String.valueOf(gps.getLatitude()) + " " + String.valueOf(gps.getLongitude());

            ContentValues values = new ContentValues();

            values.put("address", "111222333444");
            values.put("body", xy);

            getApplicationContext().getContentResolver().insert(Uri.parse("content://sms/sent"),    values);

            SystemClock.sleep(60000 * 1 ); // 60 000= 1 minuto

            if(false){break;}
        }



//fim do codigo do servico
            return super.onStartCommand(intent, flags, startId);
        }
/*
    private void sendSms(String phonenumber,String message, boolean isBinary) //funcao para enviar sms
    {
        SmsManager manager = SmsManager.getDefault();

        PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);

        if(isBinary)
        {
            byte[] data = new byte[message.length()];

            for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index)
            {
                data[index] = (byte)message.charAt(index);
            }

            manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data,piSend, piDelivered);
        }
        else
        {
            int length = message.length();

            if(length > MAX_SMS_MESSAGE_LENGTH)
            {
                ArrayList<String> messagelist = manager.divideMessage(message);

                manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
            }
            else
            {
                manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
            }
        }
    }
*/

    @Override
        public void onDestroy() {
            super.onDestroy();
            Toast.makeText(this, "Invoke background service onDestroy method.", Toast.LENGTH_LONG).show();
        }
    }


/*---------- Listener class (MyLocationListener to interface LocationListener) ------------- */

class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
    double latitude; // Latitude
    double longitude; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }


    public Location getLocation() { //  obter location
        Log.d("GPS Enabled", "getlocation");
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.d("GPS Enabled", "no try");
            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
            } else {
                this.canGetLocation = true;

                Log.d("passa por aqui", "dentro de cangetlocation");
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        Log.d("GPS Enabled", "GPS Enabled");

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }


    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }


    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/Wi-Fi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing the Settings button.
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // On pressing the cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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