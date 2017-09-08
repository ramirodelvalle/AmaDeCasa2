package com.infinitycr.amadecasa.clases;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.infinitycr.amadecasa.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ramir on 7/9/2017.
 */

public class ManejadorBaseDeDatos extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/user/0/com.infinitycr.amadecasa/databases/BDPP.db";
    private static String DB_NAME = "BDPP.db";
    //////////
    private static SQLiteDatabase mDataBase;

    private static ManejadorBaseDeDatos sInstance = null;

    public ManejadorBaseDeDatos() {
        super(MainActivity.activity, DB_NAME, null, DATABASE_VERSION);

        try {
            createDataBase();
            openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ManejadorBaseDeDatos instance() {

        if (sInstance == null) {
            sInstance = new ManejadorBaseDeDatos();
        }
        return sInstance;
    }


    private void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        SQLiteDatabase db_Read = null;

        if (dbExist) {
            // la base de datos ya existe
        } else {

            db_Read = this.getReadableDatabase();
            db_Read.close();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copiando base de datos");
            }
        }
    }

    public boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void copyDataBase() throws IOException {

        InputStream myInput = MainActivity.activity.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private void openDataBase() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public Cursor select(String query) throws SQLException {
        return mDataBase.rawQuery(query, null);
    }



    @Override
    public synchronized void close() {

        if (mDataBase != null)
            mDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
