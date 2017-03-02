package bests.pi.facialrecognition.Resources;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import bests.pi.facialrecognition.*;
import bests.pi.facialrecognition.Validations.ValidField;

public class Login extends AppCompatActivity implements View.OnClickListener{

    protected EditText editTextEmail, editTextPassword;
    protected TextInputLayout layoutEmail, layoutPassword;
    protected Button buttonLogin;
    protected Toolbar toolbarLogin;
    protected ArrayList<EditText> arrayEditText = new ArrayList<>();
    protected ArrayList<TextInputLayout> arrayLayout = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

        this.buttonLogin.setOnClickListener(this);
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
        boolean empty = false;
        for(int i = 0; i < arrayEditText.size(); i++){
            if(this.arrayEditText.get(i).getText().toString().isEmpty()){
                empty = true;
                this.arrayEditText.get(i).setError("Este campo não pode estar em branco!");
            }
        }
        if(!empty){
            if(ValidField.isValidEmail(this.editTextEmail)){
                if(ValidField.isCorrectPassword(this.editTextPassword)){
                    android.support.design.widget.Snackbar.make(this.editTextPassword, "Login Realizado com sucesso", 3000).show();
                }else{
                    this.editTextPassword.setError("E-mail e/ou senha incorretos");
                    this.layoutEmail.setErrorEnabled(true);
                }
            }else{
                this.editTextEmail.setError("Digite um e-mail válido!");
                this.layoutEmail.setErrorEnabled(true);
            }
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
        this.arrayEditText.add(this.editTextEmail);
        this.arrayEditText.add(this.editTextPassword);
        this.arrayLayout.add(this.layoutEmail);
        this.arrayLayout.add(this.layoutPassword);
    }
}