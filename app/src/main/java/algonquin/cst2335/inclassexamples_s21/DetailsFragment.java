package algonquin.cst2335.inclassexamples_s21;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class DetailsFragment extends Fragment {

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
//inflate a layout:
        View detailLayout = inflater.inflate(R.layout.details_layout, container, false);

        Button removeButton = detailLayout.findViewById(R.id.button);
        removeButton.setOnClickListener( rem -> {
            MainActivity parent = (MainActivity)getContext();
//get fragment manager:
            FragmentManager mgr = parent.getSupportFragmentManager();
            mgr.beginTransaction().remove(this).commit();

        });
        return detailLayout;
    }
}
