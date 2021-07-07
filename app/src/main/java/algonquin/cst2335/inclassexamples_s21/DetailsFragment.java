package algonquin.cst2335.inclassexamples_s21;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;


public class DetailsFragment extends Fragment {

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
//inflate a layout:
        View detailLayout = inflater.inflate(R.layout.details_layout, container, false);

        return detailLayout;
    }
}
