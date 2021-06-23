package algonquin.cst2335.inclassexamples_s21;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
public class MainActivity extends AppCompatActivity {

    /** Here you describe the variable. passwordtext holds what the user typed in */
    private EditText passwordText;

    /** This represents the result of the password complexity check */
    private TextView textView;

    /**The button the user clicks to login */
    private Button login;

    SQLiteDatabase theData; //not part of onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
            else textView.setText("No ABC string was found");

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
}