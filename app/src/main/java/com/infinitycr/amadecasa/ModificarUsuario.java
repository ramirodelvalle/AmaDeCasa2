package com.infinitycr.amadecasa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.infinitycr.amadecasa.clases.BD;

public class ModificarUsuario extends AppCompatActivity implements View.OnClickListener {
    Button btnActualizarDatos;
    Button btnBorrarTabla;
    TextView tvMail;
    EditText etNombre;
    EditText etPassWord;
    EditText etPassWord2;
    EditText etDireccion;
    EditText etLocalidad;
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
    String mailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        asigObjXML();
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        mailUsuario = (preferences.getString("mail","no tenia datos cargados"));
        llenarDatos(db);

    }

    public void asigObjXML() {
        btnActualizarDatos = (Button) findViewById(R.id.btnActualizarDatos);
        btnActualizarDatos.setOnClickListener(this);
        btnBorrarTabla = (Button) findViewById(R.id.btnBorrarTabla);
        btnBorrarTabla.setOnClickListener(this);
        tvMail = (TextView) findViewById(R.id.tvMail);
        etNombre = (EditText) findViewById(R.id.etNombre);
        etPassWord = (EditText) findViewById(R.id.etPassword);
        etPassWord2 = (EditText) findViewById(R.id.etPassword2);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etLocalidad = (EditText) findViewById(R.id.etLocalidad);
        etCp = (EditText) findViewById(R.id.etCp);
        radioButtonM = (RadioButton) findViewById(R.id.radioButtonM);
        radioButtonF = (RadioButton) findViewById(R.id.radioButtonF);
    }

    //TRAIGO LOS COLORES QUE TENIA SELECCIONADOS
    public void llenarColores(String colores){
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
            if(colores.contains(chk[i].color)){
                chk[i].check.setChecked(true);
            }
        }
    }

    //TRAIGO LOS COLORES SELECCIONADOS
    public String getColores() {
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
        for (int i = 0; i < 9; i++) {
            if (vChk[i].isChecked()) {
                colores += vChk[i].getText() + ",";
            }
        }
        return colores;
    }

    public void llenarDatos(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM usuarios WHERE mailUsuario='"+mailUsuario+"'", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                tvMail.setText(c.getString(0));
                etNombre.setText(c.getString(1));
                etPassWord.setText(c.getString(2));
                etPassWord2.setText(c.getString(2));
                etDireccion.setText(c.getString(3));
                etLocalidad.setText(c.getString(4));
                etCp.setText(c.getString(5));
                if (c.getString(6).contains("femenino")) {radioButtonF.setChecked(true);}
                else {radioButtonM.setChecked(true);}
                llenarColores(c.getString(7));
                c.close();
            } while (c.moveToNext());
            c.close();
        }
    }

    public void actualizarUsuario(SQLiteDatabase db){
        String genero = "";
        String consulta = "";
        if (radioButtonF.isChecked()) {genero="femenino";} else {genero="masculino";}
        try {
            //Actualizar un registro
            consulta = "UPDATE usuarios SET nombre='"+etNombre.getText()+"'," +
                       "passWord='" + etPassWord.getText() + "'," +
                       "direccion='"+ etDireccion.getText() +"'," +
                       "localidad='"+ etLocalidad.getText() + "'," +
                       "cp='"+ etCp.getText() +"',genero='"+genero+"'," +
                       "coloresFavoritos='"+ getColores() +"' " +
                       "WHERE mailUsuario='"+tvMail.getText()+"'";
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

    @Override
    public void onClick(View v) {
        BD bdPaoPrendas = new BD(this, "BDPP", null, 1);
        SQLiteDatabase db = bdPaoPrendas.getWritableDatabase();
        switch (v.getId()) {
            case R.id.btnActualizarDatos:
                boolean correcto = true;
                correcto = validarEditText(etNombre);
                correcto = validarEditText(etPassWord);
                correcto = validarEditText(etPassWord2);
                correcto = validarEditText(etDireccion);
                correcto = validarEditText(etLocalidad);
                correcto = validarEditText(etCp);
                String con1 = etPassWord.getText().toString();
                String con2 = etPassWord2.getText().toString();
                if(!con1.contains(con2)){
                    etPassWord2.setError("Las contraseñas deben ser iguales");}
                if(correcto){
                    try {
                        actualizarUsuario(db);
                    } catch (Exception e) {e.printStackTrace();}
                }
                break;
        }
    }
}
