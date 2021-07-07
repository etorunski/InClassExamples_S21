package algonquin.cst2335.inclassexamples_s21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //loads immediately


        //need a fragment object:
        ExampleFragment theFragment = new ExampleFragment(); //I created this in my other files.

        FragmentManager fMgr = getSupportFragmentManager(); // only one object
        FragmentTransaction tx = fMgr.beginTransaction();

        //either add, remove, or replace:
        tx.add(R.id.fragmentLocation, theFragment); //load theFragment into FrameLayout with id R.id.fragmentLocation

        //finally commit:
        tx.commit(); //start the loading...



    }
}