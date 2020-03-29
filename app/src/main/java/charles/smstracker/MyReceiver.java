package charles.smstracker;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        Log.i("recebeu s>>>>>>>>>>", action);


        //context.startService();

        Handler handler3 = new Handler(Looper.getMainLooper());
        handler3.post(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(context,"recebeu o comando SMS",Toast.LENGTH_LONG).show();
            }
        });


        GPSTracker gps = new GPSTracker(context);
        String xy = String.valueOf(gps.getLatitude()) + " " + String.valueOf(gps.getLongitude());
        gps.stopUsingGPS(); //economizar energia

        ContentValues values = new ContentValues();

        values.put("address", "035992399317");
        values.put("body", xy);

        context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);


        //asdf

    }
}