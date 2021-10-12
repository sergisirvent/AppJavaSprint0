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
        elPeticionario.hacerPeticionREST("POST", "http://192.168.1.41/back_endSprint0/LogicaNegocio/guardarMedicion.php", textoJSON,
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
     * @return listaCuantas {Lista<MedicionPOJO>} - Lista de las ultimas mediciones pedidas
     */

    public List<MedicionPOJO> obtenerUltimasMediciones(int cuantas){
        ArrayList<MedicionPOJO> listaCuantas = new ArrayList<>();
        return listaCuantas;
    }


    /**
     *
     * Método que devuelve todas las mediciones de la bbdd
     *
     * @param v {Contexto} - Contexto de mainActivity para utilizar sus metodos
     *
     * @return listaCuantas {Lista<MedicionPOJO>} - Lista de todas las mediciones de la bbdd, no la devuelve exactamente pero modifica
     * la lista global de mainActivity
     */

    public void obtenerTodasLasMediciones(Context v){

        //declaramos las listas que rellenaremos y el objeto peticionario para la peticion
        List<MedicionPOJO> listaTodas = new ArrayList<>();
        List<String> listaStrings = new ArrayList<>();
        PeticionarioREST elPeticionario = new PeticionarioREST();
        Log.d("orden","1");

        elPeticionario.hacerPeticionREST("GET", "http://192.168.1.41/back_endSprint0/LogicaNegocio/obtenerTodasLasMediciones.php", null, new PeticionarioREST.RespuestaREST() {

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



}
