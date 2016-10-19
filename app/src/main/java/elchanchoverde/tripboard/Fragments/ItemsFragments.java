package elchanchoverde.tripboard.Fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import elchanchoverde.tripboard.Helpers.DataBaseHelper;
import elchanchoverde.tripboard.R;

/**
 * Created by Ezequiel on 19/10/2016.
 */
public class ItemsFragments extends Fragment {
    ListView listView;
    TextView addItem;
    SimpleCursorAdapter cursorAdapter;
    Cursor mCursor;
    String country;
    String[] from;
    int[] to;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.items_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_item);
        addItem = (TextView) rootView.findViewById(R.id.addItem);

        Bundle bundle = getArguments();
        if (bundle != null) {
            country = (String) bundle.get("country");

            DataBaseHelper mDbHelper = new DataBaseHelper(getActivity());
            try {
                mDbHelper.CreateDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mDbHelper.OpenDataBase();
                mCursor = mDbHelper.FetchItemsList(country);

                if (mCursor != null) {
                    from = new String[]{"_id", "city"};
                    to = new int[]{R.id.id, R.id.titleItem};

                    cursorAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
                            R.layout.item_list, mCursor,
                            from, to);
                    listView.setAdapter(cursorAdapter);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                mDbHelper.Close();
            }
        }


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Click 1",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Agregar una ciudad");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHelper mDbHelper = new DataBaseHelper(getActivity().
                                getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues valores = new ContentValues();
                        valores.put("city", input.getText().toString());
                        try {
                            db.insert(country, null, valores);
                            Toast.makeText(getActivity(), "Ciudad agregada", Toast.LENGTH_SHORT).show();

                            mDbHelper.OpenDataBase();
                            Cursor cursor = mDbHelper.FetchItemsList(country);
                            from = new String[]{"_id","city"};
                            to = new int[] {R.id.id,R.id.titleItem};
                            cursorAdapter =
                                    new SimpleCursorAdapter(getActivity().getApplicationContext(),
                                            R.layout.item_list,cursor,from,to);
                            listView.setAdapter(cursorAdapter);

                        } catch (SQLiteException e) {
                            e.printStackTrace();

                        } finally {
                            db.close();

                        }

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"Click 2",Toast.LENGTH_SHORT).show();
                final TextView ID = (TextView) view.findViewById(R.id.id);
                final TextView CITY = (TextView) view.findViewById(R.id.titleItem);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Eliminar");
                builder.setMessage(Html.fromHtml("¿Seguro que desea eliminar la ciudad <b>" +
                        CITY.getText().toString()
                        + "</b>?"));
                builder.setNegativeButton("Cancelar", null);
                builder.setPositiveButton("Eliminar", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Toast.makeText(getActivity(), "Si deseo eliminarlo", Toast.LENGTH_SHORT).show();
                        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getApplicationContext());
                        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
                        String where = "_id = '" + ID.getText().toString() + "'";
                        if (db.delete(country, where, null) > 0) {
                            dataBaseHelper.OpenDataBase();
                            Cursor cursor = dataBaseHelper.FetchItemsList(country);
                            from = new String[]{"_id","city"};
                            to = new int[] {R.id.id,R.id.titleItem};
                            cursorAdapter =
                                    new SimpleCursorAdapter(getActivity().getApplicationContext(),
                                            R.layout.item_list,cursor,from,to);
                            listView.setAdapter(cursorAdapter);
                            Toast.makeText(getActivity(), CITY.getText().toString() + " ha sido eliminada", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), CITY.getText().toString() + " no pudo ser eliminada", Toast.LENGTH_SHORT).show();

                        }
                        db.close();

                    }

                });
                builder.show();
            }
        });


        return rootView;


    }
}
