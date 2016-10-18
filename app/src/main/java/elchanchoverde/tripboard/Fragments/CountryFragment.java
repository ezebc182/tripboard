package elchanchoverde.tripboard.Fragments;


import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;

import elchanchoverde.tripboard.Adapters.ListaAdapter;
import elchanchoverde.tripboard.Helpers.DataBaseHelper;
import elchanchoverde.tripboard.R;

/**
 * Created by Ezequiel on 13/10/2016.
 */
public class CountryFragment extends Fragment {
    RecyclerView recyclerView;
    ListaAdapter adapter;
    DataBaseHelper dataBaseHelper;
    private static final String TAG = "RecyclerViewFragment";

    public interface callBacks{
        public void onItemSelected(String nombreCountry, String country);

    }


//    String[] Data = new String[]{"Elemento 1", "Elemento 2", "Elemento 3",
//            "Elemento 4", "Elemento 5", "Elemento 6",
//            "Elemento 4", "Elemento 5", "Elemento 6",
//            "Elemento 4", "Elemento 5", "Elemento 6",};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.country_fragment, container, false);
        rootView.setTag(TAG);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.countryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        try {
            dataBaseHelper.CreateDataBase();
        } catch (IOException e) {
            throw new Error("No se puede crear BD");
        }
        try {
            Cursor cursor;
            dataBaseHelper.OpenDataBase();
            try{
                cursor = dataBaseHelper.FetchAllList();

            }catch (SQLException e){
                throw new Error("Error al recuperar los datos");
            }

            if (cursor != null) {
                adapter = new ListaAdapter(getActivity().getApplicationContext(), cursor);
                recyclerView.setAdapter(adapter);
            }
        } catch (SQLException e) {
            throw new Error("Error al abrir la BD");

        }
        return rootView;
    }
}
