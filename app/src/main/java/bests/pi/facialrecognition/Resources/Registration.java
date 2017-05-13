package bests.pi.facialrecognition.Resources;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import bests.pi.facialrecognition.*;
import bests.pi.facialrecognition.FinalVariables.ImutableVariables;
import bests.pi.facialrecognition.Network.Controller;
import bests.pi.facialrecognition.Validations.ValidField;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbarRegistration;
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private TextInputLayout layoutEmail, layoutPassword, layoutConfirmPassword;
    private Button buttonRegistration;
    private Button buttonCamera;
    private FaceDetector detector;
    private ArrayList<EditText> arrayEditText = new ArrayList<>();
    private ArrayList<TextInputLayout> arrayLayout = new ArrayList<>();
    private int cont;
    private String image;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        inicialize();
        this.buttonRegistration.setOnClickListener(this);
        this.buttonCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        if(view == buttonRegistration) {
            if (!isEmpty()) {
                if (ValidField.isValidEmail(this.editTextEmail)) {
                    if (ValidField.isEqualsPasswords(this.editTextPassword, this.editTextConfirmPassword)) {
                        if(cont > 0) {
                            progressDialog = ProgressDialog.show(this, "Recording", "Loading...");
                            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                    ImutableVariables.URL_REGISTRATION,
                                    response -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                        builder.setTitle("Data saved successfully!");
                                        builder.setMessage("Login to continue!");
                                        builder.setPositiveButton("OK", (dialog, which) -> {
                                            progressDialog.dismiss();
                                            startActivity(new Intent(Registration.this, Login.class));
                                            finish();
                                        });
                                        builder.show();
                                    }, error -> {
                                        progressDialog.dismiss();

                                        if (error instanceof TimeoutError){
                                            android.support.design.widget.Snackbar.make(view,
                                                    "Failed to register, Timeout", 3000).show();
                                        }

                                        else if (error instanceof NetworkError){
                                            android.support.design.widget.Snackbar.make(view,
                                                    "Failed to register, Check your connection", 3000).show();
                                        }

                                        else {
                                            android.support.design.widget.Snackbar.make(view,
                                                    "Fialed to register, E-mail already used in the database", 3000).show();
                                        }

                                        error.printStackTrace();

                                    }) {
                                        @Override
                                        public byte[] getBody() {
                                            StringBuilder sb = new StringBuilder();
                                            sb.append("{");
                                            sb.append("\"")
                                                    .append(ImutableVariables.EMAIL)
                                                    .append("\":\"")
                                                    .append(editTextEmail.getText().toString().trim())
                                                    .append("\",");
                                            sb.append("\"")
                                                    .append(ImutableVariables.PASSWORD)
                                                    .append("\":\"")
                                                    .append(editTextPassword.getText().toString().trim())
                                                    .append("\",");
                                            sb.append("\"").append(ImutableVariables.IMAGE)
                                                    .append("\":\"")
                                                    .append(image.trim())
                                                    .append("\"");
                                            sb.append("}");

                                            return sb.toString().getBytes();
                                        }

                                        @Override
                                        public String getBodyContentType() {
                                            return "application/json; charset=utf-8";
                                        }
                                    };
                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            Controller.getInstance(Registration.this).addToRequestQuee(stringRequest);
                        }
                        else{
                            android.support.design.widget.Snackbar.make(view,
                                    "Sorry, We need one picture!", 3000)
                                    .show();
                        }
                    }
                }
            }
        }
        else{
            cont = 0;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                 if (verifyPermission()){
                    dispatchTakePictureIntent();
                 }
            }
            else {
                dispatchTakePictureIntent();
            }
        }
    }

    private boolean verifyPermission() {
        if(ContextCompat.checkSelfPermission(Registration.this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED){
            return true;
        }
        ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.CAMERA}, 101);
        return verifyPermission();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            setIntent(new Intent(this, HomeScreen.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImutableVariables.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            cont = 0;
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            detector = new FaceDetector.Builder( getApplicationContext() )
                    .setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .setMode(FaceDetector.FAST_MODE)
                    .build();

            if (detector.isOperational()) {
                assert imageBitmap != null;
                Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
                SparseArray<Face> numberFaces = detector.detect(frame);
                detector.release();
                if (numberFaces.size() < 1) {
                    android.support.design.widget.Snackbar.make(buttonCamera,
                            "Sorry, We didn't detect any faces in the photo taken. Take it again",
                            3000).show();
                } else if (numberFaces.size() > 1) {
                    android.support.design.widget.Snackbar.make(buttonCamera,
                            "Sorry, We dectect more than one face in the photo taken. Take it again",
                            3000).show();
                } else {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    image = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    cont++;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_OK: {
                cont++;
            }
        }
    }
    private boolean isEmpty(){
        boolean empty = false;
        for (int i = 0; i < arrayEditText.size(); i++) {
            if (this.arrayEditText.get(i).getText().toString().isEmpty()) {
                empty = true;
                this.arrayEditText.get(i).setError("This field couldn't be empty!");
            }
        }
        return empty;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, ImutableVariables.REQUEST_IMAGE_CAPTURE);
        }
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
        this.toolbarRegistration.setTitle("Sign up");
        this.buttonCamera = (Button) findViewById(R.id.buttonCamera);
        this.cont = 0;
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
