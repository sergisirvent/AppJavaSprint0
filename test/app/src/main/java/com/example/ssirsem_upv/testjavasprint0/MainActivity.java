package com.example.ssirsem_upv.testjavasprint0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ssirsem_upv.testjavasprint0.Test.LogicaFakeTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /////////////////////////////////////////////////////////////////////////////////////
        /*
         * TEST LOGICA
         * 1.NO SE PUEDEN HACER LOS DOS TEST A LA VEZ
         * 2.SE DESCOMENTA UNO Y SE HACE
         * 3.AL FINALIZAR EL TEST EJECUTAR EN POSTMAN DELETE FROM `mediciones` WHERE `Medicion`=23
         *
         */
        LogicaFakeTest testLogica = new LogicaFakeTest();

        //TEST 1 - PRUEBA GUARDARMEDICION() Y OBTENERTODASLASMEDICIONES()
        //testLogica.hacerTestGuardarMedicionYObtenerTodas();

        //TEST 2 - PRUEBA OBTENERULTIMASMEDICIONES()
        testLogica.hacerTestObtenerUltimasLasMediciones();




        /////////////////////////////////////////////////////////////////////////////////////

    }
}