package com.infinitycr.amadecasa;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.infinitycr.amadecasa.clases.BD;

import java.util.ArrayList;
import java.util.List;

public class CrearUsuario extends AppCompatActivity implements View.OnClickListener{
    Button btnCrearUsuario;
    Button btnBorrarTabla;
    EditText etMail;
    EditText etNombre;
    EditText etPassWord;
    EditText etPassWord2;
    EditText etDireccion;
    EditText etLocalidad;
    EditText etNumeroTelefono;
    EditText etCp;
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
    Spinner spEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        asigObjXML();
        asigSpinners();
        radioButtonF.setChecked(true);
        //ABRIMOS LA BD 'BDAlumnos' en modo escritura / sino la crea
        //BDAlumnos alumnos = new BDAlumnos(null);

        //LA ABRIMOS EN MODO ESCRITURA
        //SQLiteDatabase db = alumnos.getWritableDatabase();

        //COMPRUEBO QUE LA BD SE ABRE CORRECTAMENTE

        //BDAlumnos db = new BDAlumnos( getApplicationContext() );
        //int id = 2;
        //db.agregar("Ramiro");
        // db.agregar("chespi");
        //etNombre.setText(db.obtener(id));
        //System.out.println(id);
        //db.obtener(id);

    }

    public void asigObjXML(){
        btnCrearUsuario = (Button) findViewById(R.id.btnCrearUsuario);
        btnCrearUsuario.setOnClickListener(this);
        btnBorrarTabla = (Button) findViewById(R.id.btnBorrarTabla);
        btnBorrarTabla.setOnClickListener(this);
        etMail = (EditText) findViewById(R.id.etMail);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etPassWord = (EditText) findViewById(R.id.etPassword);
        etPassWord2 = (EditText) findViewById(R.id.etPassword2);
        etNumeroTelefono = (EditText) findViewById(R.id.etNumeroTelefono);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etLocalidad = (EditText) findViewById(R.id.etLocalidad);
        etCp = (EditText) findViewById(R.id.etCp);
        radioButtonM = (RadioButton) findViewById(R.id.radioButtonM);
        radioButtonF = (RadioButton) findViewById(R.id.radioButtonF);
    }

    public void asigSpinners() {
        spEdad = (Spinner) findViewById(R.id.spEdad);
        List list = new ArrayList();
        list.add("De 15 a 25 años");
        list.add("De 25 a 45 años");
        list.add("De 45 en adelante");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEdad.setAdapter(arrayAdapter);
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

    public void crearUsuario(SQLiteDatabase db){
        String genero = "";
        String consulta = "";
        String rangoEdad = spEdad.getSelectedItem().toString();
        if (radioButtonF.isChecked()) {genero="femenino";} else {genero="masculino";}
        try {
            //INSERTAR UN REGISTRO
            consulta = "INSERT INTO usuarios (mailUsuario,nombre,passWord,rangoEdad,numeroTelefono," +
                       "direccion,localidad,cp,genero,coloresFavoritos) " +
                       "VALUES('"+etMail.getText()+"','"+etNombre.getText()+"'," +
                       "'"+ etPassWord.getText() + "','"+ rangoEdad +"'," +
                       "'"+ etNumeroTelefono.getText()  + "','" + etDireccion.getText() + "'," +
                       "'"+ etLocalidad.getText() +"','" + etCp.getText() + "'," +
                       "'"+ genero + "','" + getColores() + "')";
            db.execSQL(consulta);
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

    public void onClick(View v) {
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        switch (v.getId()){
            case R.id.btnCrearUsuario:
                boolean correcto = true;
                correcto = validarEditText(etMail);
                correcto = validarEditText(etNombre);
                correcto = validarEditText(etPassWord);
                correcto = validarEditText(etPassWord2);
                correcto = validarEditText(etDireccion);
                correcto = validarEditText(etLocalidad);
                correcto = validarEditText(etCp);
                String con1 = etPassWord.getText().toString();
                String con2 = etPassWord2.getText().toString();
                if(!con1.contains(con2)){
                    etPassWord2.setError("Las contraseñas deben ser iguales");
                }
                if(correcto){
                    boolean bien=false;
                    try {
                        crearUsuario(db);
                        bien=true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        bien=false;
                    }
                    if(bien){
                        Intent intent = new Intent(this,Login.class);
                        startActivity(intent);
                    }
                }
                break;

            case R.id.btnBorrarTabla:
                db.execSQL("DROP TABLE IF EXISTS usuarios");
                db.execSQL(bdPaoPrendas.actualizarTablaUsuarios());
                break;

        }
    }
}
