package bests.pi.facialrecognition.Validations;

import android.widget.EditText;

import java.util.regex.Pattern;


public class ValidField {


    public static boolean isEqualsPasswords(EditText editTextPassword, EditText editTextConfirmPassword) {

        if(!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()))
        {
            //editTextConfirmPassword.setError("As senhas não correspondem!");
            return false;
        }
        else{
            return isCorrectPassword(editTextPassword);
        }
    }
    public static boolean isValidEmail(EditText editTextEmail) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if(pattern.matcher(editTextEmail.getText().toString()).matches()){
            return true;
        } else{
            //editTextEmail.setError("Digite um e-mail válido!");
            return false;
        }
    }
    public static boolean isCorrectPassword(EditText editText){
        if(editText.getText().toString().length() < 4){
            //editText.setError("A senha precisa conter no mínimo 4 caracteres!");
            return false;
        }else{
            return true;
        }
    }
}
