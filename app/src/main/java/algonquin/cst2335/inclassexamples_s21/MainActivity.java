package algonquin.cst2335.inclassexamples_s21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/** This class represents the first page of the application
 *  @author Eric Torunski
 *  @version 1.0.0
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /** Here you describe the variable. passwordtext holds what the user typed in */
    private EditText passwordText;

    /** This represents the result of the password complexity check */
    private TextView textView;

    /**The button the user clicks to login */
    private Button login;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    SQLiteDatabase theData; //not part of onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //Listen for a sensor information:
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                                //call onSensorChanged
        mSensorManager.registerListener(this,               mSensor, SensorManager.SENSOR_DELAY_NORMAL );

        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        //week 7
        MyOpenHelper myOpener = new MyOpenHelper( this );
        //Open the database
        theData = myOpener.getWritableDatabase();
//this is all you need for opening a database


        passwordText = findViewById(R.id.pw);
        textView = findViewById(R.id.textView);
        login = findViewById(R.id.loginButton);

        login.setOnClickListener( clk -> {
            String password = passwordText.getText().toString();

            if(checkPassword( password ))//check if password is complex enough
            {
                textView.setText("Your password has ABC");
            }
            else //incorrect password, vibrate motor:
            {
               // v.vibrate(1000);
//pattern is the number of seconds on / off
                                            //[0]   [1]  [2]  [3]
                long [] pattern = new long[] {500, 500, 500, 500};
                int [] amplitudes = new int[] {0,  255, 0,   128};  //The maximum value is 255
                v.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1) ); //repeat at [0]
                textView.setText("No ABC string was found");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

            String currentDateandTime = sdf.format(new Date());

//insert into database:
            ContentValues newRow = new ContentValues();
            //put something in each column, except _id:
            newRow.put(MyOpenHelper.col_password, password  );
            newRow.put(MyOpenHelper.col_time_sent, currentDateandTime ); //two put() statements for each column
//now insert:
           long id =  theData.insert(MyOpenHelper.TABLE_NAME, null, newRow);
            //now should be inserted

            Cursor rows = theData.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + " ;" , null);
          //index is at -1
            int pwColumnIndex = rows.getColumnIndex( MyOpenHelper.col_password );
            while(rows.moveToNext()) //move to next row, return false if past last row
            {
                String pw = rows.getString( pwColumnIndex );
                Log.i("pw", pw);
            }
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

    @Override //gravity should have 3 values (x, y, z)
    public void onSensorChanged(SensorEvent event) {
        Log.i( "air pressure is:" , String.format("%f", event.values[0]));//temp should only have 1 number
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//normally useless except for GPS sensor
    }
}