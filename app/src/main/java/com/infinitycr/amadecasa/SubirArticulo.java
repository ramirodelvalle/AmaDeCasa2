package com.infinitycr.amadecasa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
    Intent intentImagen; //INTENT PARA SACAR LA FOTO
    Bitmap bmp; //IMAGEN OBTENIDA DE LA CAMARA
    Button btnTomarFoto;
    Button btnCargarTabla;
    Button btnBorrarTabla;
    Spinner spCategoria;
    Button btnAgregarArticulo;
    Button btnLeerArticulo;
    EditText etNombrePrenda;
    EditText etDescripcionPrenda;
    EditText etPrecioPrenda;
    RadioButton radioButtonM;
    RadioButton radioButtonF;
    Spinner spMarca;
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

    public void asigObjXML(){
        ivFoto = (ImageView) findViewById(R.id.ivFotoPrenda);
        btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto);
        btnTomarFoto.setOnClickListener(this);
        btnAgregarArticulo = (Button) findViewById(R.id.btnAgregarArticulo);
        btnAgregarArticulo.setOnClickListener(this);
        btnLeerArticulo = (Button) findViewById(R.id.btnLeerArticulo);
        btnLeerArticulo.setOnClickListener(this);
        etNombrePrenda = (EditText) findViewById(R.id.etNombrePrenda);
        etDescripcionPrenda = (EditText) findViewById(R.id.etDescripcionPrenda);
        etPrecioPrenda = (EditText) findViewById(R.id.etPrecioPrenda);
        radioButtonM = (RadioButton) findViewById(R.id.radioButtonM);
        radioButtonF = (RadioButton) findViewById(R.id.radioButtonF);
        btnCargarTabla = (Button) findViewById(R.id.btnCargarTabla);
        btnCargarTabla.setOnClickListener(this);
        btnBorrarTabla = (Button) findViewById(R.id.btnBorrarTabla);
        btnBorrarTabla.setOnClickListener(this);
    }

    public void asigSpinners(){
        //////////////////////////////////  SPINNER/DROP DOWN LIST
        spCategoria = (Spinner) findViewById(R.id.spCategoria);
        List list = new ArrayList();
        list.add("Pantalones");
        list.add("Remeras");
        list.add("Buzos");
        list.add("Boxer");
        list.add("Bombachas");
        list.add("Corpiños");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(arrayAdapter);
        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }   //muestra el elemento seleccionado
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //////////////////////////////////  SPINNER/DROP DOWN LIST
        spMarca = (Spinner) findViewById(R.id.spMarca);
        List list2 = new ArrayList();
        list2.add("Avon");
        list2.add("Juana");
        list2.add("DC");
        list2.add("Nike");
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(arrayAdapter2);
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
        articulo.setNombrePrenda(etNombrePrenda.getText().toString());
        articulo.setCategoria(spCategoria.getSelectedItem().toString());
        articulo.setDescripcion(etDescripcionPrenda.getText().toString());
        articulo.setPrecio(Float.parseFloat(etPrecioPrenda.getText().toString()));
        articulo.setColores(getColores());
        articulo.setMarca(spMarca.getSelectedItem().toString());
        if (radioButtonF.isChecked()) {articulo.setGenero("femenino");} else {articulo.setGenero("masculino");}
        try {
            String consulta = "INSERT INTO articulos (nombre,categoria,descripcion,precio,colores,genero,marca)"+
                    "VALUES('"+articulo.getNombrePrenda()+"','"+articulo.getCategoria()+"','"+
                    articulo.getDescripcion()+ "','"+articulo.getPrecio()+"','" +
                    articulo.getColores()+"','"+articulo.getGenero()+"','"+articulo.getMarca()+"')";
            //INSERTAR UN REGISTRO
            db.execSQL(consulta);
            //Actualizar un registro
            //db.execSQL("UPDATE articulos SET nombre='tanga',descripcion='grande' WHERE codArticulo=1 ");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //LEO EL ARTICULO RECIEN CREADO PARA TRAER EL NUMERO DEL ARTICULO Y CREAR SU IMAGEN EN MEMORIA CON ESE NUMERO
        Cursor c = db.rawQuery("SELECT * FROM articulos WHERE nombre = '"+
                articulo.getNombrePrenda()+"'", null);
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
        db.close();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput(
                    codArt+".png", Context.MODE_PRIVATE);
            outputStream.write(byteArray);
            //articulo.setRutaImagen(outputStream.toString());
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
        art[0].setPrecio(23);
        art[0].setColores("Rojo");
        art[0].setGenero("masculino");
        art[0].setMarca("Avon");
        art[1] = new Articulo();
        art[1].setNombrePrenda("jean");
        art[1].setCategoria("Pantalones");
        art[1].setDescripcion("roto");
        art[1].setPrecio(9);
        art[1].setColores("Azul");
        art[1].setGenero("masculino");
        art[1].setMarca("DC");
        art[2] = new Articulo();
        art[2].setNombrePrenda("tanga");
        art[2].setCategoria("Bombachas");
        art[2].setDescripcion("roja M");
        art[2].setPrecio(12);
        art[2].setColores("Rojo");
        art[2].setGenero("femenino");
        art[2].setMarca("Avon");
        art[3] = new Articulo();
        art[3].setNombrePrenda("boxer");
        art[3].setCategoria("Boxer");
        art[3].setDescripcion("gris S");
        art[3].setPrecio(23);
        art[3].setColores("Negro");
        art[3].setGenero("masculino");
        art[3].setMarca("Nike");
        art[4] = new Articulo();
        art[4].setNombrePrenda("buzo");
        art[4].setCategoria("Buzos");
        art[4].setDescripcion("abrigado");
        art[4].setPrecio(6);
        art[4].setColores("Rojo");
        art[4].setGenero("femenino");
        art[4].setMarca("DC");
        art[5] = new Articulo();
        art[5].setNombrePrenda("corpiño");
        art[5].setCategoria("Corpiños");
        art[5].setDescripcion("negro M");
        art[5].setPrecio(45);
        art[5].setColores("Negro");
        art[5].setGenero("femenino");
        art[5].setMarca("DC");
        art[6] = new Articulo();
        art[6].setNombrePrenda("corpiño 2");
        art[6].setCategoria("Corpiños");
        art[6].setDescripcion("rojo");
        art[6].setPrecio(4);
        art[6].setColores("Rojo");
        art[6].setGenero("femenino");
        art[6].setMarca("Juana");
        for (int i=0; i<7; i++) {

            try {
                String consulta = "INSERT INTO articulos (nombre,categoria,descripcion,precio,colores,genero,marca)"+
                        "VALUES('"+art[i].getNombrePrenda()+"','"+art[i].getCategoria()+"','"+
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

    //VVVVVVVVVVVVVVVVVVVVV
    //VVVVVVVVVVVVVVVVVVVVV
    @Override
    public void onClick (View v){
        BD bdArticulos = new BD(this, "BDArticulos", null, 1);
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnTomarFoto:
                intentImagen = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //ABRE LA ACTIVIDAD DE LA CAMARA
                startActivityForResult(intentImagen, 0);
                break;

            case R.id.btnAgregarArticulo:
                boolean correcto = true;
                correcto = validarEditText(etNombrePrenda);
                correcto = validarEditText(etDescripcionPrenda);
                correcto = validarEditText(etPrecioPrenda);
                if(correcto){
                    try {
                        funAgregarArticulo(db);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btnLeerArticulo:
                Cursor c = db.rawQuery("SELECT codArticulo,nombre,descripcion FROM articulos", null);
                String codArticulo = "";
                String descripcion = "";
                String nombre = "";

                //Nos aseguramos de que existe al menos un registro
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        codArticulo = c.getString(0);
                        nombre = c.getString(1);
                        descripcion = c.getString(2);
                        Toast.makeText(this,"codArticulo: "+ codArticulo+ " nombre: "+ nombre + " descripcion: "+descripcion,
                                Toast.LENGTH_SHORT).show();
                    } while(c.moveToNext());
                }
                break;
            case R.id.btnBorrarTabla:
                db.execSQL("DROP TABLE IF EXISTS articulos");
                db.execSQL(bdArticulos.actualizarTablaArt());
                break;

            case R.id.btnCargarTabla:
                cargarTabla(db);
                break;

        }

    }



}