package com.infinitycr.amadecasa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by ramir on 11/8/2017.
 */
//////////kl;
public class Articulo implements Serializable{

    private static final long serialVersionUID = 1L;
    private String CodArticulo;
    private String NombrePrenda;
    private String Descripcion;
    private String Colores;
    private String Categoria;
    private String Precio;
    private String Genero;
    private String Marca;
    private FileInputStream RutaImagen;
    private Bitmap imagen;

    public Articulo(){}

    public Articulo(String codArticulo,String nombrePrenda,String descripcion,String colores,
                    String categoria,String precio,FileInputStream rutaImagen,String genero,String marca){
        super();
        this.CodArticulo = codArticulo;
        this.NombrePrenda = nombrePrenda;
        this.Descripcion = descripcion;
        this.Colores = colores;
        this.Categoria = categoria;
        this.Precio = precio;
        this.RutaImagen = rutaImagen;
        this.Genero = genero;
        this.Marca = marca;
    }

    void setCodArticulo(String codArticulo){
        CodArticulo = codArticulo;
    }
    void setNombrePrenda(String nombrePrenda){
        NombrePrenda = nombrePrenda;
    }
    void setDescripcion(String descripcion){
        Descripcion = descripcion;
    }
    void setColores(String colores){
        Colores = colores;
    }
    void setCategoria(String categoria){
        Categoria = categoria;
    }
    void setPrecio(String precio){
        Precio = precio;
    }
    void setGenero(String genero){Genero = genero;}
    void setMarca(String marca){Marca = marca;}
    void setRutaImagen(FileInputStream rutaImagen){
        RutaImagen = rutaImagen;
    }
    void setImagen(Bitmap bmp){
        imagen = bmp;
    }

    String getCodArticulo(){
        return CodArticulo;
    }
    String getNombrePrenda(){
        return NombrePrenda;
    }
    String getDescripcion(){
        return Descripcion;
    }
    String getColores(){
        return Colores;
    }
    String getCategoria(){
        return Categoria;
    }
    String getPrecio(){return Precio;}
    String getGenero(){return Genero;}
    String getMarca(){return Marca;}
    FileInputStream getRutaImagen(){
        return RutaImagen;
    }
    Bitmap getImagen(){
        return imagen;
    }


    @Override
    public String toString(){
        return this.NombrePrenda;
    }

    // Constant with a file name
    public static String fileName = "createResumeForm.ser";


    // Serializes an object and saves it to a file
    /*
    public void saveToFile(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
