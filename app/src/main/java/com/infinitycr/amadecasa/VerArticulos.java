package com.infinitycr.amadecasa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import com.infinitycr.amadecasa.clases.BD;
import java.io.FileInputStream;
import java.io.IOException;


public class VerArticulos extends AppCompatActivity implements View.OnClickListener{

    ListaAdapter adapter;
    Articulo[] articulo;
    String[] titulo;
    Bitmap[] imagenes;
    Button btnOpciones;
    Button btnMasculino;
    Button btnFemenino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_articulos);

        asigObjXML();
        //funFiltrar();
    }

    public void abrirActividadVistaDeArticulos(Articulo articulo){
        Intent intent = new Intent(this,VistaDeUnArticulo.class);   //busca la pantalla q va a abrir  //dar de alta en el manifest!
        intent.putExtra("articuloDeLista",articulo);    //pasa el dato como una cookie
        startActivity(intent);  //abre la pantalla
    }

    //TRAIGO LA RUTA DE LA FOTO
    public Bitmap cargarFoto(int codArt){
        Bitmap bitmap = null;
        try{
            FileInputStream fileInputStream =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/"+
                            codArt+".png");
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        }catch (IOException io){
            io.printStackTrace();
        }
        return bitmap;
    }

    public String traerConsultaSQL(){
        Intent intent = getIntent();    //creo el objeto intent
        Bundle bundle = intent.getExtras(); //trae el objeto con todas las cosas q le haya pasado desde la actividad anterior
        String consulta = "";
        if(bundle!=null) {   // se fija si esta vacio para q no de error
            //TRAIGO EL OBJETO Q PASE DESDE LA OTRA ACTIVITY
            consulta = (String) getIntent().getExtras().getSerializable("consultaSQL");

        }
        return consulta;
    }

    public void asigObjXML() {
        btnOpciones = (Button) findViewById(R.id.btnOpciones);
        btnOpciones.setOnClickListener(this);
        btnMasculino = (Button) findViewById(R.id.btnMasculino);
        btnMasculino.setOnClickListener(this);
        btnFemenino = (Button) findViewById(R.id.btnFemenino);
        btnFemenino.setOnClickListener(this);

        //CODIGO DE LA LISTA PARA LLENARLA  ----------------------------------------------------------------------
        final ListView lvArticulos = (ListView) findViewById(R.id.ContenlistView);
        BD bdArticulos = new BD(this, "BDPP", null, 1);  //CREO LA CONEXION A LA BD
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        String consultaSQL;
        Intent intent = getIntent();    //creo el objeto intent
        Bundle bundle = intent.getExtras(); //trae el objeto con todas las cosas q le haya pasado desde la actividad anterior
        Cursor c;
        if(bundle!=null) {   // se fija si esta vacio para q no de error
            //TRAIGO EL OBJETO Q PASE DESDE LA OTRA ACTIVITY
            consultaSQL = (String) getIntent().getExtras().getSerializable("consultaSQL");
            c = db.rawQuery(consultaSQL, null);
        }
        else {
            c = db.rawQuery("SELECT * FROM articulos WHERE genero='femenino'", null);
        }
        //Nos aseguramos de que existe al menos un registro
        int i = 0;
        if (c.moveToFirst()) {
            articulo = new Articulo[c.getCount()]; //OBTENGO LA CANTIDAD DE REGISTROS Y SE LO ASIGNO AL VECTOR
            imagenes = new Bitmap[c.getCount()]; //VECTOR PARA LAS IMAGENES
            //Recorremos el cursor hasta que no haya mas registros
            do {
                articulo[i] = new Articulo();
                articulo[i].setCodArticulo(c.getInt(0));
                articulo[i].setCodigo(c.getString(1));
                articulo[i].setNombrePrenda(c.getString(2));
                articulo[i].setCategoria(c.getString(3));
                articulo[i].setDescripcion(c.getString(4));
                articulo[i].setPrecio(c.getString(5));
                articulo[i].setColores(c.getString(6));
                articulo[i].setGenero(c.getString(7));
                articulo[i].setMarca(c.getString(8));
                imagenes[i] = cargarFoto(articulo[i].getCodArticulo());
                i++;
            } while(c.moveToNext());
        }
        final ListView lista = (ListView) findViewById(R.id.ContenlistView);


        //EL ADAPTADOR ADAPTA LOS DATOS PARA Q FUNCIONEN EN EL LISTVIEW
        adapter = new ListaAdapter(this,articulo,i,imagenes);
        lista.setAdapter(adapter);

        //CUANDO SE PRESIONA UN ELEMENTO DE LA LISTA
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                abrirActividadVistaDeArticulos(articulo[i]);
                //Toast.makeText(getApplicationContext(), "click sobre " + i, Toast.LENGTH_SHORT).show();
            }
        });

        //CUANDO SE MANTIENE PRESIONADO UN ELEMENTO DE LA LISTA
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), "click Largo " + i, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //CODIGO DE LA LISTA PARA LLENARLA  ----------------------------------------------------------------------
    }
/*
    public void funFiltrar(SQLiteDatabase db){

        Intent intent = getIntent();    //creo el objeto intent
        Bundle bundle = intent.getExtras(); //trae el objeto con todas las cosas q le haya pasado desde la actividad anterior
        Cursor c;
        String genero="";
        if(bundle!=null) {   // se fija si esta vacio para q no de error
            //TRAIGO EL OBJETO Q PASE DESDE LA OTRA ACTIVITY
            genero = (String) getIntent().getExtras().getSerializable("genero");
        }
        else {
            genero="femenino";
        }
        String consultaSQL = "select * from articulos where genero ='"+genero+"'";
    }
*/
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnOpciones:
                intent = new Intent(this, OpcArticulos.class);
                startActivity(intent);
                break;
            case R.id.btnMasculino:
                intent = new Intent(this,VerArticulos.class);   //busca la pantalla q va a abrir  //dar de alta en el manifest!
                intent.putExtra("consultaSQL","SELECT * FROM articulos WHERE genero='masculino'");    //pasa el dato como una cookie
                startActivity(intent);  //abre la pantalla
                break;
            case R.id.btnFemenino:
                //btnFemenino.setBackgroundTintList;
                intent = new Intent(this,VerArticulos.class);   //busca la pantalla q va a abrir  //dar de alta en el manifest!
                intent.putExtra("consultaSQL","SELECT * FROM articulos WHERE genero='femenino'");    //pasa el dato como una cookie
                startActivity(intent);  //abre la pantalla
                break;

        }
    }
}
