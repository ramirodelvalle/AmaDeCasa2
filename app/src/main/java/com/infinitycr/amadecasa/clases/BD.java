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

    String SQL_CREAR_TABLA_ARTICULOS = "CREATE TABLE articulos (" +
                                        "codTabla INTEGER PRIMARY KEY AUTOINCREMENT, codArticulo TEXT," +
                                        "categoria TEXT, subCategoria TEXT, " +
                                        "precio TEXT," +
                                        "colores TEXT, genero TEXT,marca TEXT)";

    String SQL_CREAR_TABLA_USUARIOS = "CREATE TABLE usuarios (mailUsuario TEXT PRIMARY KEY,nombre TEXT,passWord TEXT," +
                                      "rangoEdad TEXT, numeroTelefono TEXT, direccion TEXT, localidad TEXT, cp INT, genero TEXT, coloresFavoritos TEXT) ";

    String SQL_CREAR_TABLA_CATEGORIAS_ARTICULOS = "CREATE TABLE categoriasArt(" +
                                                  "categoria TEXT PRIMARY KEY)";

    String SQL_CREAR_TABLA_SUB_CATEGORIAS_ARTICULOS = "CREATE TABLE subCategoriasArt(" +
                                                      "subCategoria TEXT PRIMARY KEY,categoria TEXT, genero TEXT)";

    String SQL_CREAR_TABLA_MARCAS_ARTICULOS = "CREATE TABLE marcasArt(" +
                                              "codMarca INTEGER PRIMARY KEY AUTOINCREMENT," +
                                              "nombreMarca TEXT)";

    String SQL_CREAR_TABLA_TALLES_ARTICULOS = "CREATE TABLE tallesArt(" +
                                                  "subCategoria TEXT PRIMARY KEY,categoria TEXT, genero TEXT)";

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
        db.execSQL("DROP TABLE IF EXISTS subcategoriasArt");
        db.execSQL("DROP TABLE IF EXISTS marcasArt");
        //SE CREA LA NUEVA VERSION DE LA TABLA
        db.execSQL(SQL_CREAR_TABLA_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_USUARIOS);
        db.execSQL(SQL_CREAR_TABLA_CATEGORIAS_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_SUB_CATEGORIAS_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_MARCAS_ARTICULOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //SE ELIMINA LA VERSION ANTERIOR DE LA TABLA
        db.execSQL("DROP TABLE IF EXISTS articulos");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS categoriasArt");
        db.execSQL("DROP TABLE IF EXISTS subcategoriasArt");
        db.execSQL("DROP TABLE IF EXISTS marcasArt");
        //SE CREA LA NUEVA VERSION DE LA TABLA
        db.execSQL(SQL_CREAR_TABLA_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_USUARIOS);
        db.execSQL(SQL_CREAR_TABLA_CATEGORIAS_ARTICULOS);
        db.execSQL(SQL_CREAR_TABLA_SUB_CATEGORIAS_ARTICULOS);
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

    public String actualizarTablaSubCategoriasArt(){
        return SQL_CREAR_TABLA_SUB_CATEGORIAS_ARTICULOS;
    }

    public String actualizarTablaMarcasArt(){
        return SQL_CREAR_TABLA_MARCAS_ARTICULOS;
    }

    public String actualizarTablaTallesArt() {return SQL_CREAR_TABLA_TALLES_ARTICULOS;}
}
