package charles.smstracker;

import android.app.Activity;
import android.content.Intent;
<<<<<<< HEAD
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.widget.Toast;
=======
import android.os.Bundle;
import android.view.View;
>>>>>>> db9ceb4503df5356e54558493a0edffe8785b682

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

        startService( new Intent(MainActivity.this, MyService.class));

        Handler handler3 = new Handler(Looper.getMainLooper());
        handler3.post(new Runnable(){
            @Override
            public void run(){
                Toast.makeText(MainActivity.this,"iniciou o servico e saiu da main",Toast.LENGTH_LONG).show();
            }
        });

    }
=======
    }

    public void startClicked(View view) {
        Intent intentDoServico = new Intent(this, EnviarSMS.class); // ligar este contexto ao do servico

        startService(intentDoServico);
    }

   // Intent intent = new Intent(Activity.this, MyBackgroundService.class);
    //stopService(intent);
>>>>>>> db9ceb4503df5356e54558493a0edffe8785b682
}
