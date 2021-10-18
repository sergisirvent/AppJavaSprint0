/*
*
 *LogicaFake.java
 *Fecha: 2021/09
 *Autor: Sergi Sirvent Sempere
 *
 *Clase encargada de llamar desde android a los metodos de la logica real de negocio
 */
package com.example.ssirsem_upv.appjavasprint0.LogicaFake;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ssirsem_upv.appjavasprint0.MainActivity;
import com.example.ssirsem_upv.appjavasprint0.MedicionPOJO;
import com.example.ssirsem_upv.appjavasprint0.PeticionarioREST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public  class LogicaFake extends AppCompatActivity {



    /**
     *
     * Método de la lógica fake que recibe una medición y conecta con guardarMedicion.php.
     *
     * @param medicion {MedicionPOJO} - Medicion para subir a la bbdd
     */

    public void guardarMedicion(MedicionPOJO medicion){

        // Creamos un objeto peticionarioRest para utilizar sus metodos
        PeticionarioREST elPeticionario = new PeticionarioREST();


        //definimos el cuerpo de nuestra peticion
        String textoJSON = "{\"Medicion\":\""+medicion.getMedicion()+"\", \"Longitud\":\""+medicion.getLongitud() +"\", \"Latitud\": \""+medicion.getLatitud()+"\" , \"Major\": \""+medicion.getMajor()+"\" , \"Minor\": \""+medicion.getMinor()+"\"}";
        //montamos la peticion y la enviamos
        elPeticionario.hacerPeticionREST("POST", "http://172.20.10.9/back_endSprint0/src/LogicaNegocio/guardarMedicion.php", textoJSON,
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        //si ha sido correcta la operacion
                        Log.d("","Medición subida correctamente a la base de datos");
                    }
                }
        );


    }

    /**
     *
     * Método que recibe el numero de últimas mediciones que se desea
     * y devuelve una lista con ese numero de mediciones
     *
     * @param cuantas {Z} - Numero de mediciones que contendrá la lista
     * @param v {Contexto} - Contexto de mainActivity para utilizar sus metodos
     *
     * Utilizamos el metodo de procesarDatosGet para enviar lso datos a mainActivity
     *
     */

    public void obtenerUltimasMediciones(int cuantas, Context v){

        //Log.d("Entrar","entro a obtener ultimas mediciones");
        // Creamos un objeto peticionarioRest para utilizar sus metodos
        PeticionarioREST elPeticionario = new PeticionarioREST();

        //definimos el cuerpo de nuestra peticion
        String textoJSON = "{'Cuantas': '" + cuantas + "' }";
        //montamos la peticion y la enviamos
        elPeticionario.hacerPeticionREST("GET", "http://172.20.10.9/back_endSprint0/src/LogicaNegocio/obtenerUltimasMediciones.php?Cuantas="+cuantas+"", null, new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) throws JSONException {
                try {

                    //Log.d("cuerpoRecibidoPHP",cuerpo);

                    //si ha sido correcta la operacion procesamos los datos
                    ((MainActivity)v).procesarDatosGet(cuerpo);

                }catch (JSONException e){

                    Log.d("JsonException","Error JSON");
                    throw e;
                }

            }
        });


    }


    /**
     *
     * Método que devuelve todas las mediciones de la bbdd
     *
     * @param v {Contexto} - Contexto de mainActivity para utilizar sus metodos
     *
     *Utilizamos el metodo de procesarDatosGet para enviar lso datos a mainActivity
     */

    public void obtenerTodasLasMediciones(Context v){

        // Creamos un objeto peticionarioRest para utilizar sus metodos
        PeticionarioREST elPeticionario = new PeticionarioREST();
        //Log.d("orden","1");

        //hacemos la peticion REST
        elPeticionario.hacerPeticionREST("GET", "http://172.20.10.9/back_endSprint0/src/LogicaNegocio/obtenerTodasLasMediciones.php", null, new PeticionarioREST.RespuestaREST() {

            @Override
            public void callback(int codigo, String cuerpo) throws JSONException {
                //Log.d("orden","2");
                //Log.d("respuesta",cuerpo);

                try {
                    //Log.d("cuerpoRecibidoPHP",cuerpo);

                    //si ha sido correcta la operacion procesamos los datos
                    ((MainActivity)v).procesarDatosGet(cuerpo);
                }catch (JSONException e){
                    Log.d("JsonException","Error JSON");
                    throw e;
                }




            }
        });
        //Log.d("orden","3");





    }



}
