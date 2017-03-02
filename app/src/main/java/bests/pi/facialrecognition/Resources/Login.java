package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bests.pi.facialrecognition.*;

public class Login extends AppCompatActivity implements View.OnClickListener{

    protected EditText editTextEmail, editTextPassword;
    protected TextInputLayout layoutEmail, layoutPassword;
    protected Button buttonLogin;
    protected Toolbar toolbarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

        buttonLogin.setOnClickListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            setIntent(new Intent(this, HomeScreen.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View view) {
        if(view.equals(this.buttonLogin)){

        }
    }
    private void initialize() {
        this.editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        this.editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        this.layoutEmail = (TextInputLayout) findViewById(R.id.layoutEmail);
        this.layoutPassword = (TextInputLayout) findViewById(R.id.layoutPassword);
        this.buttonLogin = (Button) findViewById(R.id.buttonLogin);
        this.toolbarLogin = (Toolbar) findViewById(R.id.toolBarLogin);
        this.toolbarLogin.setTitle("Login");
        setSupportActionBar(this.toolbarLogin);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
