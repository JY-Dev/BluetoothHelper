package com.jaeyoung.bluetoothhp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.io.OutputStream
import java.util.*

class BluetoothConnect(context: Context, uuid: UUID, bluetoothConnectCallback: BluetoothConnectCallback) {
    private val mContext = context
    private val mBleAdapter = BluetoothAdapter.getDefaultAdapter()
    private var mUUID = uuid
    private lateinit var connectDevice: BluetoothDevice
    private var bluetoothConCallback: BluetoothConnectCallback = bluetoothConnectCallback

    companion object {
        private lateinit var bleSocket: BluetoothSocket
        private var cnt_connect = 0

    }

    private fun send(data: String) {
        val os: OutputStream = bleSocket.outputStream
        os.write(data.toByteArray())
        os.flush()
        os.close()
        bleSocket.close()
        cnt_connect = 0
        bluetoothConCallback.successSendData()
    }

    fun setConnectDevice(deviceAddress: String) {
        if (mBleAdapter.isDiscovering) mBleAdapter.cancelDiscovery()
        if (BluetoothAdapter.checkBluetoothAddress(deviceAddress)) {
            connectDevice = mBleAdapter.getRemoteDevice(deviceAddress)
            bleSocket = connectDevice.createRfcommSocketToServiceRecord(mUUID)
            bluetoothConCallback.successConnectDevice()
        } else {
            Toast.makeText(mContext, "Don't Connect Device", Toast.LENGTH_SHORT).show()
            bluetoothConCallback.failConnectDevice()
        }

    }

    fun sendData(data: String) {
        bleComunication(data).start()
    }

    fun setUUID(uuid: UUID) {
        mUUID = uuid
    }

    fun getConnectDevice(): BluetoothDevice {
        return connectDevice
    }

    private fun bleComunication(data: String): Thread {
        return object : Thread() {
            override fun run() {
                Log.d("BleConnect", "Start Connect Thread")
                try {
                    bleSocket.connect()
                    Log.d("BleConnect", "Socket Connect")
                    send(data)
                } catch (e: IOException) {
                    Toast.makeText(
                        mContext,
                        "Don't Connect Device. Check Bluetooth Device",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("BleConnect", "Connect Fail")
                    e.printStackTrace()
                    //Try Count
                    cnt_connect++
                    if (cnt_connect < 3) run()
                    else {
                        Log.d("BleConnect", "Can't Connect Device")
                        bluetoothConCallback.failSendData()
                    }
                }
            }
        }
    }

}