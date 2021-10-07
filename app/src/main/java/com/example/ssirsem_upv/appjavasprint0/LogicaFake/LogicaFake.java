package com.example.ssirsem_upv.appjavasprint0.LogicaFake;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ssirsem_upv.appjavasprint0.MainActivity;
import com.example.ssirsem_upv.appjavasprint0.MedicionPOJO;
import com.example.ssirsem_upv.appjavasprint0.PeticionarioREST;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        elPeticionario.hacerPeticionREST("POST", "http://192.168.0.112/back_endSprint0/LogicaNegocio/guardarMedicion.php", textoJSON,
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

    public List<MedicionPOJO> obtenerTodasLasMediciones(){
        ArrayList<MedicionPOJO> listaTodas = new ArrayList<>();
        return listaTodas;
    }



}
