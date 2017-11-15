package com.infinitycr.amadecasa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.infinitycr.amadecasa.clases.BD;

import java.io.FileOutputStream;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText etMail;
    EditText etPassword;
    //CheckBox cbRecordarMail;
    Button btnIniciar;
    TextView tvRecuperarPassWord;
    Button btnCrearCuenta;
    Button btnIrAMain;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        asigObjXML();
        cargarUsuario(db);
    }

    public void asigObjXML(){
        etMail = (EditText) findViewById(R.id.etMail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);
        btnCrearCuenta.setOnClickListener(this);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(this);
        tvRecuperarPassWord = (TextView) findViewById(R.id.recupera);
        tvRecuperarPassWord.setOnClickListener(this);
        btnIrAMain = (Button) findViewById(R.id.btnIrAMain);
        btnIrAMain.setOnClickListener(this);
    }

    public void cargarUsuario(SQLiteDatabase db){
        SharedPreferences preferences = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        etMail.setText(preferences.getString("mail",""));
        etPassword.setText(preferences.getString("pass",""));
        if(!etMail.getText().toString().isEmpty()){
            if(ingresar(db)){
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
            }
        }
    }

    public void dejarSesionActiva(String nombre){
        SharedPreferences preferences = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mail",etMail.getText().toString());
        editor.putString("pass",etPassword.getText().toString());
        editor.putString("nombre",nombre);
        editor.apply();
    }

    public boolean ingresar(SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE mailUsuario = '"+etMail.getText()+"'", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String mail = c.getString(0);
                String nombre = c.getString(1);
                String pass = c.getString(2);
                //ACA NOSE Q PASAA
                if(mail.equals(etMail.getText().toString()) ){
                    dejarSesionActiva(nombre);
                    if(pass.equals(etPassword.getText().toString()) ){
                        c.close();
                        return true;
                    }
                }
            } while(c.moveToNext());
        }
        Toast.makeText(this,"Mail o contraseña erroneas",Toast.LENGTH_SHORT).show();
        c.close();
        return false;
    }

    @Override
    public void onBackPressed (){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Desea salir?")
                .setCancelable(false)
                .setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();   //CIERRA LA APP
                        //lo que quieras hacer cuando se da click en si
                        System.exit(0);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //lo que quieras hacer cuando se da click en no
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnCrearCuenta:
                Intent intent = new Intent(this, CrearUsuario.class);//busca la pantalla q va a abrir
                startActivity(intent);  //ABRE LA ACTIVITY
                break;
            case R.id.recupera:
                Intent intent1 = new Intent(this, RecuperaPassword.class);
                startActivity(intent1);
                break;
            case R.id.btnIniciar:
                if(ingresar(db)){
                    Intent intent3 = new Intent(this, MainActivity.class);
                    startActivity(intent3);
                }
                break;
            case R.id.btnIrAMain:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;

        }

    }
}