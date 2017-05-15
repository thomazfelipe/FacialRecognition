package bests.pi.facialrecognition.General;

import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import bests.pi.facialrecognition.R;

public class UtilSingleton extends AppCompatActivity{

    private static UtilSingleton instance = null;
    private boolean lockTheme;

    protected UtilSingleton(){

    }

    public static UtilSingleton getInstance(){
        if (instance == null){
            instance = new UtilSingleton();
        }
        return instance;
    }

    public boolean isLockTheme() {
        return lockTheme;
    }

    public void setLockTheme(boolean lockTheme) {
        this.lockTheme = lockTheme;
    }

    public void setBackground(LinearLayout ll){
        ll.setBackgroundResource((!lockTheme) ? R.color.colorPrimary : R.color.colorAsphalt);
    }

}
