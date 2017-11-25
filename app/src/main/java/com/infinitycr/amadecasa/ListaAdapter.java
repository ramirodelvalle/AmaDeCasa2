package com.infinitycr.amadecasa;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;

/**
 * Created by ramir on 15/8/2017.
 */

public class ListaAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    Articulo[] articulo;
    Bitmap[] imagenes;
    LayoutInflater inflater;
    int i;  //LA CANTIDAD DE DE ARTICULOS Q VAN A LLENAR LA LISTA
    FileInputStream fis;

    public ListaAdapter(Context context, Articulo[] articulo,int i,Bitmap[] imagenes) {
        this.context = context;
        this.imagenes = imagenes;
        this.articulo = articulo;
        this.i = i;
    }

    @Override
    public int getCount() {
        return i;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView tvCodArticulo;
        TextView tvCategoria;
        TextView tvSubCategoria;
        TextView tvPrecioPrenda;
        TextView tvMarcaPrenda;
        ImageView imgImg;

        //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.lista_formato, parent, false);

        //LOCALIZA LOS TEXTVIEWS EN EL lista_formato.xml
        tvCodArticulo = (TextView) itemView.findViewById(R.id.tvCodArticulo);
        tvCategoria = (TextView) itemView.findViewById(R.id.tvCategoria);
        tvSubCategoria = (TextView) itemView.findViewById(R.id.tvSubCategoria);
        tvPrecioPrenda = (TextView) itemView.findViewById(R.id.tvPrecioPrenda);
        tvMarcaPrenda = (TextView) itemView.findViewById(R.id.tvMarcaPrenda);
        imgImg = (ImageView) itemView.findViewById(R.id.iconLista);

        //CAPTURA LA POSICION DEL ARTICULO Y SETEA LOS TextViews
        tvCodArticulo.setText(String.valueOf(articulo[position].getCodArticulo()));
        tvCategoria.setText(articulo[position].getCategoria());
        tvSubCategoria.setText(articulo[position].getSubCategoria());
        tvPrecioPrenda.setText("$ "+String.valueOf(articulo[position].getPrecio()));
        tvMarcaPrenda.setText(articulo[position].getMarca());
        imgImg.setImageBitmap(imagenes[position]);

        return itemView;
    }


}
