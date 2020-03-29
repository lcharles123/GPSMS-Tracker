package charles.smstracker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MyService extends Service {

//===========================broadcastreceive classe
    private final BroadcastReceiver receiver = new MyReceiver();
//=================================
    @Override
    public void onCreate() {

//=================== register receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, filter);
//===========================
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
    }
}
