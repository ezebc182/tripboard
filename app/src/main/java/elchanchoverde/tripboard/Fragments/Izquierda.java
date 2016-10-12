package elchanchoverde.tripboard.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import elchanchoverde.tripboard.EnviarMensaje;
import elchanchoverde.tripboard.R;

/**
 * Created by Ezequiel on 12/10/2016.
 */
public class Izquierda extends Fragment {
    View rootView;
    EditText campo;
    Button boton;
    EnviarMensaje EM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.izquierda, container, false);
        campo = (EditText) rootView.findViewById(R.id.campotxt);
        boton = (Button) rootView.findViewById(R.id.btn);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje;
                mensaje = campo.getText().toString();
                EM.enviarDatos(mensaje);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        Activity a;

        super.onAttach(context);
        try {
            if (context instanceof Activity) {
                a = (Activity) context;
                EM = (EnviarMensaje) a;

            }

        } catch (ClassCastException e) {
            Toast.makeText(context,context.toString(),Toast.LENGTH_LONG);
            throw new ClassCastException(context.toString() + " Necesitas implemantar.");

        }
    }

}
