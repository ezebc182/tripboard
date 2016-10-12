package elchanchoverde.tripboard.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import elchanchoverde.tripboard.R;

/**
 * Created by Ezequiel on 12/10/2016.
 */
public class Derecha extends Fragment {

    View rootView;
    TextView txt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.derecha, container, false);
        txt = (TextView) rootView.findViewById(R.id.campotxt);

        return rootView;
    }

    public void obtenerDatos(String mensaje) {
        txt.setText(mensaje);
    }
}
