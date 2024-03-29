/*
 *
 *PeticionarioREST.java
 *Fecha: 2021/10
 *Autor: Sergi Sirvent Sempere
 *
 *Clase encargada de manejar las peticionesREST a los metodos de la logica verdadera
 */
package com.example.ssirsem_upv.appjavasprint0;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

// ------------------------------------------------------------------------
// ------------------------------------------------------------------------
public class PeticionarioREST extends AsyncTask<Void, Void, Boolean> {

    // --------------------------------------------------------------------
    //Interfaz  respuesta REST

    /**
     * Pasa al callback un codigo y un cuerpo que es una string
     *
     * @param {Z} codigo - Codigo de la resouesta
     * @param {String} cuerpo - Cuerpo de la respuesta
     */
    // --------------------------------------------------------------------
    public interface RespuestaREST {
        void callback (int codigo, String cuerpo) throws JSONException;

    }

    // --------------------------------------------------------------------
    //atributos privados de la clase
    // --------------------------------------------------------------------
    private String elMetodo;
    private String urlDestino;
    private String elCuerpo = null;
    private RespuestaREST laRespuesta;

    private int codigoRespuesta;
    private String cuerpoRespuesta = "";

    // --------------------------------------------------------------------

    /**
     * Método que se utiliza para realizar una petición REST
     *
     * @param metodo
     * @param urlDestino
     * @param cuerpo
     * @param laRespuesta
     */
    // --------------------------------------------------------------------
    public void hacerPeticionREST (String metodo, String urlDestino, String cuerpo, RespuestaREST  laRespuesta) {
        this.elMetodo = metodo;
        this.urlDestino = urlDestino;
        this.elCuerpo = cuerpo;
        this.laRespuesta = laRespuesta;

        this.execute(); // otro thread ejecutará doInBackground()
    }

    // --------------------------------------------------------------------
    //constructor de la clase
    // --------------------------------------------------------------------
    public PeticionarioREST() {
        Log.d("clienterestandroid", "constructor()");
    }

    // --------------------------------------------------------------------

    /**
     *
     * Método de la clase para identificar que tipo de petición REST se ejecutará
     * @param params
     * @return
     */
    // --------------------------------------------------------------------
    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("clienterestandroid", "doInBackground()");

        try {

            // envio la peticion

            // pagina web para hacer pruebas: URL url = new URL("https://httpbin.org/html");
            // ordinador del despatx 158.42.144.126 // OK URL url = new URL("http://158.42.144.126:8080");

            Log.d("clienterestandroid", "doInBackground() me conecto a >" + urlDestino + "<");

            URL url = new URL(urlDestino);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty( "Content-Type", "application/json; charset-utf-8" );
            connection.setRequestMethod(this.elMetodo);
            // connection.setRequestProperty("Accept", "*/*);

            // connection.setUseCaches(false);
            connection.setDoInput(true);

            if ( ! this.elMetodo.equals("GET") && this.elCuerpo != null && ! this.elMetodo.equals("DELETE")) {
                Log.d("clienterestandroid", "doInBackground(): no es get ni delete, pongo cuerpo");
                connection.setDoOutput(true);
                // si no es GET, pongo el cuerpo que me den en la peticin
                DataOutputStream dos = new DataOutputStream (connection.getOutputStream());
                dos.writeBytes(this.elCuerpo);
                dos.flush();
                dos.close();
            }





            ///////////////////////////////////////////////////////////////////////////////////////////
            // ya he enviado la peticin
            Log.d("clienterestandroid", "doInBackground(): peticin enviada ");

            // ahora obtengo la respuesta

            int rc = connection.getResponseCode();
            String rm = connection.getResponseMessage();
            String respuesta = "" + rc + " : " + rm;
            Log.d("clienterestandroid", "doInBackground() recibo respuesta = " + respuesta);
            this.codigoRespuesta = rc;

            try {

                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                Log.d("clienterestandroid", "leyendo cuerpo");
                StringBuilder acumulador = new StringBuilder ();
                String linea;
                while ( (linea = br.readLine()) != null) {
                    Log.d("clienterestandroid", linea);
                    acumulador.append(linea);
                }
                Log.d("clienterestandroid", "FIN leyendo cuerpo");

                this.cuerpoRespuesta = acumulador.toString();
                Log.d("clienterestandroid", "cuerpo recibido=" + this.cuerpoRespuesta);

                connection.disconnect();

            } catch (IOException ex) {
                // dispara excepcin cuando la respuesta REST no tiene cuerpo y yo intento getInputStream()
                Log.d("clienterestandroid", "doInBackground() : parece que no hay cuerpo en la respuesta");
            }

            return true; // doInBackground() termina bien

        } catch (Exception ex) {
            Log.d("clienterestandroid", "doInBackground(): ocurrio alguna otra excepcion: " + ex.getMessage());
        }

        return false; // doInBackground() NO termina bien
    } // ()

    // --------------------------------------------------------------------

    /**
     * Método de la clase que se encarga de comprobar cual ha sido la respuesta al
     * proceso
     *
     * @param comoFue
     */
    // --------------------------------------------------------------------
    protected void onPostExecute(Boolean comoFue) {
        // llamado tras doInBackground()
        Log.d("clienterestandroid", "onPostExecute() comoFue = " + comoFue);
        try {
            this.laRespuesta.callback(this.codigoRespuesta, this.cuerpoRespuesta);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

} // class


