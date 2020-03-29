package charles.smstracker;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
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
                Toast.makeText(MainActivity.this,"iniciou o servico e saiu da main",Toast.LENGTH_LONG).show();
            }
        });

    }

}
