package bests.pi.facialrecognition;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bests.pi.facialrecognition.Resources.HomeScreen;

public class MainActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed((Runnable) this,4000);
    }
    @Override
    public void run()
    {
            startActivity(new Intent(this, HomeScreen.class));
    }
}
