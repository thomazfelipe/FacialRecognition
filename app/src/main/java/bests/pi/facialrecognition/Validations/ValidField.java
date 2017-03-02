package bests.pi.facialrecognition.Validations;

import android.widget.EditText;
import java.util.regex.Pattern;

/**
 * Created by thomaz on 02/03/17.
 */

public class ValidField {
    public static boolean isEqualsPasswords(EditText editTextPassoword, EditText editTextConfirmPassword) {
        return editTextPassoword.getText().toString().equals(editTextConfirmPassword.getText().toString()) && editTextPassoword.getText().toString().length() > 3;
    }
    public static boolean isValidEmail(EditText editTextEmail) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(editTextEmail.getText().toString()).matches();
    }
    public static boolean isCorrectPassword(EditText editText){
        return true;
    }
}
