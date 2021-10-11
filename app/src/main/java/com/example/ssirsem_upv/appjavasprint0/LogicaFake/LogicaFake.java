package com.example.ssirsem_upv.appjavasprint0.LogicaFake;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ssirsem_upv.appjavasprint0.MedicionPOJO;
import com.example.ssirsem_upv.appjavasprint0.PeticionarioREST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public  class LogicaFake extends AppCompatActivity {

    private ArrayList<MedicionPOJO> listaMediciones;

    public ArrayList<MedicionPOJO> getListaMediciones() {
        return listaMediciones;
    }

    public void setListaMediciones(ArrayList<MedicionPOJO> listaMediciones) {
        this.listaMediciones = listaMediciones;
    }

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
     * @return listaCuantas {Lista<MedicionPOJO>} - Lista de todas las mediciones de la bbdd
     */

    public ArrayList<MedicionPOJO> obtenerTodasLasMediciones(){

        ArrayList<MedicionPOJO> listaTodas = new ArrayList<>();
        ArrayList<String> listaStrings = new ArrayList<>();
        PeticionarioREST elPeticionario = new PeticionarioREST();
        Log.d("orden","1");

        elPeticionario.hacerPeticionREST("GET", "http://192.168.1.41/back_endSprint0/LogicaNegocio/obtenerTodasLasMediciones.php", null, new PeticionarioREST.RespuestaREST() {

            @Override
            public void callback(int codigo, String cuerpo) throws JSONException {
                Log.d("orden","2");
                //Log.d("respuesta",cuerpo);

                try {
                    JSONArray arrayJSON = new JSONArray(cuerpo);
                    for (int i = 0;i<arrayJSON.length();i++){
                        listaStrings.add(arrayJSON.getString(i));
                        //Log.d("cuerpoDentro",arrayJSON.getString(i));
                    }
                }catch (JSONException errorJSON){
                    Log.d("errorJSON","No se ha podido convertir la array de JSON");
                }


                for (String s :listaStrings
                ) {

                    JSONObject object = new JSONObject(s);

                    MedicionPOJO medicionPOJO = new MedicionPOJO(object.getInt("Medicion"),object.getDouble("Latitud"),object.getDouble("Longitud"));
                    //Log.d("hola",String.valueOf(object.getInt("Medicion")));
                    listaTodas.add(medicionPOJO);
                    //Log.d("lista",listaTodas.toString());
                }
                setListaMediciones(listaTodas);
                Log.d("listaProcesada",listaTodas.toString());


            }
        });
        Log.d("orden","3");
        return listaTodas;




    }



}
