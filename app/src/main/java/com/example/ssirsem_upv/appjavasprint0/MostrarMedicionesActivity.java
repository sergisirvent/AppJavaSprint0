package com.example.ssirsem_upv.appjavasprint0;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MostrarMedicionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mostrarmediciones);
        TextView textView = findViewById(R.id.tVMediciones);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra("Mensaje");
        String tipoMetodo = intent.getStringExtra("MetodoUtilizado");
        if (Integer.parseInt(tipoMetodo) == 1){

            // Capture the layout's TextView and set the string as its text

            textView.setText("Has guardado la medicion " + "\n"+ message);

        }else if (Integer.parseInt(tipoMetodo) == 2){

            // Capture the layout's TextView and set the string as its text

            textView.setText("Estas son todas las mediciones " +"\n" + message);

        }else{

            textView.setText("Estas son las mediciones que has pedido" +"\n" + message);

        }

    }

    public void volverMain(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
