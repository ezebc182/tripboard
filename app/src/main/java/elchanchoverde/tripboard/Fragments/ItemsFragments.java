package elchanchoverde.tripboard.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import elchanchoverde.tripboard.R;

/**
 * Created by Ezequiel on 19/10/2016.
 */
public class ItemsFragments extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.items_fragment, container, false);

        return rootView;
    }

}
