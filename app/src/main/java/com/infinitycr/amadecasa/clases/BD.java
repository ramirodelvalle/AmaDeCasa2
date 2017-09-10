package com.infinitycr.amadecasa.clases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.infinitycr.amadecasa.MainActivity;

import java.io.IOException;

/**
 * Created by ramir on 8/8/2017.
 */

public class BD extends SQLiteOpenHelper{

    String SQL_CREAR_TABLA_ARTICULOS = "CREATE TABLE articulos (codArticulo TEXT PRIMARY KEY," +
                                       "nombre TEXT, categoria TEXT, descripcion TEXT, precio TEXT," +
                                       "colores TEXT, genero TEXT,marca TEXT)";
    String SQL_CREAR_TABLA_USUARIOS = "CREATE TABLE usuarios (mailUsuario TEXT PRIMARY KEY,nombre TEXT,passWord TEXT," +
                                      "direccion TEXT, localidad TEXT, cp INT, genero TEXT, coloresFavoritos TEXT) ";

    public BD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //SI NO EXISTE LA BD LA CREA Y EJECUTA LOS SIG COMANDOS
        db.execSQL("DROP TABLE IF EXISTS articulos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL(SQL_CREAR_TABLA_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //SE ELIMINA LA VERSION ANTERIOR DE LA TABLA
        db.execSQL("DROP TABLE IF EXISTS articulos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");

        //SE CREA LA NUEVA VERSION DE LA TABLA
        db.execSQL(SQL_CREAR_TABLA_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_USUARIOS);
    }

    public String actualizarTablaArt(){
        return SQL_CREAR_TABLA_ARTICULOS;
    }

    public String actualizarTablaUsuarios(){
        return SQL_CREAR_TABLA_USUARIOS;
    }
}
