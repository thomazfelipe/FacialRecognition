package bests.pi.facialrecognition.Resources;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bests.pi.facialrecognition.FinalVariables.ImutableVariables;
import bests.pi.facialrecognition.R;

public class TakePictures extends AppCompatActivity {

    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pictures);

            while(cont < 4) {
                dispatchTakePictureIntent();
                cont++;
            }
            showDialog();
        }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, ImutableVariables.REQUEST_IMAGE_CAPTURE);
        }
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TakePictures.this);
        builder.setTitle("Pronto!");
        builder.setMessage(" Seu cadastro foi feito com sucesso! Faça o login! :)");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                startActivity(new Intent(TakePictures.this, Login.class));
                finish();
            }
        });
        builder.show();
    }
}
