package algonquin.cst2335.inclassexamples_s21;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



//part of the screen
public class ExampleFragment extends Fragment {
    boolean isTablet ;

    public ExampleFragment(boolean isT)
    {
        isTablet = isT;
    }

    //because of tx.commit() in the other file
//this is for inflating a view
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View thisLayout = inflater.inflate(R.layout.fragment_layout, container, false);

        Button add = thisLayout.findViewById(R.id.addButton);
        Button remove = thisLayout.findViewById(R.id.removeButton);

        add.setOnClickListener( addClicked -> {


            //need a fragment object:
            DetailsFragment theFragment = new DetailsFragment(); //I created this in my other files.

//1024 x 768 smallest width = 768
// 768 x 1024 smallest width = 768

            //call parent to get fragment manager
            //get parent:
            MainActivity parentActivity = (MainActivity)getContext(); //returns your parent

            FragmentManager fMgr =    parentActivity.getSupportFragmentManager(); // only one object
            FragmentTransaction tx = fMgr.beginTransaction();

            //either add, remove, or replace:
            //if tablet, change the id of framelayout:

            if(isTablet)
                tx.add(R.id.detailsLocation, theFragment);
            else //phone
                tx.add(R.id.fragmentLocation, theFragment); //load theFragment into FrameLayout with id R.id.fragmentLocation

            //finally commit:
            tx.commit(); //start the loading...



        });

        remove.setOnClickListener( removeClicked -> {
            MainActivity parent = (MainActivity)getContext();
//get fragment manager:
            FragmentManager mgr = parent.getSupportFragmentManager();
            mgr.beginTransaction().remove(this).commit();
        });

        return thisLayout;
    }
}
