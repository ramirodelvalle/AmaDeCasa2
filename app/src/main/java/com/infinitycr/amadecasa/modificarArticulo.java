package com.infinitycr.amadecasa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import com.infinitycr.amadecasa.clases.BD;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class modificarArticulo extends AppCompatActivity implements View.OnClickListener{

    EditText etCodigo;
    EditText etNombrePrenda;
    EditText etDescripcionPrenda;
    EditText etPrecioPrenda;
    Articulo articulo;
    ImageView ivFotoPrenda;
    Intent intentImagen; //INTENT PARA SACAR LA FOTO
    Bitmap bmp; //IMAGEN OBTENIDA DE LA CAMARA
    Button btnTomarFoto;
    Button btnActualizarArt;
    Spinner spCategoria;
    CheckBox chkAmarillo;
    CheckBox chkRojo;
    CheckBox chkNaranja;
    CheckBox chkVerde;
    CheckBox chkAzul;
    CheckBox chkRosa;
    CheckBox chkNegro;
    CheckBox chkBlanco;
    CheckBox chkVioleta;
    RadioButton radioButtonF;
    RadioButton radioButtonM;
    List list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_articulo);

        asigObjXML();
        asigSpinner();
        traerArt();//TRAIGO EL ARTICULO DE LA ACTIVITY ANTERIOR
    }

    public void asigObjXML(){
        btnActualizarArt = (Button) findViewById(R.id.btnActualizarArt);
        btnActualizarArt.setOnClickListener(this);
        btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto);
        btnTomarFoto.setOnClickListener(this);
        ///////////////////////
        ivFotoPrenda = (ImageView) findViewById(R.id.ivFotoPrenda);
        btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto);
        btnTomarFoto.setOnClickListener(this);
        etCodigo = (EditText) findViewById(R.id.etCodigo);
        etNombrePrenda = (EditText) findViewById(R.id.etNombrePrenda);
        etDescripcionPrenda = (EditText) findViewById(R.id.etDescripcionPrenda);
        etPrecioPrenda = (EditText) findViewById(R.id.etPrecioPrenda);
        radioButtonM = (RadioButton) findViewById(R.id.radioButtonM);
        radioButtonF = (RadioButton) findViewById(R.id.radioButtonF);
        //////////////////////////////////  SPINNER/DROP DOWN LIST
        spCategoria = (Spinner) findViewById(R.id.spCategoria);
    }

    public void asigSpinner(){
        list = new ArrayList();
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
    }

    public void traerArt(){
        Intent intent = getIntent();    //creo el objeto intent
        Bundle bundle = intent.getExtras(); //trae el objeto con todas las cosas q le haya pasado desde la actividad anterior
        if(bundle!=null){   // se fija si esta vacio para q no de error
            //COMPLETO LOS CAMPOS CON LOS DATOS DEL ARTICULO
            articulo = (Articulo) getIntent().getExtras().getSerializable("articuloDeLista");
            etCodigo.setText(articulo.getCodigo(), TextView.BufferType.EDITABLE);
            etNombrePrenda.setText(articulo.getNombrePrenda(), TextView.BufferType.EDITABLE);
            cargarSpinner();
            etDescripcionPrenda.setText(articulo.getDescripcion(), TextView.BufferType.EDITABLE);
            etPrecioPrenda.setText(String.valueOf(articulo.getPrecio()), TextView.BufferType.EDITABLE);
            ivFotoPrenda.setImageBitmap(cargarFoto(articulo.getCodArticulo()));
            llenarColores();
            if (articulo.getGenero().equals("femenino")) {
                radioButtonF.setChecked(true);
            } else {radioButtonM.setChecked(true);}
        }
    }

    //TRAIGO LOS COLORES QUE TENIA SELECCIONADOS
    public void llenarColores(){
        class colorChk{
            CheckBox check;
            String color;
        }
        colorChk chk[] = new colorChk[9];
        chk[0] = new colorChk();
        chk[0].check = (CheckBox) findViewById(R.id.chkAmarillo);
        chk[0].color = "Amarillo";
        chk[1] = new colorChk();
        chk[1].check = (CheckBox) findViewById(R.id.chkRojo);
        chk[1].color = "Rojo";
        chk[2] = new colorChk();
        chk[2].check = (CheckBox) findViewById(R.id.chkNaranja);
        chk[2].color = "Naranja";
        chk[3] = new colorChk();
        chk[3].check = (CheckBox) findViewById(R.id.chkVerde);
        chk[3].color = "Verde";
        chk[4] = new colorChk();
        chk[4].check = (CheckBox) findViewById(R.id.chkAzul);
        chk[4].color = "Azul";
        chk[5] = new colorChk();
        chk[5].check = (CheckBox) findViewById(R.id.chkRosa);
        chk[5].color = "Rosa";
        chk[6] = new colorChk();
        chk[6].check = (CheckBox) findViewById(R.id.chkNegro);
        chk[6].color = "Negro";
        chk[7] = new colorChk();
        chk[7].check = (CheckBox) findViewById(R.id.chkBlanco);
        chk[7].color = "Blanco";
        chk[8] = new colorChk();
        chk[8].check = (CheckBox) findViewById(R.id.chkVioleta);
        chk[8].color = "Violeta";
        for(int i=0; i<9; i++){
            if(articulo.getColores().contains(chk[i].color)){
                chk[i].check.setChecked(true);
            }
        }
    }

    public void cargarSpinner(){
        String cate[] = new String[6];
        cate[0] = "Pantalones";
        cate[1] = "Remeras";
        cate[2] = "Buzos";
        cate[3] = "Boxer";
        cate[4] = "Bombachas";
        cate[5] = "Corpiños";
        for (int i=0; i<6; i++) {
            if(articulo.getCategoria().equals(cate[i]) ){
                spCategoria.setSelection(i);
                return;
            }
        }
    }

    public Bitmap cargarFoto(int codArt){
        //MUESTRO LA IMAGEN DEL ARTICULO
        Bitmap bitmap = null;
        try{
            FileInputStream fileInputStream =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/"+
                            codArt+".png");
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        }catch (IOException io){
            io.printStackTrace();
        }
        bmp = bitmap;
        return bitmap;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datos) {
        super.onActivityResult(requestCode, requestCode, datos);
        if (resultCode == Activity.RESULT_OK) {   //SI LA ACTIVIDAD DE LA CAMARA SE COMPLETO CORRECTAMENTE
            Bundle extras = datos.getExtras();  //OBTENGO LOS DATOS(FOTO)
            bmp = (Bitmap) extras.get("data");  //Y LA GUARDO EN UN OBJETO DE TIPO BITMAP
            ivFotoPrenda.setImageBitmap(bmp);    //Y ASIGNO LA IMAGEN AL IMAGEVIEW
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

    public void funActualizarArticulo(SQLiteDatabase db){
        articulo.setCodigo(etCodigo.getText().toString());
        articulo.setNombrePrenda(etNombrePrenda.getText().toString());
        articulo.setCategoria(spCategoria.getSelectedItem().toString());
        articulo.setDescripcion(etDescripcionPrenda.getText().toString());
        articulo.setPrecio(etPrecioPrenda.getText().toString());
        articulo.setColores(getColores());
        if (radioButtonF.isChecked()) {
            articulo.setGenero("femenino");
        } else {articulo.setGenero("masculino");}
        try {
            //ACTUALIZAR UN REGISTRO
            db.execSQL("UPDATE articulos SET " +
                       "codigo='"+articulo.getCodigo()+"'," +
                       "nombre='"+articulo.getNombrePrenda()+"'," +
                       "categoria='"+articulo.getCategoria()+"'," +
                       "descripcion='"+articulo.getDescripcion()+"'," +
                       "precio='"+articulo.getPrecio()+"', "+
                       "colores='"+articulo.getColores()+"', "+
                       "genero='"+articulo.getGenero()+"' "+
                       "WHERE codArt="+articulo.getCodArticulo());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        db.close();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            FileOutputStream outputStream = getApplicationContext().openFileOutput(
                    articulo.getCodArticulo()+".png", Context.MODE_PRIVATE);
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

    @Override
    public void onClick(View v) {
        BD bdArticulos = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdArticulos.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnTomarFoto:
                intentImagen = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //ABRE LA ACTIVIDAD DE LA CAMARA
                startActivityForResult(intentImagen, 0);
                break;
            case R.id.btnActualizarArt:
                boolean correcto = true;
                correcto = validarEditText(etNombrePrenda);
                correcto = validarEditText(etDescripcionPrenda);
                correcto = validarEditText(etPrecioPrenda);
                if(correcto){
                    try {
                        funActualizarArticulo(db);
                        Intent irMenuPrincipal = new Intent(this,MainActivity.class);
                        startActivity(irMenuPrincipal);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
