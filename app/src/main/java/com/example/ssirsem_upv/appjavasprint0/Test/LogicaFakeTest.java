package com.example.ssirsem_upv.appjavasprint0.Test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ssirsem_upv.appjavasprint0.MainActivity;
import com.example.ssirsem_upv.appjavasprint0.MedicionPOJO;
import com.example.ssirsem_upv.appjavasprint0.MostrarMedicionesActivity;
import com.example.ssirsem_upv.appjavasprint0.PeticionarioREST;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class LogicaFakeTest extends AppCompatActivity {

    private List<MedicionPOJO> listaMedicionesTest = new ArrayList<>();

    public List<MedicionPOJO> getListaMedicionesTest() {
        return listaMedicionesTest;
    }

    public void setListaMedicionesTest(List<MedicionPOJO> listaMedicionesTest) {
        this.listaMedicionesTest = listaMedicionesTest;
    }

    /**
     *
     * Método de la lógica fake que recibe una medición y conecta con guardarMedicion.php.
     *
     * @param medicion {MedicionPOJO} - Medicion para subir a la bbdd
     */

    public void guardarMedicionTest(MedicionPOJO medicion){

        // Creamos un objeto peticionarioRest para utilizar sus metodos
        PeticionarioREST elPeticionario = new PeticionarioREST();


        //definimos el cuerpo de nuestra peticion
        String textoJSON = "{\"Medicion\":\""+medicion.getMedicion()+"\", \"Longitud\":\""+medicion.getLongitud() +"\", \"Latitud\": \""+medicion.getLatitud()+"\"}";
        //montamos la peticion y la enviamos
        elPeticionario.hacerPeticionREST("POST", "http://192.168.1.34/back_endSprint0/test/guardarMedicionTest.php", textoJSON,
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        //si ha sido correcta la operacion
                        Log.d("Test","Medición subida correctamente a la base de datos");
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
     *
     *
     * Utilizamos el metodo de procesarDatosGet para enviar lso datos a mainActivity
     *
     */

    public void obtenerUltimasMedicionesTest(int cuantas){

        //Log.d("Entrar","entro a obtener ultimas mediciones");
        // Creamos un objeto peticionarioRest para utilizar sus metodos
        PeticionarioREST elPeticionario = new PeticionarioREST();

        //definimos el cuerpo de nuestra peticion
        String textoJSON = "{'Cuantas': '" + cuantas + "' }";
        //montamos la peticion y la enviamos
        elPeticionario.hacerPeticionREST("GET", "http://192.168.1.34/back_endSprint0/test/obtenerUltimasMedicionesTest.php?Cuantas="+cuantas+"", null, new PeticionarioREST.RespuestaREST() {
            @Override
            public void callback(int codigo, String cuerpo) throws JSONException {
                try {

                    //Log.d("cuerpoRecibidoPHP",cuerpo);

                    //si ha sido correcta la operacion procesamos los datos
                    procesarDatosGetTest(cuerpo);

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
     *
     *
     *Utilizamos el metodo de procesarDatosGet para enviar lso datos a mainActivity
     */

    public void obtenerTodasLasMedicionesTest(){

        // Creamos un objeto peticionarioRest para utilizar sus metodos
        PeticionarioREST elPeticionario = new PeticionarioREST();
        //Log.d("orden","1");

        //hacemos la peticion REST
        elPeticionario.hacerPeticionREST("GET", "http://192.168.1.34/back_endSprint0/test/obtenerTodasLasMedicionesTest.php", null, new PeticionarioREST.RespuestaREST() {

            @Override
            public void callback(int codigo, String cuerpo) throws JSONException {
                //Log.d("orden","2");
                //Log.d("respuesta",cuerpo);

                try {
                    //Log.d("cuerpoRecibidoPHP",cuerpo);

                    //si ha sido correcta la operacion procesamos los datos
                    procesarDatosGetTest(cuerpo);
                }catch (JSONException e){
                    Log.d("JsonException","Error JSON");
                    throw e;
                }




            }
        });
        //Log.d("orden","3");





    }


    /**
     *
     * Método que borra todas las mediciones de la base de datos
     *
     *
     */

    public void borrarTodasLasMedicionesTest(){

        // Creamos un objeto peticionarioRest para utilizar sus metodos
        PeticionarioREST elPeticionario = new PeticionarioREST();


        //montamos la peticion y la enviamos
        elPeticionario.hacerPeticionREST("DELETE", "http://192.168.1.34/back_endSprint0/test/borrarTodasLasMedicionesTest", null,
                new PeticionarioREST.RespuestaREST() {
                    @Override
                    public void callback(int codigo, String cuerpo) {
                        //si ha sido correcta la operacion
                        Log.d("","Borrado correcto");
                    }
                }
        );


    }

    /**
     * Método que se encarga de procesar los datos procedentes de los metodos que utilizan GET en la
     * logica fake. Recibe una string de objetos JSON y acaba pasando una lista de mediciones a settearLista
     *
     *
     * @param cuerpo
     * @throws JSONException
     */

    public void procesarDatosGetTest(String cuerpo) throws JSONException {

        List<MedicionPOJO> listaTodas = new ArrayList<>();
        List<String> listaStrings = new ArrayList<>();

        //probamos a convertir la string procedente de la bbdd a jsonarray
        try {
            //Log.d("arrayJson", "entro al procesar");
            //Log.d("arrayJson","----------------"+cuerpo.toString());
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

        Log.d("PrimerTest",listaTodas.toString());
        //enviamos la lista procesada
        settearListaTest(listaTodas);
    }

    /**
     * Metodo que se encarga de settear lso valores procedentes de la logica fake
     * a la lista global de main activity
     *
     * @param listaMediciones
     */
    public void settearListaTest(List<MedicionPOJO> listaMediciones){

        Log.d("PrimerTest",listaMediciones.toString());
        //seteamos la nueva lista como lista atributo
        setListaMedicionesTest(listaMediciones);
        if ( this.listaMedicionesTest.size() == 1){
            Log.d("PrimerTest","El tamaño de la lista atributo ha cambiado a 1");
            Log.d("PrimerTest","PrimerTest Completado con exito");
        }
        if (this.listaMedicionesTest.size() == 2){
            Log.d("SegundoTest","Devuelve una lista con dos mediciones");
            for (MedicionPOJO m:listaMediciones
                 ) {
                Log.d("SegundoTest","Valor Latitud y Longitud En Lista Final -----> " + String.valueOf(m.getLatitud()) + String.valueOf(m.getLongitud()));
            }
            Log.d("SegundoTest","SegundoTest Completado con éxito");
        }


    }

    public void hacerTestGuardarMedicionYObtenerTodas(){

        //creo una medicion de prueba
        MedicionPOJO medicion = new MedicionPOJO(23,23,23);
        Log.d("PrimerTest","Medicion a Guardar --------> "+String.valueOf(medicion.getMedicion())+" "+ String.valueOf(medicion.getLatitud())+" "+String.valueOf(medicion.getLongitud()));
        //Guardo una medicion y obtengo todas
        guardarMedicionTest(medicion);
        //Obtengo todas las mediciones de la base de datos
        obtenerTodasLasMedicionesTest();



        //----------------------------IMPORTANTE-----------------------------//
        //Elimino todas las medidas para limpiar la bbdd
        //En POSTMAN --> DELETE FROM `mediciones` WHERE `Medicion`=23
        //Para dejar la base de datos test vacia

    }

    public void hacerTestObtenerUltimasLasMediciones(){

        //creo unas mediciones de prueba
        MedicionPOJO medicion1 = new MedicionPOJO(23,23,23);
        MedicionPOJO medicion2 = new MedicionPOJO(23,24,24);
        MedicionPOJO medicion3 = new MedicionPOJO(23,25,25);
        Log.d("SegundoTest","Medicion a Guardar --------> "+String.valueOf(medicion1.getMedicion())+" "+ String.valueOf(medicion1.getLatitud())+" "+String.valueOf(medicion1.getLongitud()));
        Log.d("SegundoTest","Medicion a Guardar --------> "+String.valueOf(medicion2.getMedicion())+" "+ String.valueOf(medicion2.getLatitud())+" "+String.valueOf(medicion2.getLongitud()));
        Log.d("SegundoTest","Medicion a Guardar --------> "+String.valueOf(medicion3.getMedicion())+" "+ String.valueOf(medicion3.getLatitud())+" "+String.valueOf(medicion3.getLongitud()));

        //Guardo las mediciones
        guardarMedicionTest(medicion1);
        guardarMedicionTest(medicion2);
        guardarMedicionTest(medicion3);

        //obtengo las ultimas dos mediciones que corresponderan a 25 y 24 de valor de medicion
        obtenerUltimasMedicionesTest(2);


        //----------------------------IMPORTANTE-----------------------------//
        //Elimino todas las medidas para limpiar la bbdd
        //En POSTMAN --> DELETE FROM `mediciones` WHERE `Medicion`=23
        //Para dejar la base de datos test vacia


    }
}
