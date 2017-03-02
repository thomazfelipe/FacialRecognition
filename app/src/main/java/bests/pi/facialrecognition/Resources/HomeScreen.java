package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bests.pi.facialrecognition.*;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCreatePassword, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initialize();

        this.buttonCreatePassword.setOnClickListener(this);
        this.buttonLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view.equals(this.buttonCreatePassword)){
            startActivity(new Intent(HomeScreen.this, Registration.class));
        }
        else{
            startActivity(new Intent(HomeScreen.this, Login.class));
        }
    }
    private void initialize() {
        buttonCreatePassword = (Button) findViewById(R.id.buttonRegistrar);
        buttonLogin = (Button) findViewById(R.id.buttonDoLogin);
    }
}
