package elchanchoverde.tripboard.Fragments;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import elchanchoverde.tripboard.Adapters.ListaAdapter;
import elchanchoverde.tripboard.Helpers.DataBaseHelper;
import elchanchoverde.tripboard.R;
import elchanchoverde.tripboard.RecyclerItemClickListener;

/**
 * Created by Ezequiel on 13/10/2016.
 */
public class CountryFragment extends Fragment {
    RecyclerView recyclerView;
    ListaAdapter adapter;
    DataBaseHelper dataBaseHelper;
    private static final String TAG = "RecyclerViewFragment";
    FloatingActionButton FAB;
    String countryRel;

    //Interfaz que comunica el fragment con la activity.
    public interface callBacks {
        public void onItemSelected(String nombreCountry, String country);

    }


//    String[] Data = new String[]{"Elemento 1", "Elemento 2", "Elemento 3",
//            "Elemento 4", "Elemento 5", "Elemento 6",
//            "Elemento 4", "Elemento 5", "Elemento 6",
//            "Elemento 4", "Elemento 5", "Elemento 6",};


    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.country_fragment, container, false);
        rootView.setTag(TAG);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.countryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.
                VERTICAL, false));
        FAB = (FloatingActionButton) rootView.findViewById(R.id.FAB);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity().getApplicationContext(),"Boton",Toast.LENGTH_LONG)
//                        .show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setMessage("Nombre del país: ")
                        .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity().getApplicationContext(),"Agregando",Toast.LENGTH_LONG)
//                        .show();
                                try {
                                    Cursor cursor = dataBaseHelper.FetchAllList();

                                    if (cursor != null) {
                                        //Busco el ultimo item. Para crear una nueva tabla.
                                        cursor.moveToLast();
                                        countryRel = cursor.getString(cursor.getColumnIndex(cursor.
                                                getColumnName(0)));
                                        cursor.close();
                                    }
                                } catch (SQLiteException e) {
                                    e.printStackTrace();
                                }
                                DataBaseHelper myDBHelper = new DataBaseHelper(getActivity().
                                        getApplicationContext());
                                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                ContentValues valores = new ContentValues();
                                valores.put("name", input.getText().toString());
                                valores.put("countriesrel", "Country" + countryRel);
                                String c = "Country" + countryRel;
                                db.insert("countries", null, valores);
                                db.execSQL("CREATE TABLE '" + c + "' ('_id' INTEGER PRIMARY KEY" +
                                        " AUTOINCREMENT NOT NULL UNIQUE," +
                                        "'city' TEXT NOT NULL)");
                                ContentValues v = new ContentValues();
                                v.put("city", "City A ");
                                db.insert(c, null, v);
                                db.close();

                                try {
                                    myDBHelper.OpenDataBase();
                                    Cursor cursor = myDBHelper.FetchAllList();
                                    if (cursor != null) {
                                        adapter = new ListaAdapter(getActivity().
                                                getApplicationContext(), cursor);
                                        recyclerView.setAdapter(adapter);
                                        recyclerView.addOnItemTouchListener(
                                                new RecyclerItemClickListener(getActivity().
                                                        getApplicationContext(),
                                                        new OnItemClickListener()));

                                        myDBHelper.Close();
                                    }
                                } catch (SQLiteException e) {
                                    e.printStackTrace();
                                }


                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
        try {
            dataBaseHelper.CreateDataBase();
        } catch (IOException e) {
            throw new Error("No se puede crear BD");
        }
        try {
            Cursor cursor;
            dataBaseHelper.OpenDataBase();
            try {
                cursor = dataBaseHelper.FetchAllList();

            } catch (SQLException e) {
                throw new Error("Error al recuperar los datos");
            }

            if (cursor != null) {
                adapter = new ListaAdapter(getActivity().getApplicationContext(), cursor);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new OnItemClickListener()));
            }
        } catch (SQLException e) {
            throw new Error("Error al abrir la BD");

        }
        return rootView;
    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {
        @Override
        public void onItemClick(View childView, int position) {
            TextView textView = (TextView) childView.findViewById(R.id.title);
            TextView countryName = (TextView) childView.findViewById(R.id.lista_nombre);
            ((callBacks) getActivity()).onItemSelected(textView.getText().toString(), countryName.getText().toString());
//            Toast.makeText(getActivity(),"Click en "+ textView.getText()+
//                    " y su tabla es " +countryName.getText().toString().toString(),
//                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongPress(View childView, int position) {
            Toast.makeText(getActivity(), "Long press", Toast.LENGTH_SHORT).show();
        }
    }
}
