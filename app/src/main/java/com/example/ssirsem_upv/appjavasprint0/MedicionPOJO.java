package com.example.ssirsem_upv.appjavasprint0;

import java.io.Serializable;

public class MedicionPOJO implements Serializable {

    //atributos privados de la clase

    //valor de la medicion del sensor
    private int medicion;

    //valores de las coordenadas para saber desde donde se tomó la medición
    private double latitud;
    private double longitud;


    //constructor de la clase
    public MedicionPOJO(int medicion, double latitud, double longitud) {

        this.medicion = medicion;
        this.latitud = latitud;
        this.longitud = longitud;
    }


    //Getters de la clase

    /**
     * getter del atributo medicion
     *
     * @return
     */
    public int getMedicion() {
        return medicion;
    }


    /**
     * getter del atributo latitud
     *
     * @return
     */
    public double getLatitud() {
        return latitud;
    }


    /**
     * getter del atributo longitud
     *
     * @return
     */
    public double getLongitud() {
        return longitud;
    }
}
