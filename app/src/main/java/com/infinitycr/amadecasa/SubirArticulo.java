package com.infinitycr.amadecasa;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.infinitycr.amadecasa.clases.BD;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubirArticulo extends AppCompatActivity implements View.OnClickListener {

    Articulo articulo = new Articulo();
    ImageView ivFoto;
    Bitmap bmp; //IMAGEN OBTENIDA DE LA CAMARA
    Button btnTomarFoto;
    Button btnCargarTabla;
    Button btnBorrarTabla;
    Spinner spCategoria;
    Spinner spSubCategoria;
    Button btnAgregarArticulo;
    Button btnLeerArticulo;
    EditText etCodArticulo;
    EditText etNombrePrenda;
    EditText etDescripcionPrenda;
    EditText etPrecioPrenda;
    RadioButton radioButtonM;
    RadioButton radioButtonF;
    Button btnMasculino;
    Spinner spMarca;
    Spinner spTalles;
    CheckBox chkAmarillo;
    CheckBox chkRojo;
    CheckBox chkNaranja;
    CheckBox chkVerde;
    CheckBox chkAzul;
    CheckBox chkRosa;
    CheckBox chkNegro;
    CheckBox chkBlanco;
    CheckBox chkVioleta;

    static Context context;
    static String local_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_articulo);

        asigObjXML();
        asigSpinners();
        radioButtonF.setChecked(true);
    }

    public SQLiteDatabase traerBD(){
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        return db;
    }


    public void asigObjXML(){
        ivFoto = (ImageView) findViewById(R.id.ivFotoPrenda);
        btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto);
        btnTomarFoto.setOnClickListener(this);
        btnAgregarArticulo = (Button) findViewById(R.id.btnAgregarArticulo);
        btnAgregarArticulo.setOnClickListener(this);
        btnLeerArticulo = (Button) findViewById(R.id.btnLeerArticulo);
        btnLeerArticulo.setOnClickListener(this);
        etCodArticulo = (EditText) findViewById(R.id.etCodArticulo);
        etNombrePrenda = (EditText) findViewById(R.id.etNombrePrenda);
        //etDescripcionPrenda = (EditText) findViewById(R.id.etDescripcionPrenda);
        etPrecioPrenda = (EditText) findViewById(R.id.etPrecioPrenda);
        radioButtonM = (RadioButton) findViewById(R.id.radioButtonM);
        radioButtonM.setOnClickListener(this);
        radioButtonF = (RadioButton) findViewById(R.id.radioButtonF);
        radioButtonF.setOnClickListener(this);
        btnCargarTabla = (Button) findViewById(R.id.btnCargarTabla);
        btnCargarTabla.setOnClickListener(this);
        btnBorrarTabla = (Button) findViewById(R.id.btnBorrarTabla);
        btnBorrarTabla.setOnClickListener(this);
    }

    public ArrayAdapter traerLista(SQLiteDatabase db){
        List array = new ArrayList();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, array);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cursor c = db.rawQuery("SELECT * FROM categoriasArt ORDER BY categoria ASC", null);

        int i = 0;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                String cate = c.getString(0);
                array.add(cate);
                i++;
            } while(c.moveToNext());
        }
        //c.close();
        return arrayAdapter;
    }

    public ArrayAdapter traerLista(String categoria, SQLiteDatabase db){
        List array = new ArrayList();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, array);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cursor c;
        if(radioButtonM.isChecked()){
            c = db.rawQuery("SELECT * FROM subCategoriasArt WHERE genero='masculino' " +
                            "and categoria = '"+categoria+"' ORDER BY subCategoria ASC ", null);

        }
        else {
            c = db.rawQuery("SELECT * FROM subCategoriasArt WHERE genero='femenino' " +
                            "and categoria = '"+categoria+"' ORDER BY subCategoria ASC", null);
        }
        int i = 0;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya mas registros
            do {
                String cate = c.getString(0);
                array.add(cate);
                i++;
            } while(c.moveToNext());
        }
        //c.close();
        return arrayAdapter;
    }

    public void asigSpinners(){
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        final SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS categoriasArt");   //BORRO LAS TABLAS Y LAS VUELVO A CREAR
        db.execSQL(bdPaoPrendas.actualizarTablaCategoriasArt());
        db.execSQL("DROP TABLE IF EXISTS subCategoriasArt");
        db.execSQL(bdPaoPrendas.actualizarTablaSubCategoriasArt());
        cargarCategorias(db);
        //cargarTalles(db);

        //////////////////////////////////  SPINNER CATEGORIAS
        spCategoria = (Spinner) findViewById(R.id.spCategoria);
        spCategoria.setAdapter(traerLista(db));
        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spSubCategoria.setAdapter(traerLista(spCategoria.getSelectedItem().toString(),db));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //////////////////////////////////  SPINNER/DROP DOWN LIST
        spSubCategoria = (Spinner) findViewById(R.id.spSubCategoria);
        spSubCategoria.setAdapter(traerLista(spCategoria.getSelectedItem().toString(),db));
        spSubCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SubirArticulo.this, "asdasd", Toast.LENGTH_SHORT).show();
            }   //muestra el elemento seleccionado
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //////////////////////////////////  SPINNER/DROP DOWN LIST
        spTalles = (Spinner) findViewById(R.id.spTalles);
        List listTalles = new ArrayList();

        listTalles.add("1 al 4");
        listTalles.add("1 al 5");
        listTalles.add("1 al 6");
        listTalles.add("5 y 6");
        //listTalles.add("");
        //listTalles.add("");
        ArrayAdapter arrayAdapterTalles = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listTalles); //el 3 parametro es la lista
        arrayAdapterTalles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTalles.setAdapter(arrayAdapterTalles);
        spTalles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }   //muestra el elemento seleccionado
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/*
        //////////////////////////////////  SPINNER/DROP DOWN LIST
        spMarca = (Spinner) findViewById(R.id.spMarca);
        List list2 = new ArrayList();
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        //bdPaoPrendas.onUpgrade(db,1,1);
        //db.execSQL(bdPaoPrendas.actualizarTablaCategoriasArt());
        //db.execSQL(bdPaoPrendas.actualizarTablaCategoriasArt());
        Cursor c = db.rawQuery("SELECT * FROM marcasArt ORDER BY nombreMarca asc", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String marca = c.getString(1);
                list2.add(marca);
            } while(c.moveToNext());
        }
        c.close();
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(arrayAdapter2);
*/
    }

    public void cargarCategorias(SQLiteDatabase db){
        Articulo categorias[] = new Articulo[50];
        int i = 0;
        categorias[i] = new Articulo();
        categorias[i].setNombrePrenda("corseteria");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("hombres");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("lenceria");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("mallas");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("moda");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("niños");

        int c = i;
        for (i=0; i<=c; i++) {
            try {
                String consulta = "INSERT INTO categoriasArt (categoria)"+
                        "VALUES('"+categorias[i].getNombrePrenda()+"')";
                //INSERTAR UN REGISTRO
                db.execSQL(consulta);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        Articulo subCategorias[] = new Articulo[100];
        i = 0;
        subCategorias[i] = new Articulo();
        subCategorias[i].setNombrePrenda("tasa soft");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("bombachas");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("otros");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("medias");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("sin aro");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("deportivo");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("erotico");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("modelante");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("niños");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("con aro");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("accesorios");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camisetas");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("lenceria");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("moda");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("push up");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("amamantar");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("strapless");
        subCategorias[i].setCategoria("corseteria");
        subCategorias[i].setGenero("femenino");


        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pijama algodon");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camison algodon");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camisolin");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camison futura mama");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pantuflas");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("batas");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("baby doll");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("hombres");
        subCategorias[i].setCategoria("lenceria");
        subCategorias[i].setGenero("femenino");


        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("triangulo/ marilyn");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("entera comun");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("bando");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("piezas inferiores");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("tasa soft");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("tankini");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("entera t. grandes");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("niños");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("push up/ con aro");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("bikini talles grandes");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("ojotas");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("bikini nena");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("enteriza nena");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("con short");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("entera strapless");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("hombres");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("entera deportiva");
        subCategorias[i].setCategoria("mallas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("remera");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("calza");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("corseteria");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("short dama");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pantalones");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pescadora");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("campera");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("vestido");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camisa");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("ciclista");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("accesorios");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("babucha");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("buzo");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("conjunto");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("hombres");
        subCategorias[i].setCategoria("moda");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("bebe");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("medias niño");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pijamas nena");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("juv. nena");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("slip y boxer niño");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("corseteria");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("malla bikini");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("bombacha nena");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("short de baño");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("mallas");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pijamas varon");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("malla enteriza");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("hombres");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("malla bebe");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pantuflas");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("remeras");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("buzo");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camison");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("accesorios");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("batas");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("campera colegial");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("ojotas");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pantalon");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("short");
        subCategorias[i].setCategoria("niños");
        subCategorias[i].setGenero("femenino");


        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("boxer");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("medias hombre");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("remeras");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camiseta hombre");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("short baño");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("slip");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("bermuda");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pijamas");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pantalon");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pantuflas de hombre");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("niños");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("ojotas");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("mallas");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("lenceria");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("moda");
        subCategorias[i].setCategoria("hombres");
        subCategorias[i].setGenero("masculino");

        c = i;
        for (i=0; i<=c; i++) {
            try {
                String consulta = "INSERT INTO subCategoriasArt (subCategoria,categoria,genero)"+
                        "VALUES('"+subCategorias[i].getNombrePrenda()+"','" +
                                subCategorias[i].getCategoria()+"','" +
                                subCategorias[i].getGenero()+"')";
                //INSERTAR UN REGISTRO
                db.execSQL(consulta);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //db.close();
    }

    public void cargarTalles(SQLiteDatabase db) {
        /*Articulo categorias[] = new Articulo[23];
        int i = 0;
        categorias[i] = new Articulo();
        categorias[i].setCategoria("conjuntos");
        categorias[i].setNombrePrenda("camisones");
        categorias[i].setGenero("femenino");
        categorias[i].setDescripcion("1 y 2");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("conjuntos");
        categorias[i].setGenero("femenino");

        for (i = 0; i < 23; i++) {
            try {
                String consulta = "INSERT INTO categoriasArt (categoria,genero)" +
                        "VALUES('" + categorias[i].getNombrePrenda() + "'," +
                        "'" + categorias[i].getGenero() + "')";
                //INSERTAR UN REGISTRO
                db.execSQL(consulta);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datos) {
        super.onActivityResult(requestCode, requestCode, datos);
        if (resultCode == Activity.RESULT_OK) {   //SI LA ACTIVIDAD DE LA CAMARA SE COMPLETO CORRECTAMENTE
            Bundle extras = datos.getExtras();  //OBTENGO LOS DATOS(FOTO)
            bmp = (Bitmap) extras.get("data");  //Y LA GUARDO EN UN OBJETO DE TIPO BITMAP
            ivFoto.setImageBitmap(bmp);    //Y ASIGNO LA IMAGEN AL IMAGEVIEW
        }
    }

    //TRAIGO LOS COLORES SELECCIONADOS
    public String getColores(){
        String colores = "";
        CheckBox[] vChk = new CheckBox[9];
        vChk[0] = (CheckBox) findViewById(R.id.chkAmarillo);
        vChk[1] = (CheckBox) findViewById(R.id.chkRojo);
        vChk[2] = (CheckBox) findViewById(R.id.chkNaranja);
        vChk[3] = (CheckBox) findViewById(R.id.chkVerde);
        vChk[4] = (CheckBox) findViewById(R.id.chkAzul);
        vChk[5] = (CheckBox) findViewById(R.id.chkRosa);
        vChk[6] = (CheckBox) findViewById(R.id.chkNegro);
        vChk[7] = (CheckBox) findViewById(R.id.chkBlanco);
        vChk[8] = (CheckBox) findViewById(R.id.chkVioleta);
        for(int i=0; i<9; i++){
            if(vChk[i].isChecked()){
                colores += vChk[i].getText()+",";
            }
        }
        return colores;
    }

    public void funAgregarArticulo(SQLiteDatabase db){
        //articulo.setCodigo(etCodigo.getText().toString());
        articulo.setCodArticulo(etCodArticulo.getText().toString());
        articulo.setCategoria(spCategoria.getSelectedItem().toString());
        articulo.setSubCategoria(spSubCategoria.getSelectedItem().toString());
        //articulo.setPrecio(etPrecioPrenda.getText().toString());
        articulo.setColores(getColores());
        //articulo.setMarca(spMarca.getSelectedItem().toString());
        if (radioButtonF.isChecked()) {articulo.setGenero("femenino");} else {articulo.setGenero("masculino");}
        try {/*
            String consulta = "INSERT INTO articulos (codArticulo,categoria,subCategoria," +
                        "colores,genero)"+
                        "VALUES('"+articulo.getCodArticulo()+"','"+articulo.getNombrePrenda()+"','"+
                        articulo.getCategoria()+"','"+articulo.getCategoria()+"','"+
                        articulo.getColores()+"','"+articulo.getGenero()+"')";*/
            //INSERTAR UN REGISTRO
            //db.execSQL(consulta);
            //Actualizar un registro
            String consulta = "UPDATE articulos SET " +
                    "categoria='"+articulo.getCategoria()+"'," +
                    "subCategoria='"+articulo.getSubCategoria()+"'," +
                    "colores='"+articulo.getColores()+"'," +
                    "genero='"+articulo.getGenero()+"' " +
                    "WHERE codArticulo='\"" + articulo.getCodArticulo() + "\"'";
            db.execSQL(consulta);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /*
        //LEO EL ARTICULO RECIEN CREADO PARA TRAER EL NUMERO DEL ARTICULO Y CREAR SU IMAGEN EN MEMORIA CON ESE NUMERO
        Cursor c = db.rawQuery("SELECT * " +
                "FROM articulos WHERE codArticulo=\"" + articulo.getCodArticulo() + "\"", null);
        //Nos aseguramos de que existe al menos un registro
        int i = 0;
        int codArt = 0;
        if (c.moveToFirst()) {
            Articulo[] articuloV = new Articulo[c.getCount()]; //OBTENGO LA CANTIDAD DE REGISTROS Y SE LO ASIGNO AL VECTOR
            //Recorremos el cursor hasta que no haya más registros
            do {
                codArt = c.getInt(0);
                i++;
            } while(c.moveToNext());
        }
        */
        db.close();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput(
                    "\""+articulo.getCodArticulo()+"\""+".png", Context.MODE_PRIVATE);
            outputStream.write(byteArray);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public boolean validarEditText(EditText et){
        if(et.length()==0){
            et.setError("Complete este campo");
            return false;
        }
        else{
            return true;
        }
    }

    public void cargarTabla(SQLiteDatabase db){
        Articulo art[] = new Articulo[7];
        art[0] = new Articulo();
        art[0].setNombrePrenda("remera roja");
        art[0].setCategoria("Remeras");
        art[0].setDescripcion("grande");
        art[0].setPrecio("23");
        art[0].setColores("Rojo");
        art[0].setGenero("masculino");
        art[0].setMarca("ACROBATA");
        art[1] = new Articulo();
        art[1].setNombrePrenda("jean");
        art[1].setCategoria("Pantalones");
        art[1].setDescripcion("roto");
        art[1].setPrecio("9");
        art[1].setColores("Azul");
        art[1].setGenero("masculino");
        art[1].setMarca("AILIN");
        art[2] = new Articulo();
        art[2].setNombrePrenda("tanga");
        art[2].setCategoria("Bombachas");
        art[2].setDescripcion("roja M");
        art[2].setPrecio("12");
        art[2].setColores("Rojo");
        art[2].setGenero("femenino");
        art[2].setMarca("DIME QUIEN ERES");
        art[3] = new Articulo();
        art[3].setNombrePrenda("boxer");
        art[3].setCategoria("Boxer");
        art[3].setDescripcion("gris S");
        art[3].setPrecio("25");
        art[3].setColores("Negro");
        art[3].setGenero("masculino");
        art[3].setMarca("DIN DAN");
        art[4] = new Articulo();
        art[4].setNombrePrenda("buzo");
        art[4].setCategoria("Buzos");
        art[4].setDescripcion("abrigado");
        art[4].setPrecio("84");
        art[4].setColores("Rojo");
        art[4].setGenero("femenino");
        art[4].setMarca("DUFOUR");
        art[5] = new Articulo();
        art[5].setNombrePrenda("corpiño");
        art[5].setCategoria("Corpiños");
        art[5].setDescripcion("negro M");
        art[5].setPrecio("78");
        art[5].setColores("Negro");
        art[5].setGenero("femenino");
        art[5].setMarca("JE TAIME");
        art[6] = new Articulo();
        art[6].setNombrePrenda("corpiño 2");
        art[6].setCategoria("Corpiños");
        art[6].setDescripcion("rojo");
        art[6].setPrecio("65");
        art[6].setColores("Rojo");
        art[6].setGenero("femenino");
        art[6].setMarca("ACROBATA");
        for (int i=0; i<7; i++) {
            try {
                String consulta = "INSERT INTO articulos (codigo,nombre,categoria,descripcion,precio,colores,genero,marca)"+
                        "VALUES('"+(i+100)+"','"+art[i].getNombrePrenda()+"','"+art[i].getCategoria()+"','"+
                        art[i].getDescripcion()+ "','"+art[i].getPrecio()+"','" +
                        art[i].getColores()+"','"+art[i].getGenero()+"','"+art[i].getMarca()+"')";
                //INSERTAR UN REGISTRO
                db.execSQL(consulta);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        db.close();

    }



    @Override
    public void onClick (View v){
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        Intent intent;
        switch (v.getId()) {
            case R.id.btnTomarFoto:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //ABRE LA ACTIVIDAD DE LA CAMARA
                startActivityForResult(intent, 0);
                break;

            case R.id.btnAgregarArticulo:
                boolean correcto = true;
                /*correcto = validarEditText(etNombrePrenda);
                correcto = validarEditText(etDescripcionPrenda);
                correcto = validarEditText(etPrecioPrenda);
                */
                if(correcto){
                    try {
                        funAgregarArticulo(db);
                        intent = new Intent(this,MainActivity.class);   //busca la pantalla q va a abrir
                        startActivity(intent);  //ABRE LA ACTIVITY
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btnLeerArticulo:
                Cursor c = db.rawQuery("SELECT * FROM articulos", null);
                int codArticulo;
                String codigo = "";
                String nombre = "";

                //Nos aseguramos de que existe al menos un registro
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        codArticulo = c.getInt(0);
                        codigo = c.getString(1);
                        nombre = c.getString(2);
                        Toast.makeText(this,"codArticulo: "+codArticulo+ " codigo: "+codigo+ " nombre: "+nombre,
                                Toast.LENGTH_SHORT).show();
                    } while(c.moveToNext());
                }
                break;
            case R.id.btnBorrarTabla:
                db.execSQL("DROP TABLE IF EXISTS articulos");
                db.execSQL("DROP TABLE IF EXISTS categoriasArt");
                db.execSQL("DROP TABLE IF EXISTS subCategoriasArt");
                db.execSQL(bdPaoPrendas.actualizarTablaArt());
                db.execSQL(bdPaoPrendas.actualizarTablaCategoriasArt());
                db.execSQL(bdPaoPrendas.actualizarTablaSubCategoriasArt());
                break;

            case R.id.btnCargarTabla:
                cargarCategorias(db);
                break;

            case R.id.radioButtonM:
                asigSpinners();
                break;

            case R.id.radioButtonF:
                asigSpinners();
                break;
        }

    }



}