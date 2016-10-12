package elchanchoverde.tripboard.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import elchanchoverde.tripboard.EnviarMensaje;
import elchanchoverde.tripboard.Fragments.Derecha;
import elchanchoverde.tripboard.R;

public class MainActivity extends AppCompatActivity implements EnviarMensaje {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void enviarDatos(String mensaje) {

        Derecha derecha = (Derecha) getFragmentManager().findFragmentById(R.id.derecha);
        derecha.obtenerDatos(mensaje);
    }
}
