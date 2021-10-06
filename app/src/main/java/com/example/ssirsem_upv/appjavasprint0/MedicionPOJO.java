package com.example.ssirsem_upv.appjavasprint0;

public class MedicionPOJO {


    private int medicion;
    private double latitud;
    private double longitud;

    public MedicionPOJO(int medicion, double latitud, double longitud) {

        this.medicion = medicion;
        this.latitud = latitud;
        this.longitud = longitud;
    }



    public int getMedicion() {
        return medicion;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
