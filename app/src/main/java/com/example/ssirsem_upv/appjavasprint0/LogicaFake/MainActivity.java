
package com.example.ssirsem_upv.appjavasprint0.LogicaFake;
// ------------------------------------------------------------------
// ------------------------------------------------------------------

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ssirsem_upv.appjavasprint0.R;
import com.example.ssirsem_upv.appjavasprint0.Ejemplos.TramaIBeacon;
import com.example.ssirsem_upv.appjavasprint0.Utilidades.Utilidades;

import java.util.List;

// ------------------------------------------------------------------
// ------------------------------------------------------------------

public class MainActivity extends AppCompatActivity {

    // --------------------------------------------------------------
    //Se definen una String identificativa para el log y un codigo que nos facilita
    //la peticion de permisos
    // --------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";

    private static final int CODIGO_PETICION_PERMISOS = 11223344;

    // --------------------------------------------------------------
    //Declaracion del objeto BluetoothLeScanner y declaracion e inicialización a null del
    //objeto ScanCallback
    // --------------------------------------------------------------
    private BluetoothLeScanner elEscanner;

    private ScanCallback callbackDelEscaneo = null;

//-------------------------------------

    //----------------------
    EditText txtMediciones;
    EditText txtLatitud;
    EditText txtLongitud;
    TextView textViewResultado;


