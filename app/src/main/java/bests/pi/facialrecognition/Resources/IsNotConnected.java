package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bests.pi.facialrecognition.R;

public class IsNotConnected extends AppCompatActivity implements View.OnClickListener{

    protected Button buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_not_connected);

        this.buttonHome = (Button) findViewById(R.id.buttonHome);
        this.buttonHome.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        startActivity(new Intent(IsNotConnected.this, HomeScreen.class));
        finish();
    }
}
