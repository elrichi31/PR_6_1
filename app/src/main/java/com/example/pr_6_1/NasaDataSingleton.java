package com.example.pr_6_1;

import java.util.ArrayList;
import java.util.HashMap;

public class NasaDataSingleton {
    private static NasaDataSingleton instance;

    private HashMap<String, Nasa> listaImagenes;

    private NasaDataSingleton() {
        listaImagenes = new HashMap<>();
    }

    public static NasaDataSingleton getInstance() {
        if (instance == null) {
            instance = new NasaDataSingleton();
        }
        return instance;
    }

    public void updateData(ArrayList<Nasa> nasaList) {
        for (Nasa nasa : nasaList) {
            listaImagenes.put(nasa.getTitulo(), nasa);
        }
    }

    public HashMap<String, Nasa> getListaImagenes() {
        return listaImagenes;
    }
}
