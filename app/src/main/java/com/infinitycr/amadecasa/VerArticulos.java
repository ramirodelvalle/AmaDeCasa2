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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_articulos);

        btnOpciones = (Button) findViewById(R.id.btnOpciones);
        btnOpciones.setOnClickListener(this);
        //CODIGO DE LA LISTA PARA LLENARLA  ----------------------------------------------------------------------
        final ListView lvArticulos = (ListView) findViewById(R.id.ContenlistView);
        BD bdArticulos = new BD(this, "BDArticulos", null, 1);  //CREO LA CONEXION A LA BD
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
            c = db.rawQuery("SELECT * FROM articulos", null);
        }
        //Nos aseguramos de que existe al menos un registro
        int i = 0;
        if (c.moveToFirst()) {
            articulo = new Articulo[c.getCount()]; //OBTENGO LA CANTIDAD DE REGISTROS Y SE LO ASIGNO AL VECTOR
            imagenes = new Bitmap[c.getCount()]; //VECTOR PARA LAS IMAGENES
            //Recorremos el cursor hasta que no haya mas registros
            do {
                articulo[i] = new Articulo();
                articulo[i].setCodArticulo(c.getString(0));
                articulo[i].setNombrePrenda(c.getString(1));
                articulo[i].setCategoria(c.getString(2));
                articulo[i].setDescripcion(c.getString(3));
                articulo[i].setPrecio(c.getString(4));
                articulo[i].setColores(c.getString(5));
                articulo[i].setGenero(c.getString(6));
                articulo[i].setMarca(c.getString(7));
                //imagenes[i] = cargarFoto(articulo[i].getCodArticulo());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpciones:
                Intent intent = new Intent(this, OpcArticulos.class);
                startActivity(intent);
                break;
        }
    }
}
