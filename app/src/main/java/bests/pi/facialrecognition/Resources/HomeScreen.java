package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import bests.pi.facialrecognition.*;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCreatePassword, buttonLogin;
    private Toolbar toolbarHome;

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
        toolbarHome = (Toolbar) findViewById(R.id.toolBarHome);
        toolbarHome.inflateMenu(R.menu.menu_toolbar);
    }
}
