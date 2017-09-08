package com.infinitycr.amadecasa.clases;

import java.util.ArrayList;

/**
 * Created by ramir on 7/9/2017.
 */
public class ManejadorDeDatos extends Object{

    public static ArrayList<String> nombre = new ArrayList<String>();
    public static ArrayList<String> origen = new ArrayList<String>();
    public static ArrayList<String> significado = new ArrayList<String>();

    private static final ManejadorDeDatos INSTANCE = new ManejadorDeDatos();

    //====================================================
    // CONSTRUCTOR
    //====================================================

    public ManejadorDeDatos() {
    }

    //====================================================
    // GETTERS & SETTERS
    //====================================================
    public static ManejadorDeDatos getInstance(){
        return INSTANCE;
    }

    public static ArrayList<String> ObtenerListaNombres() {
        return nombre;
    }

    public static ArrayList<String> ObtenerListaOrigenes() {
        return origen;
    }

    public static ArrayList<String> ObtenerListaSignificados() {
        return significado;
    }

    public void SetearValores(String mNombre,String mOrigen,String mSignificado) {
        nombre.add(mNombre);
        origen.add(mOrigen);
        significado.add(mSignificado);
    }

}
