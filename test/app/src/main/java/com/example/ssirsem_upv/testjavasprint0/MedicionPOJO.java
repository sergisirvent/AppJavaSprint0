package com.example.ssirsem_upv.testjavasprint0;

import java.io.Serializable;

public class MedicionPOJO implements Serializable {

    //atributos privados de la clase

    //valor de la medicion del sensor
    private int medicion;

    //valores de las coordenadas para saber desde donde se tomó la medición
    private double latitud;
    private double longitud;
    private int major;
    private int minor;


    //constructor de la clase

    public MedicionPOJO(int medicion, double latitud, double longitud) {
        this.medicion = medicion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public MedicionPOJO(int medicion, double latitud, double longitud, int major, int minor) {
        this.medicion = medicion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.major = major;
        this.minor = minor;
    }

    //Getters de la clase

    /**
     * getter del atributo medicion
     *
     * @return medicion {Z}
     */
    public int getMedicion() {
        return medicion;
    }


    /**
     * getter del atributo latitud
     *
     * @return latitud {Z}
     */
    public double getLatitud() {
        return latitud;
    }


    /**
     * getter del atributo longitud
     *
     * @return longitud {Z}
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * getter del atributo major
     *
     * @return major {Z}
     */

    public int getMajor() {
        return major;
    }

    /**
     * getter del atributo minor
     *
     * @return minor {Z}
     */

    public int getMinor() {
        return minor;
    }
}
