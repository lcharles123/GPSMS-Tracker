package charles.smstracker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService( new Intent(MainActivity.this, MyService.class));

        Handler handler3 = new Handler(Looper.getMainLooper());
        handler3.post(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(MainActivity.this,"Serviço iniciado",Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this,"Esperando SMS...",Toast.LENGTH_LONG).show();
            }
        });

    }

}
