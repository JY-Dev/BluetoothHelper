package com.jaeyoung.bluetoothhelper

import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jaeyoung.bluetoothhp.BluetoothConnect
import com.jaeyoung.bluetoothhp.BluetoothConnectCallback
import com.jaeyoung.bluetoothhp.BluetoothScan
import com.jaeyoung.bluetoothhp.BluetoothScanCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val bluetoothScan =  BluetoothScan(this,object : BluetoothScanCallback{
            override fun foundDevice(device: BluetoothDevice) {
                Log.d("MainActivity","Found Device")
            }

            override fun finishScan() {
                Log.d("MainActivity","Finish Device Scan")
            }
        })
        bluetoothScan.stopScan()
        val bluetoothConnect = BluetoothConnect(this,java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"),object : BluetoothConnectCallback {
            override fun failConnectDevice() {
                Log.d("MainActivity","Fail Connect Device")
            }

            override fun successConnectDevice() {
                Log.d("MainActivity","Found Device")
            }

            override fun successSendData() {
                Log.d("MainActivity","Success Send Data")
            }

            override fun failSendData() {
                Log.d("MainActivity","Fail Send Data")
            }

        })



    }
}