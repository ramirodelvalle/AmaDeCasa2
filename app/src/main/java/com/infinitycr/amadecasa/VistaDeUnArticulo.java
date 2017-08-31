package com.infinitycr.amadecasa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class VistaDeUnArticulo extends AppCompatActivity implements View.OnClickListener{

    ImageView ivFotoPrenda;
    Intent intentImagen; //INTENT PARA SACAR LA FOTO
    Bitmap bmp; //IMAGEN OBTENIDA DE LA CAMARA
    TextView tvCodArticulo;
    TextView tvNombrePrenda;
    TextView tvCategoria;
    TextView tvPrecioPrenda;
    TextView tvDescripcionPrenda;
    TextView tvColores;
    TextView tvGenero;
    TextView tvMarcaPrenda;
    Button btnModificarArt;

    Articulo articulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_de_un_articulo);

        asigObjXML();
        Intent intent = getIntent();    //creo el objeto intent
        Bundle bundle = intent.getExtras(); //trae el objeto con todas las cosas q le haya pasado desde la actividad anterior
        if(bundle!=null){   // se fija si esta vacio para q no de error
            //TRAIGO EL OBJETO Q PASE DESDE LA OTRA ACTIVITY
            articulo = (Articulo) getIntent().getExtras().getSerializable("articuloDeLista");
            //etNombreArticulo.setText(articulo.getNombrePrenda());
            tvCodArticulo.setText(String.valueOf(articulo.getCodArticulo()));
            tvNombrePrenda.setText(articulo.getNombrePrenda());
            tvCategoria.setText(articulo.getCategoria());
            tvPrecioPrenda.setText(String.valueOf(articulo.getPrecio()));
            tvDescripcionPrenda.setText(articulo.getDescripcion());
            tvColores.setText(articulo.getColores());
            tvGenero.setText(articulo.getGenero());
            tvMarcaPrenda.setText(articulo.getMarca());
        }

        //MUESTRO LA IMAGEN DEL ARTICULO
        Bitmap bitmap = null;
        try{
            FileInputStream fileInputStream =
                    new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/"+
                                        articulo.getCodArticulo()+".png");
            bitmap = BitmapFactory.decodeStream(fileInputStream);
        }catch (IOException io){
            io.printStackTrace();
        }
        ivFotoPrenda.setImageBitmap(bitmap);
    }

    public void asigObjXML(){
        ivFotoPrenda = (ImageView) findViewById(R.id.ivFotoPrenda);
        tvCodArticulo = (TextView) findViewById(R.id.tvCodArticulo);
        tvNombrePrenda = (TextView) findViewById(R.id.tvNombrePrenda);
        tvCategoria = (TextView) findViewById(R.id.tvCategoria);
        tvPrecioPrenda = (TextView) findViewById(R.id.tvPrecioPrenda);
        tvDescripcionPrenda = (TextView) findViewById(R.id.tvDescripcionPrenda);
        tvPrecioPrenda = (TextView) findViewById(R.id.tvPrecioPrenda);
        tvColores = (TextView) findViewById(R.id.tvColores);
        tvGenero = (TextView) findViewById(R.id.tvGenero);
        tvMarcaPrenda = (TextView) findViewById(R.id.tvMarcaPrenda);
        btnModificarArt = (Button) findViewById(R.id.btnModificarArt);
        btnModificarArt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnModificarArt:
                Intent intent = new Intent(this,modificarArticulo.class); //ABRE LA ACTIVIDAD
                intent.putExtra("articuloDeLista",articulo);    //pasa el dato como una cookie
                startActivity(intent);
                break;
        }
    }



}
