package co.luthfillahmafazi.crudmakanan.UI.Makanan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.luthfillahmafazi.crudmakanan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananFragment extends Fragment {


    public MakananFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_makanan, container, false);
    }

}
