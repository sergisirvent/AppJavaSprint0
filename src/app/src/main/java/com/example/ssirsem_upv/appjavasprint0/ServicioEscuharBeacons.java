package com.example.ssirsem_upv.appjavasprint0;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import static android.app.Service.START_STICKY;

/**
 *ServicioEscucharBeacons.java
 *Fecha: 2021/10
 *Autor: Sergi Sirvent Sempere
 *
 *Clase encargada de definir el servicio de la escucha de los beacons
 */





// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
public class ServicioEscuharBeacons  extends IntentService {

    // ---------------------------------------------------------------------------------------------
    //Atributos privados de la clase beacons
    // ---------------------------------------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private long tiempoDeEspera = 10000;

    private boolean seguir = true;

    // ---------------------------------------------------------------------------------------------
    //Constructor de la clase
    // ---------------------------------------------------------------------------------------------
    public ServicioEscuharBeacons(  ) {
        super("HelloIntentService");

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.constructor: termina");
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------


    // ---------------------------------------------------------------------------------------------

    /**
     * Metodo de la clase que se encarga de poner en falso el booleano de
     * seguir, por lo tanto detiene la busqueda
     *
     * No recibe ni devuelve nada
     */
    // ---------------------------------------------------------------------------------------------
    public void parar () {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() " );


        if ( this.seguir == false ) {
            return;
        }

        this.seguir = false;
        this.stopSelf();

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.parar() : acaba " );

    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Metodo de la clase que se encarga de destruir el objeto de servicio
     *
     * No recibe ni devuelve nada
     */
    // ---------------------------------------------------------------------------------------------
    public void onDestroy() {

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onDestroy() " );


        this.parar(); // posiblemente no haga falta, si stopService() ya se carga el servicio y su worker thread
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    /**
     * Metodo de la clase que se encarga de manejar el intent del servicio de la clase
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        this.tiempoDeEspera = intent.getLongExtra("tiempoDeEspera", /* default */ 50000);
        this.seguir = true;

        // esto lo ejecuta un WORKER THREAD !

        long contador = 1;

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: empieza : thread=" + Thread.currentThread().getId() );

        try {

            while ( this.seguir ) {
                Thread.sleep(tiempoDeEspera);
                Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent: tras la espera:  " + contador );
                contador++;
            }

            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleIntent : tarea terminada ( tras while(true) )" );


        } catch (InterruptedException e) {
            // Restore interrupt status.
            Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: problema con el thread");

            Thread.currentThread().interrupt();
        }

        Log.d(ETIQUETA_LOG, " ServicioEscucharBeacons.onHandleItent: termina");

    }
} // class
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------// -------------------------------------------------------------------------------------------------