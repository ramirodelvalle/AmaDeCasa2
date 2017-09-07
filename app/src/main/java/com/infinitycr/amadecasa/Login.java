package com.infinitycr.amadecasa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText etMail;
    EditText etPassWord;
    //CheckBox cbRecordarMail;
    Button btnIniciar;
    TextView tvRecuperarPassWord;
    Button btnCrearCuenta;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);
        btnCrearCuenta.setOnClickListener(this);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(this);
        tvRecuperarPassWord = (TextView) findViewById(R.id.recupera);
        tvRecuperarPassWord.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

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


                break;


        }

    }
}