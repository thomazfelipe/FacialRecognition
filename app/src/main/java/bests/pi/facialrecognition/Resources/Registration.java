package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import bests.pi.facialrecognition.*;
import bests.pi.facialrecognition.Validations.ValidField;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    protected Toolbar toolbarRegistration;
    protected EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    protected TextInputLayout layoutEmail, layoutPassword, layoutConfirmPassword;
    protected Button buttonRegistration;
    protected ArrayList<EditText> arrayEditText = new ArrayList<>();
    protected ArrayList<TextInputLayout> arrayLayout = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inicialize();

        this.buttonRegistration.setOnClickListener(this);
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
                if(ValidField.isEqualsPasswords(this.editTextPassword, this.editTextConfirmPassword)){
                    android.support.design.widget.Snackbar.make(editTextPassword, "Cadastro Realizado com sucesso", 3000).show();
                }
            }
        }
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
    private void inicialize() {
        this.editTextEmail = (EditText) findViewById(R.id.editTextEmailRegistration);
        this.editTextPassword = (EditText) findViewById(R.id.editTextPasswordRegistration);
        this.editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPasswordRegistration);
        this.layoutEmail = (TextInputLayout) findViewById(R.id.layoutEmailRegistration);
        this.layoutPassword = (TextInputLayout) findViewById(R.id.layoutPasswordRegistration);
        this.layoutConfirmPassword = (TextInputLayout) findViewById(R.id.layoutConfirmPasswordRegistration);
        this.buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        this.toolbarRegistration = (Toolbar) findViewById(R.id.toolBarRegistration);
        setSupportActionBar(this.toolbarRegistration);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        this.arrayEditText.add(this.editTextEmail);
        this.arrayEditText.add(this.editTextPassword);
        this.arrayEditText.add(this.editTextConfirmPassword);
        this.arrayLayout.add(this.layoutEmail);
        this.arrayLayout.add(this.layoutPassword);
        this.arrayLayout.add(this.layoutConfirmPassword);
    }
}
