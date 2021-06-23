package algonquin.cst2335.inclassexamples_s21;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/** This class represents the first page of the application
 *  @author Eric Torunski
 *  @version 1.0.0
 */
public class MainActivity extends AppCompatActivity {

    /** Here you describe the variable. passwordtext holds what the user typed in */
    private EditText passwordText;

    /** This represents the result of the password complexity check */
    private TextView textView;

    /**The button the user clicks to login */
    private Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyOpenHelper myOpener = new MyOpenHelper( this );

        passwordText = findViewById(R.id.pw);
        textView = findViewById(R.id.textView);
        login = findViewById(R.id.loginButton);

        login.setOnClickListener( clk -> {
            String password = passwordText.getText().toString();

            if(checkPassword( password ))//check if password is complex enough
            {
                textView.setText("Your password has ABC");
            }
            else textView.setText("No ABC string was found");


        });
    }

    /** This function checks if ABC is in the string.
     *  More words for the detailed description.
     *  More words
     *  More words
     *  More words
     * @param password The string being checked
     * @return true if ABC is in the password otherwise false.
     */
    private boolean checkPassword(String password) {
        return password.contains("ABC");
    }
}