    // --------------------------------------------------------------
    /**
     * Este método privado instala el scancallback y en el caso de haber dispositivos
     * muestra su informacion y en caso de no haberlos indica que no hay.No recibe ni devuelve nada.
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void buscarTodosLosDispositivosBTLE() {
        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): empieza ");

        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): instalamos scan callback ");

        this.callbackDelEscaneo = new ScanCallback() {

            //analizamos el resultado del scanner

            @Override
            public void onScanResult( int callbackType, ScanResult resultado ) {
                //pasamos resutados a super
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onScanResult() ");

                //mostramos la info
                mostrarInformacionDispositivoBTLE( resultado );
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                //pasamos resutados a super
                super.onBatchScanResults(results);
                //mensaje de batch
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                //pasamos resutados a super
                super.onScanFailed(errorCode);
                //mensaje de error
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onScanFailed() ");

            }
        };

        Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): empezamos a escanear ");

        this.elEscanner.startScan( this.callbackDelEscaneo);

    } // ()

    // --------------------------------------------------------------
    /**
     * Este método privado se encarga de mostrar en el log la informacion del dispositivo
     * detectado en el escaner.Recibe un resultad del escaner pero no devuelve nada.
     *
     * @param {ScanResult} resultado - Resultado del escaner previo.
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado ) {

        //obtenemos el dispositivo y sus datos
        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        int rssi = resultado.getRssi();

        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " ****** DISPOSITIVO DETECTADO BTLE ****************** ");
        Log.d(ETIQUETA_LOG, " ****************************************************");
        Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
        Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());

        /*
        ParcelUuid[] puuids = bluetoothDevice.getUuids();
        if ( puuids.length >= 1 ) {
            //Log.d(ETIQUETA_LOG, " uuid = " + puuids[0].getUuid());
           // Log.d(ETIQUETA_LOG, " uuid = " + puuids[0].toString());
        }*/

        Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
        Log.d(ETIQUETA_LOG, " rssi = " + rssi );

        Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
        Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));

        //creamos trama beacon
        TramaIBeacon tib = new TramaIBeacon(bytes);

        Log.d(ETIQUETA_LOG, " ----------------------------------------------------");
        Log.d(ETIQUETA_LOG, " prefijo  = " + Utilidades.bytesToHexString(tib.getPrefijo()));
        Log.d(ETIQUETA_LOG, "          advFlags = " + Utilidades.bytesToHexString(tib.getAdvFlags()));
        Log.d(ETIQUETA_LOG, "          advHeader = " + Utilidades.bytesToHexString(tib.getAdvHeader()));
        Log.d(ETIQUETA_LOG, "          companyID = " + Utilidades.bytesToHexString(tib.getCompanyID()));
        Log.d(ETIQUETA_LOG, "          iBeacon type = " + Integer.toHexString(tib.getiBeaconType()));
        Log.d(ETIQUETA_LOG, "          iBeacon length 0x = " + Integer.toHexString(tib.getiBeaconLength()) + " ( "
                + tib.getiBeaconLength() + " ) ");
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToHexString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " uuid  = " + Utilidades.bytesToString(tib.getUUID()));
        Log.d(ETIQUETA_LOG, " major  = " + Utilidades.bytesToHexString(tib.getMajor()) + "( "
                + Utilidades.bytesToInt(tib.getMajor()) + " ) ");
        Log.d(ETIQUETA_LOG, " minor  = " + Utilidades.bytesToHexString(tib.getMinor()) + "( "
                + Utilidades.bytesToInt(tib.getMinor()) + " ) ");
        Log.d(ETIQUETA_LOG, " txPower  = " + Integer.toHexString(tib.getTxPower()) + " ( " + tib.getTxPower() + " )");
        Log.d(ETIQUETA_LOG, " ****************************************************");

    } // ()

    // --------------------------------------------------------------
    /**
     * Este método privado se encarga de buscar un dispositivo a partir de su nombre.
     * Recibe un nombre de dispositivo pero no devuelve nada.
     *
     * @param {String} dispositivoBuscado - Nombre del dispositivo buscado.
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void buscarEsteDispositivoBTLE(final String dispositivoBuscado ) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");


        // super.onScanResult(ScanSettings.SCAN_MODE_LOW_LATENCY, result); para ahorro de energía

        //analizamos el resultado del scanner
        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult( int callbackType, ScanResult resultado ) {
                //pasamos resutados a super
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");

                mostrarInformacionDispositivoBTLE( resultado );
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                //pasamos resutados a super
                super.onBatchScanResults(results);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onBatchScanResults() ");

            }

            @Override
            public void onScanFailed(int errorCode) {
                //pasamos resutados a super
                super.onScanFailed(errorCode);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanFailed() ");

            }
        };

        ScanFilter sf = new ScanFilter.Builder().setDeviceName( dispositivoBuscado ).build();

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado );
        //Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado
          //      + " -> " + Utilidades.stringToUUID( dispositivoBuscado ) );

        this.elEscanner.startScan( this.callbackDelEscaneo );
    } // ()

    // --------------------------------------------------------------
    /**
     * Este método privado se encarga de detener la busqueda de dispositivos Bluetooth.
     * No recibe ni devuelve nada.
     *
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void detenerBusquedaDispositivosBTLE() {

        //si el callback esta en null paramos con return
        if ( this.callbackDelEscaneo == null ) {
            return;
        }

        //paramos scanner
        this.elEscanner.stopScan( this.callbackDelEscaneo );
        //ponemos el callback en null
        this.callbackDelEscaneo = null;

    } // ()

    // --------------------------------------------------------------
    /**
     * Este método público se encarga de buscar el dispositivo que haya pulsado el usuario.
     * Recibe una vista refiriendose a la vista que pulsa el usuario en su interfaz.
     *
     * @param {View} v - Vista referente al interfaz del usuario
     *
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void botonBuscarDispositivosBTLEPulsado(View v ) {
        Log.d(ETIQUETA_LOG, " boton buscar dispositivos BTLE Pulsado" );
        //llamamos metodo de la logica al pulsar el boton
        this.buscarTodosLosDispositivosBTLE();
    } // ()

    // --------------------------------------------------------------
    /**
     * Este método público se encarga de buscar el dispositivo que haya pulsado el usuario.
     * Recibe una vista refiriendose a la vista que pulsa el usuario en su interfaz.
     *
     * @param {View} v - Vista referente al boton del interfaz del usuario
     *
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void botonBuscarNuestroDispositivoBTLEPulsado(View v ) {
        Log.d(ETIQUETA_LOG, " boton nuestro dispositivo BTLE Pulsado" );
        //this.buscarEsteDispositivoBTLE( Utilidades.stringToUUID( "EPSG-GTI-PROY-3A" ) );

        //this.buscarEsteDispositivoBTLE( "EPSG-GTI-PROY-3A" );
        //llamamos metodo de la logica al pulsar el boton
        this.buscarEsteDispositivoBTLE( "fistro" );

    } // ()

    // --------------------------------------------------------------
    /**
     * Este método público se encarga de detener la busqueda de dispositivos gracias al boton que
     * pulsa el usuario.
     * Recibe una vista refiriendose a la vista que pulsa el usuario en su interfaz.
     *
     * @param {View} v - Vista referente al boton del interfaz del usuario
     *
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void botonDetenerBusquedaDispositivosBTLEPulsado(View v ) {
        Log.d(ETIQUETA_LOG, " boton detener busqueda dispositivos BTLE Pulsado" );

        //llamamos metodo de la logica al pulsar el boton
        this.detenerBusquedaDispositivosBTLE();
    } // ()

    // --------------------------------------------------------------
    /**
     * Este método privado se encarga de inicializar el proceso de deteccion por bluetooth
     * Este metodo no recibe ni devuelve nada.
     *
     * @param {View} v - Vista referente al boton del interfaz del usuario
     *
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void inicializarBlueTooth() {
        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos adaptador BT ");

        //creamos el adapter
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitamos adaptador BT ");

        //lo activamos
        bta.enable();

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState() );

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        //obtenemos el scanner
        this.elEscanner = bta.getBluetoothLeScanner();

        if ( this.elEscanner == null ) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): voy a perdir permisos (si no los tuviera) !!!!");

        //comprobamos los permisos
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PETICION_PERMISOS);
        }
        else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");

        }
    } // ()


    // --------------------------------------------------------------



    public void guardarMedicion(View quien) {

            Log.d("clienterestandroid", "boton_enviar_pulsado");


            // ojo: creo que hay que crear uno nuevo cada vez
            PeticionarioREST elPeticionario = new PeticionarioREST();


            String textoJSON = "{\"Medicion\":\""+txtMediciones.getText()+"\", \"Longitud\":\""+txtLongitud.getText() +"\", \"Latitud\": \""+txtLatitud.getText()+"\"}";
            elPeticionario.hacerPeticionREST("POST", "http://192.168.0.113/back_endSprint0/LogicaNegocio/guardarMedicion.php", textoJSON,
                    new PeticionarioREST.RespuestaREST() {
                        @Override
                        public void callback(int codigo, String cuerpo) {
                            textViewResultado.setText("codigo respuesta= " + codigo + " <-> \n" + cuerpo + "SE HA AÑADIDO EL NUMERO"+ txtMediciones.getText());
                        }
                    }
            );



    }



    /**
     *
     * Metodo onCreate de la aplicacion
     */
    // --------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //establecemos la vista
        setContentView(R.layout.activity_main);

        Log.d(ETIQUETA_LOG, " onCreate(): empieza ");

        inicializarBlueTooth();//llamamos metodo para inicializar el BlueTooth

        Log.d(ETIQUETA_LOG, " onCreate(): termina ");

        txtMediciones = findViewById(R.id.txtMediciones);
        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);
        textViewResultado = findViewById(R.id.textViewResultado);

    } // onCreate()

    // --------------------------------------------------------------

    /**
     * Este metodo se ejecuta una vez se obtiene una respuesta a si se tienen los permisos o no,
     * gracias a esto te facilita el estado de tus permisos.
     *
     * @param {Z} requestCode - Codigo de solicitud
     * @param {Lista<Texto>} permissions - Lista de permisos
     * @param {Lista<Z>} grantResults - Lista de resultados
     */
    // --------------------------------------------------------------
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults);

        //analizamos el codigo de solicitud
        switch (requestCode) {
            case CODIGO_PETICION_PERMISOS:

                //Si la solicitud se cancela, la array de resultados estara vacia
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): permisos concedidos  !!!!");

                    //El permiso esta concedido. Continua el flujo de trabajo de tu app
                }  else {

                    Log.d(ETIQUETA_LOG, " onRequestPermissionResult(): Socorro: permisos NO concedidos  !!!!");

                }
                return;
        }
        // Estas lineas son por si acaso se deben comprobar otros permisos de tu app
        
    } // ()

} // class
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------
// --------------------------------------------------------------


