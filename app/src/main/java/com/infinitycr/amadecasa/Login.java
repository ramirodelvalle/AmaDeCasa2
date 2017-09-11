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

        asigObjXML();
        cargarUsuario();
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

    public void cargarUsuario(){
        SharedPreferences preferences = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        etMail.setText(preferences.getString("mail","no tenia datos cargados"));
    }

    public void dejarSesionActiva(){
        SharedPreferences preferences = getSharedPreferences("sesion",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mail",etMail.getText().toString());
        editor.apply();
    }

    public boolean ingresar(SQLiteDatabase db,Context context){
        Cursor c = db.rawQuery("SELECT * FROM usuarios", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String mail = c.getString(0);
                String pass = c.getString(2);
                if(mail.equals(etMail.getText().toString()) &&
                        pass.equals(etPassword.getText().toString())){
                    dejarSesionActiva();
                    c.close();
                    return true;
                }
            } while(c.moveToNext());
        }
        Toast.makeText(this,"Mail o contraseña erroneas",Toast.LENGTH_SHORT).show();
        c.close();
        return false;
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
                if(ingresar(db,context)){
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