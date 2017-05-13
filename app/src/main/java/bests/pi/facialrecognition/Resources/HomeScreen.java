package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.BoolRes;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Locale;

import bests.pi.facialrecognition.*;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCreatePassword, buttonLogin, buttonLanguage, buttonTheme;
    private boolean lock, lockTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initialize();
        this.buttonCreatePassword.setOnClickListener(this);
        this.buttonLogin.setOnClickListener(this);
        this.buttonTheme.setOnClickListener((event)->setLocale(!lockTheme?"pt":"en"));
        buttonLanguage.setOnClickListener((event)->setLocale(!lock?"pt":"en"));
        lockTheme = getIntent().getBooleanExtra("lockTheme", true);
        lock = getIntent().getBooleanExtra("lock", true);
        buttonLanguage.setBackground(getResources().getDrawable(!lock ? R.drawable.bra : R.drawable.usa));
    }
    @Override
    public void onClick(View view) {
        startActivity(
                (view.equals(this.buttonCreatePassword)) ?
                        (new Intent(HomeScreen.this, Registration.class)) :
                        (new Intent(HomeScreen.this, Login.class))
        );
    }

    private void initialize() {
        buttonCreatePassword = (Button) findViewById(R.id.buttonRegistrar);
        buttonLogin = (Button) findViewById(R.id.buttonDoLogin);
        buttonLanguage = (Button) findViewById(R.id.buttonLanguage);
        buttonTheme = (Button) findViewById(R.id.buttonTheme);
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(this, HomeScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("lock", !lock);
        startActivity(intent);
    }
}
