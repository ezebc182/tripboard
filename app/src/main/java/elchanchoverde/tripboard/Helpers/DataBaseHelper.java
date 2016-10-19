package elchanchoverde.tripboard.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Ezequiel on 17/10/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/elchanchoverde.tripboard/databases/";
    private static String DB_NAME = "bdtripboard.sqlite";
    private final Context mContext;
    private SQLiteDatabase mDatabase;

    public DataBaseHelper(Context context) {
        //contexto,nombre,factory,version
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    // Cuando teneés el archivo de la bd ya listo.
    public void CreateDataBase() throws IOException {
        boolean dbExists = CheckDatabase();
        SQLiteDatabase db_Read = null;
        if (dbExists) {

        } else {
            db_Read = this.getReadableDatabase();
            db_Read.close();
            try {
                CopyDataBase();

            } catch (IOException e) {
                throw new Error("Error copiando base de datos");
            }
        }
    }

    private void CopyDataBase() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = mInput.read(buffer)) != -1) {
            if (length > 0) {
                mOutput.write(buffer, 0, length);
            }
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public void OpenDataBase() throws SQLException {
        try {


            String mPath = DB_PATH + DB_NAME;
            mDatabase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);

        } catch (Exception e) {
            throw new Error("Problema al abri la BD.");
        }

    }

    public synchronized void Close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    public boolean CheckDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String mPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            File dbFile = new File(DB_PATH + DB_NAME);
            return dbFile.exists();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Algunos crean acá el código SQL de la BD.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Acá le pasas la base de datos.
        // El código de la actualización.
        try {
            CreateDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cursor FetchAllList() throws SQLException {
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM 'countries' ORDER BY 'name' DESC", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor FetchItemsList(String country) throws SQLException {
        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM '" + country + "' ORDER BY 'name' DESC", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
