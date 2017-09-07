package com.infinitycr.amadecasa;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.infinitycr.amadecasa.clases.BD;

public class CrearUsuario extends AppCompatActivity implements View.OnClickListener{
    Button btnCrearUsuario;
    Button btnBorrarTabla;
    EditText etMail;
    EditText etNombre;
    EditText etEdad;
    EditText etDireccion;
    EditText etLocalidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        btnCrearUsuario = (Button) findViewById(R.id.btnCrearUsuario);
        btnCrearUsuario.setOnClickListener(this);
        btnBorrarTabla = (Button) findViewById(R.id.btnBorrarTabla);
        btnBorrarTabla.setOnClickListener(this);
        etMail = (EditText) findViewById(R.id.etMail);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etEdad = (EditText) findViewById(R.id.etEdad);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etLocalidad = (EditText) findViewById(R.id.etLocalidad);

        //ABRIMOS LA BD 'BDAlumnos' en modo escritura / sino la crea
        //BDAlumnos alumnos = new BDAlumnos(null);

        //LA ABRIMOS EN MODO ESCRITURA
        //SQLiteDatabase db = alumnos.getWritableDatabase();

        //COMPRUEBO QUE LA BD SE ABRE CORRECTAMENTE

        //BDAlumnos db = new BDAlumnos( getApplicationContext() );
        //int id = 2;
        //db.agregar("Ramiro");
        // db.agregar("chespi");
        //etNombre.setText(db.obtener(id));
        //System.out.println(id);
        //db.obtener(id);

    }

    public void onClick(View v) {
        BD bdArticulos = new BD(this, "BDArticulos", null, 1);
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        switch (v.getId()){
            case R.id.btnCrearUsuario:
                try {
                    //INSERTAR UN REGISTRO
                    db.execSQL("INSERT INTO usuarios (mailUsuario, nombre) " +
                               "VALUES('"+etMail.getText()+"','"+etNombre+"')");

                    //Actualizar un registro
                    //db.execSQL("UPDATE articulos SET nombre='tanga',descripcion='grande' WHERE codArticulo=1 ");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.btnBorrarTabla:
                db.execSQL("DROP TABLE IF EXISTS usuarios");
                String SQL_CREAR2 = "CREATE TABLE usuarios (mailUsuario TEXT PRIMARY KEY," +
                        "nombre TEXT, anioNacimiento INT, direccion TEXT, localidad TEXT," +
                        "genero TEXT, coloresFavoritos TEXT) ";
                db.execSQL(SQL_CREAR2);
                break;

        }
    }
}
