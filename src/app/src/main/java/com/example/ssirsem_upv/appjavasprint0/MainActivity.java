package com.example.ssirsem_upv.appjavasprint0;
/*
 *
 *MainActivity.java
 *Fecha: 2021/09
 *Autor: Sergi Sirvent Sempere
 *
 *Clase encargada de contener la actividad principal de la aplicacion
 */
// ------------------------------------------------------------------
// ------------------------------------------------------------------

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ssirsem_upv.appjavasprint0.LogicaFake.LogicaFake;

import com.example.ssirsem_upv.appjavasprint0.Utilidades.Utilidades;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
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
    //Views que van a ser usadas en la app
    //----------------------
    EditText txtMediciones;
    EditText txtCuantas;

    //Invocamos a la logica fake para usar sus metodos
    LogicaFake logicaFake = new LogicaFake();

    //variables para el metodo de obtener la localizacion

    private LocationManager locManager;
    public Location loc;
    private double longitud;
    private double latitud;

    //variables para el servicio de beacons
    private Intent elIntentDelServicio = null;

    //Lista interna de mediciones con su getter y su setter
    private List<MedicionPOJO> listaMediciones;

    //Atributos de major y minor
    public int majorMedicion;
    public int minorMedicion;

    /**
     * Getter del atributo privado ListaMediciones
     *
     * @return {Lista<MedicionPOJO>} - Lista de mediciones devuelta
     */
    public List<MedicionPOJO> getListaMediciones() {
        return listaMediciones;
    }
    /**
     * Setter del atributo privado ListaMediciones
     *
     * @param  listaMediciones {Lista<MedicionPOJO>} - Lista de mediciones que quieres settear al atributo
     */
    public void setListaMediciones(List<MedicionPOJO> listaMediciones) {
        this.listaMediciones = listaMediciones;
    }
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
            public void onScanResult(int callbackType, ScanResult resultado) {
                //pasamos resutados a super
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, " buscarTodosLosDispositivosBTL(): onScanResult() ");

                //mostramos la info
                mostrarInformacionDispositivoBTLE(resultado);


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

        delay = false;

        //esto hay que temporizar
            this.elEscanner.startScan(this.callbackDelEscaneo);



    } // ()

    // --------------------------------------------------------------

    /**
     * Este método privado se encarga de mostrar en el log la informacion del dispositivo
     * detectado en el escaner.Recibe un resultad del escaner pero no devuelve nada.
     *
     * @param {ScanResult} resultado - Resultado del escaner previo.
     */
    // --------------------------------------------------------------
    int majorActual = 0;
    boolean primeraVez = true;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void mostrarInformacionDispositivoBTLE(ScanResult resultado) {


        BluetoothDevice bluetoothDevice = resultado.getDevice();
        byte[] bytes = resultado.getScanRecord().getBytes();
        int rssi = resultado.getRssi();
        String nombre = bluetoothDevice.getName() + "";
        if(nombre.equals("sergi")) {


                primeraVez = false;
                TramaIBeacon tib = new TramaIBeacon(bytes);

                Log.d(ETIQUETA_LOG, " ******************");
                Log.d(ETIQUETA_LOG, " ** DISPOSITIVO DETECTADO BTLE ****** ");
                Log.d(ETIQUETA_LOG, " ******************");
                Log.d(ETIQUETA_LOG, " nombre = " + bluetoothDevice.getName());
                Log.d(ETIQUETA_LOG, " toString = " + bluetoothDevice.toString());

        /*
        ParcelUuid[] puuids = bluetoothDevice.getUuids();
        if ( puuids.length >= 1 ) {
            //Log.d(ETIQUETA_LOG, " uuid = " + puuids[0].getUuid());
           // Log.d(ETIQUETA_LOG, " uuid = " + puuids[0].toString());
        }*/

                Log.d(ETIQUETA_LOG, " dirección = " + bluetoothDevice.getAddress());
                Log.d(ETIQUETA_LOG, " rssi = " + rssi);

                Log.d(ETIQUETA_LOG, " bytes = " + new String(bytes));
                Log.d(ETIQUETA_LOG, " bytes (" + bytes.length + ") = " + Utilidades.bytesToHexString(bytes));



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
                Log.d(ETIQUETA_LOG, " ******************");

                //atribuimos los valores del sensor a nuestra medición

                minorMedicion=Utilidades.bytesToInt(tib.getMinor());
                majorMedicion=Utilidades.bytesToInt(tib.getMajor());

                if (primeraVez || majorActual != majorMedicion){
                    majorActual = majorMedicion;
                    primeraVez = false;
                    Log.d("Major","MajorActual--------------------------->" +Utilidades.bytesToInt(tib.getMajor()));

                    // construimos la medicion a guardar
                    MedicionPOJO medicion = new MedicionPOJO(1234,latitud,longitud,minorMedicion);
                    //la guardamos con el metodo de la logica fake
                    logicaFake.guardarMedicion(medicion);
                    Log.d("Major","Medicion Introducida con valor--------------------------->" +Utilidades.bytesToInt(tib.getMinor()));
                }

                /*
                Log.d("Minor","Dato--------------------------->" +Utilidades.bytesToInt(tib.getMinor()) );
                Log.d("Major","DatoMajor--------------------------->" +Utilidades.bytesToInt(tib.getMajor()));
                */




        }

    } // ()


    /**
     * Este método privado se encarga de buscar un dispositivo a partir de su nombre.
     * Recibe un nombre de dispositivo pero no devuelve nada.
     *
     * @param {String} dispositivoBuscado - Nombre del dispositivo buscado.
     */
    // --------------------------------------------------------------
    boolean delay = true;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void buscarEsteDispositivoBTLE(final String dispositivoBuscado) {
        Log.d(ETIQUETA_LOG, " buscarEsteDispositivoBTLE(): empieza ");

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): instalamos scan callback ");


        // super.onScanResult(ScanSettings.SCAN_MODE_LOW_LATENCY, result); para ahorro de energía

        //analizamos el resultado del scanner
        this.callbackDelEscaneo = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult resultado) {
                //pasamos resutados a super
                super.onScanResult(callbackType, resultado);
                Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): onScanResult() ");

                mostrarInformacionDispositivoBTLE(resultado);
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

        ScanFilter sf = new ScanFilter.Builder().setDeviceName(dispositivoBuscado).build();

        Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado);
        //Log.d(ETIQUETA_LOG, "  buscarEsteDispositivoBTLE(): empezamos a escanear buscando: " + dispositivoBuscado
        //      + " -> " + Utilidades.stringToUUID( dispositivoBuscado ) );


        this.elEscanner.startScan(this.callbackDelEscaneo);


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
        if (this.callbackDelEscaneo == null) {
            return;
        }

        //paramos scanner
        this.elEscanner.stopScan(this.callbackDelEscaneo);
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
    public void botonBuscarDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton buscar dispositivos BTLE Pulsado");
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
    public void botonBuscarNuestroDispositivoBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton nuestro dispositivo BTLE Pulsado");
        //this.buscarEsteDispositivoBTLE( Utilidades.stringToUUID( "EPSG-GTI-PROY-3A" ) );

        //this.buscarEsteDispositivoBTLE( "EPSG-GTI-PROY-3A" );
        //llamamos metodo de la logica al pulsar el boton
        Log.d("Control","Etiqueta de control");
        this.buscarEsteDispositivoBTLE("sergi");

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
    public void botonDetenerBusquedaDispositivosBTLEPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton detener busqueda dispositivos BTLE Pulsado");

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

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): habilitado =  " + bta.isEnabled());

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): estado =  " + bta.getState());

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): obtenemos escaner btle ");

        //obtenemos el scanner
        this.elEscanner = bta.getBluetoothLeScanner();

        if (this.elEscanner == null) {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): Socorro: NO hemos obtenido escaner btle  !!!!");

        }

        Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): voy a perdir permisos (si no los tuviera) !!!!");

        //comprobamos los permisos
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_PETICION_PERMISOS);
        } else {
            Log.d(ETIQUETA_LOG, " inicializarBlueTooth(): parece que YA tengo los permisos necesarios !!!!");

        }
    } // ()

    /**
     * Metodo para arrancar el servicio
     *
     * @param v {View} - Vista que le pasamos
     */

    public void botonArrancarServicioPulsado(View v) {
        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado");

        if (this.elIntentDelServicio != null) {
            // ya estaba arrancado
            return;
        }

        Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscuharBeacons.class);

        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        startService(this.elIntentDelServicio);

    } // ()

    // --------------------------------------------------------------

    /**
     * Método para detener el servicio
     *
     * @param v: {View}
     *
     * @return No devuelve nada
     */
    // --------------------------------------------------------------
    public void botonDetenerServicioPulsado(View v) {

        if (this.elIntentDelServicio == null) {
            // no estaba arrancado
            return;
        }

        stopService(this.elIntentDelServicio);

        this.elIntentDelServicio = null;

        Log.d(ETIQUETA_LOG, " boton detener servicio Pulsado");


    } // ()

    // --------------------------------------------------------------

    /**
     * Método para obtener la latitud y la longitud
     *
     *
     */
    // --------------------------------------------------------------
    public void obtenerLatitudLongitud() {

        //HE TENIDO PROBLEMAS CON EL EMULADOR PARA
        //OBTENER LA LATITUD Y LONGITUD REAL ASI QUE SERAN
        //VALORES FIJOS

        //Por ahora como el emulador devuelve una latitud y longitud NULL, le paso a los
        //atributos la latitud y longitud de la EPSG


        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc==null){

                //Como tengo problemas con el emulador aqui le atribuyo los valores fijos de la
                //latitud y longitud de la EPSG

                latitud =39.482369;
                longitud  = -0.343578;

            }else{
                latitud = loc.getLatitude();
                longitud = loc.getLongitude();
            }
        }


    }


    /**
     * Metodo de guardar medicion que llama a la logica fake
     *
     * @param v {View} - Boton que se corresponde a la funcion
     */

    public void onClickBotonGuardarMediciones(View v){

        Log.d("","Boton guardar medicion pulsado");
        if(txtMediciones.getText().toString().equals("")){
            Toast.makeText(this,"Introduce un valor de medicion", Toast.LENGTH_SHORT).show();
        }else {
            MedicionPOJO medicion = new MedicionPOJO(Integer.parseInt(txtMediciones.getText().toString()),latitud,longitud,minorMedicion);
            logicaFake.guardarMedicion(medicion);
            Toast.makeText(this, "Se ha añadido la medicion con valor: "+ txtMediciones.getText().toString(), Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * Metodo de obtener las ultimas mediciones  que llama a la logica fake
     *
     * @param v {View} - Boton que se corresponde a la funcion
     */

    public void onClickBotonObtenerUltimasMediciones(View v){
        Log.d("","Boton obtener ultimas pulsado");

        if (txtCuantas.getText().toString().equals("")){
            Toast.makeText(this,"Introduce un valor de cuantas mediciones quieres", Toast.LENGTH_SHORT).show();
        }else{

            logicaFake.obtenerUltimasMediciones(Integer.parseInt(txtCuantas.getText().toString()),this);
        }



    }

    /**
     * Metodo de obtener todas las mediciones que llama a la logica fake
     *
     * @param v {View} - Boton que se corresponde a la funcion
     */

    public void onClickBotonObtenerTodasLasMediciones(View v) throws JSONException {

        Log.d("","Boton obtener todas pulsado");
        logicaFake.obtenerTodasLasMediciones(this);
        //Log.d("resul",listaM.toString());

    }


    /**
     * Metodo que se encarga de settear lso valores procedentes de la logica fake
     * a la lista global de main activity
     *
     * @param listaMediciones
     */
    public void settearLista(List<MedicionPOJO> listaMediciones){

        Log.d("resul",listaMediciones.toString());
        //seteamos la nueva lista como lista atributo
        setListaMediciones(listaMediciones);
        Log.d("resul2",this.listaMediciones.toString());

        //lista para pasar en el intent
        List<MedicionPOJO> object = getListaMediciones();
        Log.d("size",String.valueOf(object.size()));

        //creamos el intent y le pasamos la lista para que se abra la actividad MostrarMedicionesActivity
        Intent intent = new Intent(this, MostrarMedicionesActivity.class);
        intent.putExtra("miLista", (Serializable) object);
        startActivity(intent);


    }

    /**
     * Método que se encarga de procesar los datos procedentes de los metodos que utilizan GET en la
     * logica fake. Recibe una string de objetos JSON y acaba pasando una lista de mediciones a settearLista
     *
     *
     * @param cuerpo
     * @throws JSONException
     */

    public void procesarDatosGet(String cuerpo) throws JSONException {

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

        Log.d("listaProcesada",listaTodas.toString());
        //enviamos la lista procesada
        settearLista(listaTodas);
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
        //obtenemos latitud y longitud
        obtenerLatitudLongitud();
        Log.d(ETIQUETA_LOG, " onCreate(): empieza ");

        inicializarBlueTooth();//llamamos metodo para inicializar el BlueTooth

        Log.d(ETIQUETA_LOG, " onCreate(): termina ");

        txtMediciones = findViewById(R.id.txtMediciones);
        txtCuantas = findViewById(R.id.editTextCuantasMediciones);



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


