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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mostrarmediciones);
        TextView textView = findViewById(R.id.tVMediciones);
        textView.setText("");
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String tipoMetodo = intent.getStringExtra("MetodoUtilizado");

        if (Integer.parseInt(tipoMetodo) == 1){

            String mensaje = intent.getStringExtra("Mensaje");
            textView.setText("Se ha añadido la medicion con valor: "+mensaje);


        }else{

            List<MedicionPOJO> listaMediciones = (List<MedicionPOJO>) intent.getSerializableExtra("miLista");
            Log.d("listaAndroid",String.valueOf(listaMediciones.size()));
            for (int i = 0;i<listaMediciones.size();i++){
                textView.append("Medicion: "+String.valueOf(listaMediciones.get(i).getMedicion())+
                        " Longitud: "+String.valueOf(listaMediciones.get(i).getLongitud())+
                        " Latitud: "+String.valueOf(listaMediciones.get(i).getLatitud())+"\n");
            }
        }


    }

    public void volverMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
