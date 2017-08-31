package com.infinitycr.amadecasa;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class OpcArticulos extends AppCompatActivity implements View.OnClickListener {

    Spinner spMarca;
    Spinner spCategoria;
    Spinner spPrecio;
    RadioButton radioButtonM;
    RadioButton radioButtonF;
    CheckBox chkAmarillo;
    CheckBox chkRojo;
    CheckBox chkNaranja;
    CheckBox chkVerde;
    CheckBox chkAzul;
    CheckBox chkRosa;
    CheckBox chkNegro;
    CheckBox chkBlanco;
    CheckBox chkVioleta;
    Button btnFiltrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opc_articulos);

        asigObjXML();
        asigSpinners();
    }

    public void asigObjXML(){
        radioButtonM = (RadioButton) findViewById(R.id.radioButtonM);
        radioButtonF = (RadioButton) findViewById(R.id.radioButtonF);
        btnFiltrar = (Button) findViewById(R.id.btnFiltrar);
        btnFiltrar.setOnClickListener(this);
        radioButtonF.setChecked(true);
        spMarca = (Spinner) findViewById(R.id.spMarca);
        spCategoria = (Spinner) findViewById(R.id.spCategoria);
        spPrecio = (Spinner) findViewById(R.id.spPrecio);
    }

    public void asigSpinners(){
        //////////////////////////////////  SPINNER/DROP DOWN LIST
        List list = new ArrayList();
        list.add("-Vacio-");
        list.add("Pantalones");
        list.add("Remeras");
        list.add("Buzos");
        list.add("Boxer");
        list.add("Bombachas");
        list.add("Corpiños");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(arrayAdapter);
        //////////////////////////////////  SPINNER/DROP DOWN LIST
        List list2 = new ArrayList();
        list2.add("-Vacio-");
        list2.add("Avon");
        list2.add("Juana");
        list2.add("DC");
        list2.add("Nike");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(arrayAdapter2);
        //////////////////////////////////  SPINNER/DROP DOWN LIST
        List list3 = new ArrayList();
        list3.add("-Vacio-");
        list3.add("Mayor Precio");
        list3.add("Menor Precio");
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list3);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPrecio.setAdapter(arrayAdapter3);
    }

    public String whereOand(boolean bandera) {
        String where = " where ";
        String and = " and ";
        if (bandera)
            return and;
        else
            return where;
    }

    public void funFiltrar(SQLiteDatabase db){
        String consultaSQL = "select * from articulos";
        boolean where = false;
        String sinSeleccion = "-Vacio-";
        if (spCategoria.getSelectedItem().toString() != sinSeleccion)
        {
            consultaSQL  += whereOand(where);
            consultaSQL  += "categoria='" + spCategoria.getSelectedItem().toString() + "' ";
            where = true;
        }
        if (spMarca.getSelectedItem().toString() != sinSeleccion)
        {
            consultaSQL  += whereOand(where);
            consultaSQL  += "marca='" + spMarca.getSelectedItem().toString() + "' ";
            where = true;
        }
        if (spPrecio.getSelectedItem().toString() != sinSeleccion)
        {
            if (spPrecio.getSelectedItem().toString().contains("Mayor Precio"))
            consultaSQL  += " ORDER BY precio desc ";
            else {
                consultaSQL  += " ORDER BY precio asc ";
            }
            where = true;
        }
        if(!where){
            consultaSQL = "SELECT * FROM articulos";
        }
        Intent intent = new Intent(this,VerArticulos.class);   //busca la pantalla q va a abrir  //dar de alta en el manifest!
        intent.putExtra("consultaSQL",consultaSQL);    //pasa el dato como una cookie
        startActivity(intent);  //abre la pantalla
    }

    @Override
    public void onClick(View v) {
        BD bdArticulos = new BD(this, "BDArticulos", null, 1);
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnFiltrar:
                funFiltrar(db);
                break;
        }
    }
}