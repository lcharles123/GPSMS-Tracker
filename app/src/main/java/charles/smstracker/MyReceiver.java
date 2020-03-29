package charles.smstracker;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        //Log.i("Recebeu SMS", action);

        boolean smsCorreto = false;
        String credenciais = "user.password"; //keyword for app to send back the coordinates

        Handler handler3 = new Handler(Looper.getMainLooper());
        handler3.post(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(context,"SMS recebido",Toast.LENGTH_LONG).show();
            }
        });
        handler3.post(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(context,"Checando mensagem...",Toast.LENGTH_LONG).show();
            }
        });

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        final String msgBody = msgs[i].getMessageBody();

                        if(msgBody.equals(credenciais))
                            smsCorreto = true ; //verifica as credenciais se sao as corretas

                        if(smsCorreto)
                            handler3.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "SMS correto!", Toast.LENGTH_LONG).show();
                            }
                        });
                        else
                            handler3.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "SMS incorreto!", Toast.LENGTH_LONG).show();
                                }
                            });

                    }
                } catch (Exception e) {
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }

        GPSTracker gps = new GPSTracker(context);

        if(smsCorreto) {

            final String xy = String.valueOf(gps.getLatitude()) + "+" + String.valueOf(gps.getLongitude());

            handler3.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "obteve: " + xy , Toast.LENGTH_LONG).show();
                }
            });

            ContentValues values = new ContentValues();

            values.put("address", "000111222333");
            values.put("body", "https://maps.google.com/?q=" + xy);

            context.getContentResolver().insert(Uri.parse("content://sms/sent"), values); //only for DEBUG, copy sms to "sent"
            //SmsManager smsManager = SmsManager.getDefault(); //send SMS in background
            //smsManager.sendTextMessage("000111222333", null, "https://maps.google.com/?q=" + xy, null, null);

        } else {
            Toast.makeText(context, "esperando o prox SMS..." , Toast.LENGTH_LONG).show();
        }
    }
}