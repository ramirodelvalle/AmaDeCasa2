package com.infinitycr.amadecasa;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSubirArticulo;
    Button btnVerArticulos;
    Button btnCrearUsuario;
    Button btnTalles;


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
    }


    public void onClick(View v) {
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
        }
    }


}
