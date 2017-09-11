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

        BD bdPaoPrendas = new BD(this, "BDPaoPrendas", null, 1);
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
        etMail.setText(preferences.getString("mail","no tenia datos cargados"));
        etPassword.setText(preferences.getString("pass","no tenia datos cargados"));
        if(!etMail.getText().toString().isEmpty()){
            ingresar(db);
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

    public void ingresar(SQLiteDatabase db){
        Cursor c = db.rawQuery("SELECT * FROM usuarios", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String mail = c.getString(0);
                String nombre = c.getString(1);
                String pass = c.getString(2);
                if(mail.equals(etMail.getText().toString()) &&
                        pass.equals(etPassword.getText().toString())){
                    dejarSesionActiva(nombre);
                    c.close();
                    Intent intent3 = new Intent(this, MainActivity.class);
                    startActivity(intent3);
                    return;
                }
            } while(c.moveToNext());
        }
        Toast.makeText(this,"Mail o contraseña erroneas",Toast.LENGTH_SHORT).show();
        c.close();
    }

    @Override
    public void onClick(View v) {
        BD bdPaoPrendas = new BD(this, "BDPaoPrendas", null, 1);
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
                ingresar(db);
                break;
            case R.id.btnIrAMain:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;


        }

    }
}