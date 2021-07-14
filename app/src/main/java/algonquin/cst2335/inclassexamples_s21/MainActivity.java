package algonquin.cst2335.inclassexamples_s21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    EditText theEdit;
    TextView tv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.theButton);
        theEdit = findViewById(R.id.editText);


        btn.setOnClickListener( click -> {



            //still on GUI thread, can't connect to server here
            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(  ( ) -> {

                URL url = null;
                try {
                    //connect to the server:
                    String serverURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(theEdit.getText().toString(), "UTF-8")
                            + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";


                    //on other cpu:
                    url = new URL(serverURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //this converts to a String
                    String text = (new BufferedReader(
                            new InputStreamReader(in, StandardCharsets.UTF_8)))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    //convert string to JSON object:

                    JSONObject theDocument = new JSONObject( text );
                    JSONObject coord = theDocument.getJSONObject("coord");
                    double lat = coord.getDouble("lat");
                    double lon = coord.getDouble("lon");

                    JSONArray weatherArray = theDocument.getJSONArray("weather");
                    JSONObject obj0 = weatherArray.getJSONObject(0);
                    JSONObject main = theDocument.getJSONObject("main");
                    double currentTemp = main.getDouble("temp");
                    double min = main.getDouble("temp_min");
                    double max = main.getDouble("temp_max");

                }
                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }  ); //run() function, run on different cpu




        });

    }
}