package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Locale;

import bests.pi.facialrecognition.*;
import bests.pi.facialrecognition.FinalVariables.ImutableVariables;
import bests.pi.facialrecognition.General.UtilSingleton;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener{

    private Button buttonCreatePassword, buttonLogin, buttonLanguage, buttonTheme;
    private boolean lockLanguage;
    private boolean lockTheme;
    private UtilSingleton util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        util = UtilSingleton.getInstance();
        initialize();
        buttonCreatePassword.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        buttonLanguage.setOnClickListener((event)->setLocale(
                !lockLanguage ? ImutableVariables.LANGUAGE_PT : ImutableVariables.LANGUAGE_EN)
        );
        lockLanguage = getIntent().getBooleanExtra("lockLanguage", true);
        buttonLanguage.setBackground(getResources().getDrawable(
                !lockLanguage ? R.drawable.bra : R.drawable.usa)
        );
        buttonTheme.setOnClickListener((event)->controlLock(
                !lockTheme ? ImutableVariables.THEME_DARK : ImutableVariables.THEME_LIGHT)
        );
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(
                (view.equals(this.buttonCreatePassword)) ?
                (new Intent(HomeScreen.this, Registration.class)) :
                (new Intent(HomeScreen.this, Login.class))
        );
        startActivity(intent);
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
        intent.putExtra("lockLanguage", !lockLanguage);
        startActivity(intent);
    }

    public void controlLock(int theme){
        lockTheme = ((theme == 0));
        util.setLockTheme(lockTheme);
        util.setBackground((LinearLayout) findViewById(R.id.activity_home_screen));
    }

    private void initialize() {
        buttonCreatePassword = (Button) findViewById(R.id.buttonRegistrar);
        buttonLogin = (Button) findViewById(R.id.buttonDoLogin);
        buttonLanguage = (Button) findViewById(R.id.buttonLanguage);
        buttonTheme = (Button) findViewById(R.id.buttonTheme);
        util.setBackground((LinearLayout) findViewById(R.id.activity_home_screen));
    }
}
