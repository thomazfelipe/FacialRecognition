package bests.pi.facialrecognition.Resources;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import bests.pi.facialrecognition.*;
import bests.pi.facialrecognition.ArtificialIntelligence.CameraOpenCV;
import bests.pi.facialrecognition.ArtificialIntelligence.PersonRecognizer;
import bests.pi.facialrecognition.ArtificialIntelligence.Trainee;
import bests.pi.facialrecognition.FinalVariables.ImutableVariables;
import bests.pi.facialrecognition.General.UtilSingleton;
import bests.pi.facialrecognition.Network.Controller;
import bests.pi.facialrecognition.Validations.ValidField;

import static android.provider.Contacts.PresenceColumns.IDLE;

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
    private UtilSingleton util;
    private Mat mRgba;
    private Mat mGray;
    private CascadeClassifier mJavaDetector;
    private static final int frontCam =1;
    private int mChooseCamera = 2;
    private int faceState=IDLE;
    private static final Scalar FACE_RECT_COLOR     = new Scalar(0, 255, 0, 255);
    public static final int        JAVA_DETECTOR       = 0;
    public static final int TRAINING= 0;
    public static final int SEARCHING= 1;
    private int mDetectorType = JAVA_DETECTOR;
    private float mRelativeFaceSize   = 0.2f;
    private int mAbsoluteFaceSize   = 0;
    private Bitmap mBitmap;
    private File mCascadeFile;
    private CameraOpenCV mOpenCvCameraView;
    static final long MAXIMG = 10;
    private PersonRecognizer trainee;
    private int countImages = 0;
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    trainee.load();

                    try {
                        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "lbpcascade.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());

                        //mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(), 0);

                        cascadeDir.delete();

                    } catch (IOException ignored) {
                    }

                    mOpenCvCameraView.enableView();

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        util = UtilSingleton.getInstance();
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
                            progressDialog = ProgressDialog.show(
                                    this,
                                    getResources().getString(R.string.recording),
                                    getResources().getString(R.string.loading)
                            );
                            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                    ImutableVariables.URL_REGISTRATION,
                                    response -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                        builder.setTitle(getResources().getString(R.string.data_saved));
                                        builder.setMessage(getResources().getString(R.string.login_to_continue));
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
                                                    getResources().getString(R.string.failed_register)
                                                    + ", " +
                                                    getResources().getString(R.string.timeout), 3000).show();
                                        }

                                        else if (error instanceof NetworkError){
                                            android.support.design.widget.Snackbar.make(view,
                                                    getResources().getString(R.string.failed_register)
                                                    + ", " +
                                                    getResources().getString(R.string.error_network), 3000).show();
                                        }

                                        else {
                                            android.support.design.widget.Snackbar.make(view,
                                                    getResources().getString(R.string.failed_register)
                                                    + ", " +
                                                    getResources().getString(R.string.unique_email), 3000).show();
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
                                    getResources().getString(R.string.sorry)
                                    + ", " +
                                    getResources().getString(R.string.one_picture),
                                    3000).show();
                        }
                    }
                    else {
                        this.editTextConfirmPassword.setError(getResources().getString(R.string.match_passwords));
                    }
                }
                else {
                    this.editTextEmail.setError(getResources().getString(R.string.valid_email));
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
                            getResources().getString(R.string.sorry)
                            + ", " +
                            getResources().getString(R.string.no_one_face),
                            3000).show();
                } else if (numberFaces.size() > 1) {
                    android.support.design.widget.Snackbar.make(buttonCamera,
                            getResources().getString(R.string.sorry)
                            + ", " +
                            getResources().getString(R.string.many_faces),
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
                this.arrayEditText.get(i).setError(getResources().getString(R.string.empty_field));
            }
        }
        return empty;
    }

    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, ImutableVariables.REQUEST_IMAGE_CAPTURE);
//        }
          //mOpenCvCameraView.setCamFront();
        startActivity(new Intent(Registration.this, CameraCV.class));

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
        this.toolbarRegistration.setTitle(getResources().getString(R.string.sign_up));
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
        util.setBackground((LinearLayout) findViewById(R.id.activity_login));
    }
}
