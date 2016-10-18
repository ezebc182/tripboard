package elchanchoverde.tripboard.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import elchanchoverde.tripboard.R;

/**
 * Created by Ezequiel on 17/10/2016.
 */
public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ViewHolder>{
    Context myContext;
    CursorAdapter cursorAdapter;
    //private String[] myData;


    public ListaAdapter(Context context,Cursor cursor)
    {
        cursorAdapter = new CursorAdapter(context,cursor,0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View retView = inflater.inflate(R.layout.country_item,parent,false);

                return retView;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView textView = (TextView) view.findViewById(R.id.title);
                TextView listaNombre = (TextView) view.findViewById(R.id.lista_nombre);

                textView.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                listaNombre.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));

            }
        };

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.textView.setText(myData[position]);
        cursorAdapter.getCursor().moveToPosition(position);
        cursorAdapter.bindView(holder.itemView,myContext,cursorAdapter.getCursor());
    }

    @Override
    public int getItemCount() {
        return cursorAdapter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView,listaNombre;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.title);
            listaNombre = (TextView) itemView.findViewById(R.id.lista_nombre);
        }
    }
}
