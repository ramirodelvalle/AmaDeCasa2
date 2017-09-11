package com.infinitycr.amadecasa;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.infinitycr.amadecasa.clases.BD;
import com.infinitycr.amadecasa.clases.ManejadorBaseDeDatos;
import com.infinitycr.amadecasa.clases.ManejadorDeDatos;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Context activity;
    Button btnSubirArticulo;
    Button btnVerArticulos;
    Button btnCrearUsuario;
    Button btnTalles;
    Button btnToast;
    Button btnCerrarSesion;
    TextView tvUsuario;

    ManejadorBaseDeDatos manejadorBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCrearUsuario = (Button) findViewById(R.id.btnCrearCuenta);
        btnCrearUsuario.setOnClickListener(this);
        btnSubirArticulo = (Button) findViewById(R.id.btnSubirArticulo);
        btnSubirArticulo.setOnClickListener(this);
        btnVerArticulos = (Button) findViewById(R.id.btnVerArticulos);
        btnVerArticulos.setOnClickListener(this);
        btnTalles = (Button) findViewById(R.id.btnTalles);
        btnTalles.setOnClickListener(this);
        btnToast = (Button) findViewById(R.id.btnToast);
        btnToast.setOnClickListener(this);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(this);
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);

        activity = this;
        manejadorBD = ManejadorBaseDeDatos.instance();

        Cursor cursor = manejadorBD.select("SELECT * FROM articulos");

        String cod;
        String marca;
        String precio;
        cargarUsuario();
/*
        while (cursor.moveToNext()) {
            cod = cursor.getString(0);
            marca = cursor.getString(1);
            precio = cursor.getString(2);
            funAgregarArticulo(cod,marca,precio);
        }
        cursor.close();
*/

    }

    public void funAgregarArticulo(String cod,String marca,String precio) {
        BD bdArticulos = new BD(this, "BDArticulos", null, 1);
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        try {
            String consulta = "INSERT INTO articulos (codArticulo,marca,precio)" +
                    "VALUES('" + cod + "','" + marca + "','" + precio + "')";
            //INSERTAR UN REGISTRO
            db.execSQL(consulta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    public void cargarUsuario(){
        SharedPreferences preferences = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        tvUsuario.setText(preferences.getString("nombre","no tenia datos cargados"));
    }

    public void cerrarSesion(){
        SharedPreferences preferences = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mail","");
        editor.putString("pass","");
        editor.apply();
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void onClick(View v) {
        BD bdArticulos = new BD(this, "BDArticulos", null, 1);
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        switch (v.getId()){
            case R.id.btnSubirArticulo:
                Intent intent = new Intent(this,SubirArticulo.class);   //busca la pantalla q va a abrir
                startActivity(intent);  //ABRE LA ACTIVITY
                break;
            case R.id.btnVerArticulos:
                Intent intent2 = new Intent(this,VerArticulos.class);   //busca la pantalla q va a abrir
                startActivity(intent2);  //ABRE LA ACTIVITY
                break;
            case R.id.btnCrearCuenta:
                Intent intent3 = new Intent(this,CrearUsuario.class);   //busca la pantalla q va a abrir
                startActivity(intent3);  //ABRE LA ACTIVITY
                break;
            case R.id.btnTalles:
                Intent intent4 = new Intent(this,VistaDeTalles.class);   //busca la pantalla q va a abrir
                startActivity(intent4);  //ABRE LA ACTIVITY
                break;
            case R.id.btnToast:

                break;
            case R.id.btnCerrarSesion:
                cerrarSesion();
                break;
        }
    }


}
