/*
 *
 *TramaIBeacon.java
 *Fecha: 2021/09
 *Autor: Sergi Sirvent Sempere
 *
 *Clase encargada de tratar los datos procedentes de la captura de un beacon
 */
package com.example.ssirsem_upv.appjavasprint0;

import java.util.Arrays;

// -----------------------------------------------------------------------------------
// 
// -----------------------------------------------------------------------------------
public class TramaIBeacon {

    //--------------------------------------------------------------------------------
    //Atributos privados de la clase TramaIBeacon
    //--------------------------------------------------------------------------------
    private byte[] prefijo = null; // 9 bytes
    private byte[] uuid = null; // 16 bytes
    private byte[] major = null; // 2 bytes
    private byte[] minor = null; // 2 bytes
    private byte txPower = 0; // 1 byte

    private byte[] losBytes;

    private byte[] advFlags = null; // 3 bytes
    private byte[] advHeader = null; // 2 bytes
    private byte[] companyID = new byte[2]; // 2 bytes
    private byte iBeaconType = 0 ; // 1 byte
    private byte iBeaconLength = 0 ; // 1 byte

    // -------------------------------------------------------------------------------
    //A continuacion se definen los getters de cada atributo de la clase
    // -------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo prefijo de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getPrefijo() {
        return prefijo;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo uuid de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getUUID() {
        return uuid;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo major de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getMajor() {
        return major;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo minor de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getMinor() {
        return minor;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo txPower de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte getTxPower() {
        return txPower;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo losBytes de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getLosBytes() {
        return losBytes;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo advFlags de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getAdvFlags() {
        return advFlags;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo advHeader de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getAdvHeader() {
        return advHeader;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo companyID de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte[] getCompanyID() {
        return companyID;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo iBeaconType de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte getiBeaconType() {
        return iBeaconType;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método devuelve una lista de bytes que equivalen al atributo iBeaconLength de la clase
     *
     * @returns {Lista<Byte>} Lista de bytes devuelta.
     */
    // -------------------------------------------------------------------------------
    public byte getiBeaconLength() {
        return iBeaconLength;
    }

    // -------------------------------------------------------------------------------
    /**
     * Este método es el constructor de la clase, recibe una lista de bytes y extrae de ella
     * la información para cada uno de sus atributos privados
     *
     * @param  {Lista<Byte>} Lista de bytes que contiene la información.
     */
    // -------------------------------------------------------------------------------
    public TramaIBeacon(byte[] bytes ) {
        this.losBytes = bytes;

        //separamos la cadena de bytes y vamos cogiendo los bytes que
        //nos interesan para rellenar los atributos

        prefijo = Arrays.copyOfRange(losBytes, 0, 8+1 ); // 9 bytes
        uuid = Arrays.copyOfRange(losBytes, 9, 24+1 ); // 16 bytes
        major = Arrays.copyOfRange(losBytes, 25, 26+1 ); // 2 bytes
        minor = Arrays.copyOfRange(losBytes, 27, 28+1 ); // 2 bytes
        txPower = losBytes[ 29 ]; // 1 byte

        advFlags = Arrays.copyOfRange( prefijo, 0, 2+1 ); // 3 bytes
        advHeader = Arrays.copyOfRange( prefijo, 3, 4+1 ); // 2 bytes
        companyID = Arrays.copyOfRange( prefijo, 5, 6+1 ); // 2 bytes
        iBeaconType = prefijo[ 7 ]; // 1 byte
        iBeaconLength = prefijo[ 8 ]; // 1 byte

    } // ()
} // class
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------


