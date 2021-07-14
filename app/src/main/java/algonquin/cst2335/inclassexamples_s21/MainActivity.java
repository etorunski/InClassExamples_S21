package algonquin.cst2335.inclassexamples_s21;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
                            + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";


                    //on other cpu:
                    url = new URL(serverURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    //USE XML:

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    // ignore namespaces:
                    factory.setNamespaceAware(false);

                    //create the object
                    XmlPullParser xpp = factory.newPullParser();
                    //read from in, like Scanner
                    xpp.setInput( in  , "UTF-8");
                    //xpp is now pointing at BEGIN_DOCUMENT
                    //like Cursor starts at row -1

                    //int eventType = xpp.getEventType();
                    String currentTemp;
                    String minTemp;
                    String maxTemp;
                    String value;
                    //move xpp to first element:
                    while(xpp.next() != XmlPullParser.END_DOCUMENT)
                    {  //what are we currently pointing at?
                        switch(xpp.getEventType())
                        {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.END_DOCUMENT:
                                break;
                            case   XmlPullParser.START_TAG:
                                //look for temperature
                                if(xpp.getName().equals("temperature")) //which opening tag are we looking at?
                                {
                                    currentTemp = xpp.getAttributeValue(null, "value");
                                    minTemp = xpp.getAttributeValue(null, "min");
                                    maxTemp = xpp.getAttributeValue(null, "max");
                                }
                                else if(xpp.getName().equals("weather"))
                                {
                                    value = xpp.getAttributeValue(null, "value");
                                }
                                else if(xpp.getName().equals("Longitude")) {
                                    //
                                    xpp.next();//move forward one positoin:
                                    xpp.getText();//return string


                                    xpp.nextText();//advances and get text:

                                }
                                break;
                            case XmlPullParser.END_TAG:
                                break;
                            case   XmlPullParser.TEXT:
                                break;
                        }
                    }


                        /*commented out for now
                        //convert string to JSON object:

                        //this converts to a String
                        String text = (new BufferedReader(
                                new InputStreamReader(in, StandardCharsets.UTF_8)))
                                .lines()
                                .collect(Collectors.joining("\n"));

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

                         */

                }
                catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }

            }  ); //run() function, run on different cpu




        });

    }
}