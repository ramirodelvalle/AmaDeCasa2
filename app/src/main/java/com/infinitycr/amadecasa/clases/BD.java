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

    String SQL_CREAR_TABLA_ARTICULOS = "CREATE TABLE articulos (codArt INTEGER PRIMARY KEY AUTOINCREMENT, codigo TEXT," +
                                       "nombre TEXT, categoria TEXT, descripcion TEXT, precio TEXT," +
                                       "colores TEXT, genero TEXT,marca TEXT)";
    String SQL_CREAR_TABLA_USUARIOS = "CREATE TABLE usuarios (mailUsuario TEXT PRIMARY KEY,nombre TEXT,passWord TEXT," +
                                      "rangoEdad TEXT, numeroTelefono TEXT, direccion TEXT, localidad TEXT, cp INT, genero TEXT, coloresFavoritos TEXT) ";
    String SQL_CREAR_TABLA_CATEGORIAS_ARTICULOS = "CREATE TABLE categoriasArt(" +
                                                  "codCategoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                  "categoria TEXT)";
    String SQL_CREAR_TABLA_MARCAS_ARTICULOS = "CREATE TABLE marcasArt(" +
                                              "codMarca INTEGER PRIMARY KEY AUTOINCREMENT," +
                                              "marca TEXT)";
    String SQL_CREAR_TABLA_COMPRAS_POR_USUARIOS = "CREATE TABLE comprasXusuario (";

    public BD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SI NO EXISTE LA BD LA CREA Y EJECUTA LOS SIG COMANDOS
        db.execSQL("DROP TABLE IF EXISTS articulos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS categoriasArt");
        db.execSQL(SQL_CREAR_TABLA_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_USUARIOS);
        db.execSQL(SQL_CREAR_TABLA_CATEGORIAS_ARTICULOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //SE ELIMINA LA VERSION ANTERIOR DE LA TABLA
        db.execSQL("DROP TABLE IF EXISTS articulos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS categoriasArt");
        //SE CREA LA NUEVA VERSION DE LA TABLA
        db.execSQL(SQL_CREAR_TABLA_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_USUARIOS);
        db.execSQL(SQL_CREAR_TABLA_CATEGORIAS_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_MARCAS_ARTICULOS);
    }

    public String actualizarTablaArt(){
        return SQL_CREAR_TABLA_ARTICULOS;
    }

    public String actualizarTablaUsuarios(){
        return SQL_CREAR_TABLA_USUARIOS;
    }

    public String actualizarTablaCategoriasArt(){
        return SQL_CREAR_TABLA_CATEGORIAS_ARTICULOS;
    }

    public String actualizarTablaMarcasArt(){
        return SQL_CREAR_TABLA_MARCAS_ARTICULOS;
    }
}
