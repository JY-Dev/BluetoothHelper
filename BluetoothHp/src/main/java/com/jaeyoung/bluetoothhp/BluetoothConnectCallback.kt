package com.jaeyoung.bluetoothhp

interface BluetoothConnectCallback {
    fun failConnectDevice()
    fun successConnectDevice()
    fun successSendData()
    fun failSendData()

}