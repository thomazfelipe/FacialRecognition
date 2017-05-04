package bests.pi.facialrecognition.Resources;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import bests.pi.facialrecognition.Domain.User;
import bests.pi.facialrecognition.FinalVariables.ImutableVariables;
import bests.pi.facialrecognition.Network.Controller;
import bests.pi.facialrecognition.Network.RequestLogin;
import bests.pi.facialrecognition.R;

public class IsConnected extends AppCompatActivity implements View.OnClickListener{

    protected Button buttonLogout;
    protected ImageView imageConnected;
    protected String picture = null;
    protected Bitmap image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_connected);

        initialize();
        setPicture();
        this.buttonLogout.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        SharedPreferences sp = getSharedPreferences(ImutableVariables.PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
        startActivity(new Intent(IsConnected.this, HomeScreen.class));
        finish();
    }
    private void setPicture() {
        RequestLogin request = new RequestLogin(Request.Method.GET, ImutableVariables.URL_GETIMAGE + 1,
                new Response.Listener<JSONObject>() {
                    final Gson gson = new Gson();
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        User user = gson.fromJson( jsonObject.toString(), User.class );
                        imageConnected.setImageDrawable(convertToDrawable(user));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        }){ };
        Controller.getInstance(IsConnected.this).addToRequestQuee(request);
    }
    private Drawable convertToDrawable(User user){
        byte[] bytes = new byte[user.getImage().length];
        for(int i = 0; i < user.getImage().length; i++) {
            bytes[i] = user.getImage()[i];
        }

        return new BitmapDrawable(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
    }
    public void initialize(){
        this.buttonLogout = (Button) findViewById(R.id.buttonLogout);
        this.imageConnected = (ImageView) findViewById(R.id.imageConnected);
    }
}