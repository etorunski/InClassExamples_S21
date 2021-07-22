package com.cst2335.inclassexamples_s21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

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
boolean isHidden = false;

//called when user an item:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() )
        {
            case R.id.nav_clear:
            case R.id.hide_views:
                isHidden = !isHidden;
                tv.setVisibility( (isHidden)?View.INVISIBLE:View.VISIBLE );
                break;
            case R.id.nav_increase:
            case R.id.increase:

                float oldSize = theEdit.getTextSize();
                float newSize = Float.max(oldSize + 1, 5);

                tv.setTextSize(newSize);
                theEdit.setTextSize(newSize);
                btn.setTextSize(newSize);
                break;
            case R.id.nav_decrease:

                oldSize = theEdit.getTextSize();
                newSize = Float.max(oldSize - 10, 5);

                tv.setTextSize(newSize);
                theEdit.setTextSize(newSize);
                btn.setTextSize(newSize);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    //initializes toolbar:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //only change change the R.menu file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.my_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting a toolbar:
        Toolbar myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar); //Android calls onCreateOptionsMenu to inflate the menu

        //drawer layout: Need <DrawerLayout >
        DrawerLayout drawer = findViewById(R.id.myDrawerLayout);
                                                                                    //Generate Open and Close strings
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.myNavView);
        navView.setNavigationItemSelectedListener( (item) -> {
               onOptionsItemSelected(item); //let the other function handle it
            drawer.closeDrawer(GravityCompat.START);//this closes the drawer
                return false;
        });

//bottom nav view:
        BottomNavigationView bView = findViewById(R.id.bottomNav);
        bView.setOnNavigationItemReselectedListener((item) -> {
            onOptionsItemSelected(item);
        });

        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.theButton);
        theEdit = findViewById(R.id.editText);


        btn.setOnClickListener( click -> {

String cityName = theEdit.getText().toString();


             //still on GUI thread, can't connect to server here
                Executor newThread = Executors.newSingleThreadExecutor();
                newThread.execute(  ( ) -> {

                    URL url = null;
                    try {
                        //connect to the server:
                        String serverURL = "https://api.openweathermap.org/data/2.5/weather?q="
                                + URLEncoder.encode(cityName, "UTF-8")
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
                                    if(xpp.getName().equals("movie")) //which opening tag are we looking at?
                                    {
                                         currentTemp = xpp.getAttributeValue(null, "runtime");
                                         minTemp = xpp.getAttributeValue(null, "year");
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