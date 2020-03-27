package charles.smstracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startClicked(View view) {
        Intent intentDoServico = new Intent(this, EnviarSMS.class); // ligar este contexto ao do servico

        startService(intentDoServico);
    }

   // Intent intent = new Intent(Activity.this, MyBackgroundService.class);
    //stopService(intent);
}
