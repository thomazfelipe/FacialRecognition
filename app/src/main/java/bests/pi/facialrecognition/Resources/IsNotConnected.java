package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import bests.pi.facialrecognition.General.UtilSingleton;
import bests.pi.facialrecognition.R;

public class IsNotConnected extends AppCompatActivity implements View.OnClickListener{

    protected Button buttonHome;
    private UtilSingleton util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_not_connected);
        util = UtilSingleton.getInstance();
        this.buttonHome = (Button) findViewById(R.id.buttonHome);
        this.buttonHome.setOnClickListener(this);
        util.setBackground((LinearLayout) findViewById(R.id.activity_home_screen));
    }
    @Override
    public void onClick(View view) {
        startActivity(new Intent(IsNotConnected.this, HomeScreen.class));
        finish();
    }
}
