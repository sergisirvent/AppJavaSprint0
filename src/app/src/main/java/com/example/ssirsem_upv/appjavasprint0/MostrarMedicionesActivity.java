/*
 *
 *MostrarMedicionesActivity.java
 *Fecha: 2021/10
 *Autor: Sergi Sirvent Sempere
 *
 *Clase encargada de manejar la actividad donde se muestran las mediciones
 */
package com.example.ssirsem_upv.appjavasprint0;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MostrarMedicionesActivity extends AppCompatActivity {

    /**
     * Metodo onCreate de la actividad
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.mostrarmediciones);
        //buscamos la view del textView
        TextView textView = findViewById(R.id.tVMediciones);
        textView.setText("");

        // Cogemos el intent de main y lo procesamos
        Intent intent = getIntent();

        //obtenemos la lista del intent
        List<MedicionPOJO> listaMediciones = (List<MedicionPOJO>) intent.getSerializableExtra("miLista");
            //Log.d("listaAndroid",String.valueOf(listaMediciones.size()));

        //añadimos cada medicion de la lista al text view
        for (int i = 0;i<listaMediciones.size();i++){
            textView.append("Medicion: "+String.valueOf(listaMediciones.get(i).getMedicion())+
                    " Longitud: "+String.valueOf(listaMediciones.get(i).getLongitud())+
                    " Latitud: "+String.valueOf(listaMediciones.get(i).getLatitud())+"\n");
        }


    }

    /**
     * Metodo que se encarga de que al pulsar el boton de volver se vuelva al main
     *
     * @param v {View} - Vista que recibe el metodo
     */
    public void volverMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
