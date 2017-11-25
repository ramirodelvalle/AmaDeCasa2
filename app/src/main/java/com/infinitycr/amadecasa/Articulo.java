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
    private int CodTabla;
    private String Codigo;
    private String NombrePrenda;
    private String Descripcion;
    private String Colores;
    private String Categoria;
    private String SubCategoria;
    private String Precio;
    private String Genero;
    private String Marca;
    private FileInputStream RutaImagen;
    private Bitmap imagen;

    public Articulo(){}

    public Articulo(int codTabla,String codigo,String nombrePrenda,String descripcion,String colores,
                    String categoria,String subCategoria,String precio,FileInputStream rutaImagen,String genero,String marca){
        super();
        this.CodTabla = codTabla;
        this.Codigo = codigo;
        this.NombrePrenda = nombrePrenda;
        this.Descripcion = descripcion;
        this.Colores = colores;
        this.Categoria = categoria;
        this.Categoria = subCategoria;
        this.Precio = precio;
        this.RutaImagen = rutaImagen;
        this.Genero = genero;
        this.Marca = marca;
    }

    void setCodTabla(int codTabla){
        CodTabla = codTabla;
    }
    void setCodArticulo(String codigo){
        Codigo = codigo;
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
    void setSubCategoria(String subCategoria){
        SubCategoria = subCategoria;
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

    int getCodTabla(){
        return CodTabla;
    }
    String getCodArticulo(){
        return Codigo;
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
    String getSubCategoria(){
        return SubCategoria;
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


   // @Override
    //public String toString(){
        //return this.NombrePrenda;
   // }

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
