package com.infinitycr.amadecasa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.infinitycr.amadecasa.clases.BD;

import java.util.ArrayList;
import java.util.List;

public class OpcArticulos extends AppCompatActivity implements View.OnClickListener {

    Spinner spMarca;
    Spinner spCategoria;
    Spinner spSubCategoria;
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
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        final SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS categoriasArt");   //BORRO LAS TABLAS Y LAS VUELVO A CREAR
        db.execSQL(bdPaoPrendas.actualizarTablaCategoriasArt());
        db.execSQL("DROP TABLE IF EXISTS subCategoriasArt");
        db.execSQL(bdPaoPrendas.actualizarTablaSubCategoriasArt());
        cargarCategorias(db);
        cargarTalles(db);

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
        spMarca = (Spinner) findViewById(R.id.spMarca);
        List list2 = new ArrayList();
        list2.add("-Vacio-");
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

        //////////////////////////////////  SPINNER/DROP DOWN LIST
        List list3 = new ArrayList();
        list3.add("-Vacio-");
        list3.add("Mayor Precio");
        list3.add("Menor Precio");
        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list3);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPrecio.setAdapter(arrayAdapter3);
        //////////////////////////////////  SPINNER/DROP DOWN LIST
    }

    public void cargarCategorias(SQLiteDatabase db){
        Articulo categorias[] = new Articulo[23];
        int i = 0;
        categorias[i] = new Articulo();
        categorias[i].setNombrePrenda("bombachas");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("conjuntos");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("calzados");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("buzos");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("calzas");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("remeras");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("camisas");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("vestidos");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("mallas");
        categorias[i].setGenero("femenino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("boxers");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("remeras");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("medias");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("pantalones");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("pijamas");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("batas");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("calzado");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("camperas");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("buzos");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("remeras");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("calzas");
        categorias[i].setGenero("masculino");

        categorias[++i] = new Articulo();
        categorias[i].setNombrePrenda("shorts");
        categorias[i].setGenero("masculino");



        for (i=0; i<23; i++) {
            try {
                String consulta = "INSERT INTO categoriasArt (categoria,genero)"+
                        "VALUES('"+categorias[i].getNombrePrenda()+"'," +
                        "'"+categorias[i].getGenero()+"')";
                //INSERTAR UN REGISTRO
                db.execSQL(consulta);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        Articulo subCategorias[] = new Articulo[19];
        i = 0;
        subCategorias[i] = new Articulo();
        subCategorias[i].setNombrePrenda("bombacha");
        subCategorias[i].setCategoria("bombachas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("cola less");
        subCategorias[i].setCategoria("bombachas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("culotte");
        subCategorias[i].setCategoria("bombachas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("vedetina");
        subCategorias[i].setCategoria("bombachas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("mini short");
        subCategorias[i].setCategoria("bombachas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("deportivo");
        subCategorias[i].setCategoria("conjuntos");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("soft");
        subCategorias[i].setCategoria("conjuntos");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("disfraces");
        subCategorias[i].setCategoria("conjuntos");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pijamas");
        subCategorias[i].setCategoria("conjuntos");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("batas");
        subCategorias[i].setCategoria("conjuntos");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("pantuflas");
        subCategorias[i].setCategoria("calzado");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("chinelas");
        subCategorias[i].setCategoria("calzado");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("botitas");
        subCategorias[i].setCategoria("calzado");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("con cierre");
        subCategorias[i].setCategoria("buzos");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("sin cierre");
        subCategorias[i].setCategoria("buzos");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("camperas");
        subCategorias[i].setCategoria("camperas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("chupin");
        subCategorias[i].setCategoria("calzas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("recta");
        subCategorias[i].setCategoria("calzas");
        subCategorias[i].setGenero("femenino");

        subCategorias[++i] = new Articulo();
        subCategorias[i].setNombrePrenda("chupin");
        subCategorias[i].setCategoria("calzas");
        subCategorias[i].setGenero("femenino");

        int b = i;
        for (i=0; i<=b; i++) {
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

    public ArrayAdapter traerLista(SQLiteDatabase db){
        List array = new ArrayList();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, array);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cursor c;
        if(radioButtonM.isChecked()){
            c = db.rawQuery("SELECT * FROM categoriasArt WHERE genero='masculino' ORDER BY" +
                    " categoria ASC", null);
        }
        else {
            c = db.rawQuery("SELECT * FROM categoriasArt WHERE genero='femenino' ORDER BY" +
                    " categoria ASC", null);
        }
        int i = 0;
        array.add("-Vacio-");
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

    public void cargarTalles(SQLiteDatabase db) {
        Articulo categorias[] = new Articulo[23];
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
        }
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
        array.add("-Vacio-");
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
        if (!spCategoria.getSelectedItem().toString().equals(sinSeleccion))
        {
            consultaSQL  += whereOand(where);
            consultaSQL  += "categoria='" + spCategoria.getSelectedItem().toString() + "' ";
            where = true;
        }
        if (!spMarca.getSelectedItem().toString().equals(sinSeleccion))
        {
            consultaSQL  += whereOand(where);
            consultaSQL  += "marca='" + spMarca.getSelectedItem().toString() + "' ";
            where = true;
        }
        String genero;
        if (radioButtonF.isChecked()) {genero="femenino";} else {genero="masculino";}
        consultaSQL  += whereOand(where);
        consultaSQL  += "genero='" + genero + "' ";
        where = true;
        if (!spPrecio.getSelectedItem().toString().equals(sinSeleccion))
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
        BD bdArticulos = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnFiltrar:
                funFiltrar(db);
                break;
        }
    }
}