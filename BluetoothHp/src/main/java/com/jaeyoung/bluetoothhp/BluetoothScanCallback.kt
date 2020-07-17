package com.jaeyoung.bluetoothhp

import android.bluetooth.BluetoothDevice

interface BluetoothScanCallback {
    fun foundDevice(device: BluetoothDevice)
    fun finishScan()
}