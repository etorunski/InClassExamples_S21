package algonquin.cst2335.inclassexamples_s21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//load XML layout

        //load object into Java


        TextView mytext = findViewById(R.id.mytextview);

        Button myButton = findViewById( R.id.mybutton );


        myButton.setOnClickListener( ( vw ) -> mytext.setText("You clicked the button") );

        CheckBox mycheck = findViewById(R.id.mycb);


        mycheck.setOnCheckedChangeListener( (buttonView, isChecked) -> {

            Context context = getApplicationContext();
            CharSequence text = "Hello to everyone!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }  );


        RadioButton radiobtn = findViewById(R.id.myradio);
        radiobtn.setOnCheckedChangeListener( (buttonView, isChecked) -> {
            Context context = getApplicationContext();
            CharSequence text = "Hello to everyone!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } );
    }
}