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

        // ojo: creo que hay que crear uno nuevo cada vez
        PeticionarioREST elPeticionario = new PeticionarioREST();


        String textoJSON = "{\"Medicion\":\""+medicion.getMedicion()+"\", \"Longitud\":\""+medicion.getLongitud() +"\", \"Latitud\": \""+medicion.getLatitud()+"\"}";
        elPeticionario.hacerPeticionREST("POST", "http://10.236.52.203/back_endSprint0/LogicaNegocio/guardarMedicion.php", textoJSON,
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) {
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
     * Devuelve la lista de mediciones al main activity mediante el metodo settearLista, ya que los
     * datos vienen desde un callback referente a RespuestaREST
     *
     */

    public void obtenerUltimasMediciones(int cuantas, Context v){

        Log.d("Entrar","entro a obtener ultimas mediciones");
        PeticionarioREST elPeticionario = new PeticionarioREST();

        //hacemos la peticion REST
        String textoJSON = "{'Cuantas': '" + cuantas + "' }";
        elPeticionario.hacerPeticionREST("GET", "http://10.236.52.203/back_endSprint0/LogicaNegocio/obtenerUltimasMediciones.php?Cuantas="+cuantas+"", null, new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) throws JSONException {
                try {
                    Log.d("cuerpoRecibidoPHP",cuerpo);
                    procesarDatosGet(cuerpo,v);
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
     *Devuelve la lista de mediciones al main activity mediante el metodo settearLista, ya que los
     *datos vienen desde un callback referente a RespuestaREST
     */

    public void obtenerTodasLasMediciones(Context v){

        //declaramos las listas que rellenaremos y el objeto peticionario para la peticion
        List<MedicionPOJO> listaTodas = new ArrayList<>();
        List<String> listaStrings = new ArrayList<>();
        PeticionarioREST elPeticionario = new PeticionarioREST();
        Log.d("orden","1");

        //hacemos la peticion REST
        elPeticionario.hacerPeticionREST("GET", "http://10.236.52.203/back_endSprint0/LogicaNegocio/obtenerTodasLasMediciones.php", null, new PeticionarioREST.RespuestaREST() {

            @Override
            public void callback(int codigo, String cuerpo) throws JSONException {
                Log.d("orden","2");
                //Log.d("respuesta",cuerpo);

                //probamos a convertir la string procedente de la bbdd a jsonarray
                try {
                    JSONArray arrayJSON = new JSONArray(cuerpo);
                    for (int i = 0;i<arrayJSON.length();i++){
                        //llenamos cada posicion de la lista de strings con una string que equivale a un objeto medicion
                        listaStrings.add(arrayJSON.getString(i));
                        //Log.d("cuerpoDentro",arrayJSON.getString(i));
                    }
                }catch (JSONException errorJSON){
                    Log.d("errorJSON","No se ha podido convertir la array de JSON");
                }


                //ahora por cada string en listaStrings creamos un objeto medicion y lo añadimos a la lista
                //de mediciones que enviaremos a main activity
                for (String s :listaStrings
                ) {

                    JSONObject object = new JSONObject(s);

                    MedicionPOJO medicionPOJO = new MedicionPOJO(object.getInt("Medicion"),object.getDouble("Latitud"),object.getDouble("Longitud"));
                    //Log.d("hola",String.valueOf(object.getInt("Medicion")));
                    listaTodas.add(medicionPOJO);
                    //Log.d("lista",listaTodas.toString());
                }

                Log.d("listaProcesada",listaTodas.toString());
                //enviamos la lista procesada
                ((MainActivity)v).settearLista(listaTodas);

            }
        });
        Log.d("orden","3");





    }

    public void procesarDatosGet(String cuerpo , Context v) throws JSONException {

        List<MedicionPOJO> listaTodas = new ArrayList<>();
        List<String> listaStrings = new ArrayList<>();

        //probamos a convertir la string procedente de la bbdd a jsonarray
        try {
            Log.d("arrayJson", "entro al procesar");
            Log.d("arrayJson","----------------"+cuerpo.toString());
            JSONArray arrayJSON = new JSONArray(cuerpo);
            for (int i = 0;i<arrayJSON.length();i++){
                Log.d("arrayJson", "entro al bucle");
                //llenamos cada posicion de la lista de strings con una string que equivale a un objeto medicion
                listaStrings.add(arrayJSON.getString(i));
                //Log.d("cuerpoDentro",arrayJSON.getString(i));
            }
        }catch (JSONException errorJSON){
            Log.d("errorJSON","No se ha podido convertir la array de JSON");
        }


        //ahora por cada string en listaStrings creamos un objeto medicion y lo añadimos a la lista
        //de mediciones que enviaremos a main activity
        for (String s :listaStrings
        ) {

            JSONObject object = new JSONObject(s);

            MedicionPOJO medicionPOJO = new MedicionPOJO(object.getInt("Medicion"),object.getDouble("Latitud"),object.getDouble("Longitud"));
            //Log.d("hola",String.valueOf(object.getInt("Medicion")));
            listaTodas.add(medicionPOJO);
            //Log.d("lista",listaTodas.toString());
        }

        Log.d("listaProcesada",listaTodas.toString());
        //enviamos la lista procesada
        ((MainActivity)v).settearLista(listaTodas);
    }

}
