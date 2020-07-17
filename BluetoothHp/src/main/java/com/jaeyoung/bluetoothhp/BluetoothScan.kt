package com.jaeyoung.bluetoothhp

import android.app.Activity
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast

class BluetoothScan(context: Context,bluetoothScanCallback: BluetoothScanCallback) {
    private val mContext = context
    private val mActivity = context as Activity
    private var filter: IntentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
    private val mBtAdapter = BluetoothAdapter.getDefaultAdapter()

    init {
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        bluetoothCheck(mBtAdapter)
        gpsSetDialog()
    }

    private val mScanReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action){
                BluetoothDevice.ACTION_FOUND -> {
                    // Get the BluetoothDevice object from the Intent
                    val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                    if(device!=null&&device.name!=null&&device.bondState!= BluetoothDevice.BOND_BONDED){
                        Log.d("BLE Search: ","Found Device")
                        bluetoothScanCallback.foundDevice(device)
                    }
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("BLE Search: ","Finsh Scan")
                    Toast.makeText(mContext,"Finish Device Scan", Toast.LENGTH_SHORT).show()
                    bluetoothScanCallback.finishScan()
                    mContext.unregisterReceiver(this)
                }
            }

        }
    }

    /**
     * Check bluetooth service
     */
    private fun bluetoothCheck(bluetoothAdapter: BluetoothAdapter?) {
        if (bluetoothAdapter == null) {
            //If your device can't provided bluetooth service, finish your Activity
            Toast.makeText(mContext, "This device can't provided bluetooth service", Toast.LENGTH_SHORT).show()
            mActivity.finish()
        } else {
            if (!bluetoothAdapter.isEnabled) {
                //turn on bluetooth service
                mActivity.startActivity(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }
    }

    /**
     * Gps Service Setting
     */
    private fun gpsSetDialog() {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val builder =
                AlertDialog.Builder(mActivity)
            builder.setTitle("Gps Service Off")
            builder.setMessage(
                """
                If you use this App, you need Gps Service
                Accept Gps Service
                """.trimIndent()
            )
            builder.setCancelable(true)
            builder.setPositiveButton("Option") { _, _ ->
                mActivity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            builder.create().show()
        }
    }

    fun settingBleGps(){
        bluetoothCheck(mBtAdapter)
        gpsSetDialog()
    }

    fun startScan() {
        Log.d("BLE Search", "Start Scan")
        //리시버 등록
        mContext.registerReceiver(mScanReceiver, filter)
        mBtAdapter.startDiscovery()
    }

    fun stopScan(){
        Log.d("BLE Search", "Stop Scan")
        mBtAdapter.cancelDiscovery()
        mActivity.isDestroyed
    }

}