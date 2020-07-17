# BluetoothHelper
It's helpful for your bluetooth Scan and Connect. In Android studio

### Library Version

Version v1.0.1

### minSdkVersion

minSdkVersion 19

### Edit Gradle

Edit `YourProject/build.gradle` like below.

#### JitPack
```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

Edit `app/build.gradle` like below.

```gradle
   implementation 'com.github.JY-Dev:ColorCodeDialog:v1.0.1'
```

## How To Use

## At First you add Permission AndroidManifest
```

<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

```

### In Code

#### Init BluetoothScan
```
// Init BluetoothScan Class
val bluetoothScan =  BluetoothScan(this,object : BluetoothScanCallback{
            override fun foundDevice(device: BluetoothDevice) {
                Log.d("MainActivity","Found Device")
            }

            override fun finishScan() {
                Log.d("MainActivity","Finish Device Scan")
            }
})
```

#### BluetoothScanCallback
```
  fun foundDevice(device: BluetoothDevice) - It call When your Device find another Bluetooth Device.
  fun finishScan() -  It call When your Device scan finished.
```

#### StartScan()
```
bluetoothScan.startScan() - Start device Scan
```

#### StopScan()
```
bluetoothScan.stopScan() - Stop device Scan
```

#### SettingBleGps
```
bluetoothScan.settingBleGps() - It's turn on your Gps Setting and Bluetooth Setting.
```

#### If your App Destoryed You must Call StopScan()


#### Init BluetoothConnect
```
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
```

#### BluetoothConnectCallback
```
    fun failConnectDevice() - It call When your Device Connect Failed.
    fun successConnectDevice() - It call When your Device Connect Successed.
    fun successSendData() - It call When you send data Successed.
    fun failSendData() - It call When you send data Failed.
```

#### At First you set ConnectDevice
```
bluetoothConnect.setConnectDevice(Your Device MacAddress) - Set Your Connect Device
```

#### To Set Your Device UUID
```
bluetoothConnect.setUUID(Your Device UUID)
```

#### Send Data for Bluetooth Device
```
bluetoothConnect.sendData(Your Send Data)
```
#### Get Connected Device
```
bluetoothConnect.getConnectDevice()
```
