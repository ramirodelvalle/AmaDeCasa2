package com.infinitycr.amadecasa;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.infinitycr.amadecasa.clases.BD;
import com.infinitycr.amadecasa.clases.ManejadorBaseDeDatos;
import com.infinitycr.amadecasa.clases.ManejadorDeDatos;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static Context activity;
    Button btnSubirArticulo;
    Button btnVerArticulos;
    Button btnCrearUsuario;
    Button btnActualizarUsuario;
    Button btnCerrarSesion;
    TextView tvUsuario;
    Context context = this;

    ManejadorBaseDeDatos manejadorBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubirArticulo = (Button) findViewById(R.id.btnSubirArticulo);
        btnSubirArticulo.setOnClickListener(this);
        btnVerArticulos = (Button) findViewById(R.id.btnVerArticulos);
        btnVerArticulos.setOnClickListener(this);
        btnCerrarSesion = (Button) findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(this);
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        btnActualizarUsuario = (Button) findViewById(R.id.btnActualizarUsuario);
        btnActualizarUsuario.setOnClickListener(this);

        activity = this;

        cargarUsuario();
        funCargarMarcas();
    }

    public void funCargarMarcas() {//SI YA SE INSTALO NO ENTRA EN ESTA FUNCION
        SharedPreferences preferences = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        String instalo = preferences.getString("instalo","no");
        if(!preferences.getString("instalo","no").contains("si")){
            manejadorBD = ManejadorBaseDeDatos.instance();
            Cursor cursor = manejadorBD.select("SELECT * FROM articulos");
            String cod;
            String marca;
            String precio;

            String[] marcas = new String[500];

            BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
            SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS marcasArt");
            db.execSQL(bdPaoPrendas.actualizarTablaMarcasArt());
            db.execSQL("DROP TABLE IF EXISTS articulos");
            db.execSQL(bdPaoPrendas.actualizarTablaArt());
            for (int i=0; i<500; i++) {
                marcas[i] = "";
            }
            int pos=0;
            while (cursor.moveToNext()) {
                cod = cursor.getString(0);
                marca = cursor.getString(1);
                precio = cursor.getString(2);
                try {
                    String consulta = "INSERT INTO articulos (codArticulo,precio,marca)"+
                            "VALUES('"+cod+"','"+precio+"','"+marca+"')";
                    //INSERTAR UN REGISTRO
                    db.execSQL(consulta);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                if(marca.contains("ZAILU MALLAS")){
                    String asd = "0";
                }
                boolean esta=false;
                for (int i=0; i<500; i++) {
                    if(marcas[i].contains(marca)){
                        esta = true;
                    }
                    if(i==499 && esta==false){
                        marcas[pos] = marca;
                        funAgregarCat(db,marca);
                        esta = true;
                        pos++;
                    }
                }
            }
            cursor.close();
            SharedPreferences preferences2 = getSharedPreferences("sesion",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString("instalo","si");
            editor.apply();
        }

    }

    public void funAgregarCat(SQLiteDatabase db,String marca){
        try {
            String consulta = "INSERT INTO marcasArt(nombreMarca)"+
                    "VALUES('"+marca+"')";
            //INSERTAR UN REGISTRO
            db.execSQL(consulta);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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
        Intent intent;
        switch (v.getId()){
            case R.id.btnSubirArticulo:
                intent = new Intent(this,SubirArticulo.class);   //busca la pantalla q va a abrir
                startActivity(intent);  //ABRE LA ACTIVITY
                break;
            case R.id.btnVerArticulos:
                intent = new Intent(this,VerArticulos.class);   //busca la pantalla q va a abrir
                startActivity(intent);  //ABRE LA ACTIVITY
                break;
            case R.id.btnCerrarSesion:
                cerrarSesion();
                break;
            case R.id.btnActualizarUsuario:
                intent = new Intent(this,ModificarUsuario.class);   //busca la pantalla q va a abrir
                startActivity(intent);  //ABRE LA ACTIVITY
                break;
        }
    }


}
