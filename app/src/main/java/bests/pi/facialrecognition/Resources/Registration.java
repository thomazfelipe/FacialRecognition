package bests.pi.facialrecognition.Resources;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.ArrayList;
import bests.pi.facialrecognition.*;
import bests.pi.facialrecognition.FinalVariables.ImutableVariables;
import bests.pi.facialrecognition.Network.Controller;
import bests.pi.facialrecognition.Validations.ValidField;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    protected Toolbar toolbarRegistration;
    protected EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    protected TextInputLayout layoutEmail, layoutPassword, layoutConfirmPassword;
    protected Button buttonRegistration;
    protected Camera cam;
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
    public void onClick(final View view) {
        boolean empty = false;
        for (int i = 0; i < arrayEditText.size(); i++) {
            if (this.arrayEditText.get(i).getText().toString().isEmpty()) {
                empty = true;
                this.arrayEditText.get(i).setError("Este campo não pode estar em branco!");
            }
        }
        if (!empty) {
            if (ValidField.isValidEmail(this.editTextEmail)) {
                if (ValidField.isEqualsPasswords(this.editTextPassword, this.editTextConfirmPassword)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ImutableVariables.URL_REGISTRATION,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                    builder.setTitle("Cadastro realizado com sucesso!");
                                    builder.setMessage(" Agora precisamos de algumas fotos sua, tudo bem?");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            startActivity(new Intent(Registration.this, TakePictures.class));
                                            finish();
                                        }
                                    });
                                    builder.show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            android.support.design.widget.Snackbar.make(view, "Erro ao cadastrar, tente novamente", 3000).show();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        public byte[] getBody() {
                            StringBuilder sb = new StringBuilder();
                            sb.append("{");
                            sb.append("\"").append(ImutableVariables.EMAIL).append("\":\"").append(editTextEmail.getText().toString().trim()).append("\",");
                            sb.append("\"").append(ImutableVariables.PASSWORD).append("\":\"").append(editTextPassword.getText().toString().trim()).append("\"");
                            sb.append("}");

                            return sb.toString().getBytes();
                        }
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }
                    };
                    Controller.getInstance(Registration.this).addToRequestQuee(stringRequest);
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
