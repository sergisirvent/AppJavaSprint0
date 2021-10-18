/*
 *
 *MedicionPOJO.java
 *Fecha: 2021/09
 *Autor: Sergi Sirvent Sempere
 *
 *Clase POJO de la medicion. Son las mediciones que obtendremos del sensor (major y minor), junto con un
 * valro orientativo para procesar las mediciones
 */
package com.example.ssirsem_upv.appjavasprint0;

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




    /**
     * Constructor de la clase sin major ni minor
     *
     * @param medicion
     * @param latitud
     * @param longitud
     */
    public MedicionPOJO(int medicion, double latitud, double longitud) {
        this.medicion = medicion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Constructor de la clase con major y minor
     *
     * @param medicion
     * @param latitud
     * @param longitud
     * @param major
     * @param minor
     */
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
