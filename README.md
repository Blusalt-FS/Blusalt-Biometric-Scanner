# FINGERPRINT SCANNER SDK

This document describes how to integrate the fingerprint SDK into your android Mobile app

The Fingerprint SDK for Android enables Biometrics capture using the Integrated Biometrics Device Kojak PN: KP210DA. SDK includes:

Real-time Biometric scanning
Offline, on-device processing, Biometrics Capture
The SDK supports the following manufacturers
- Curve
- Watson
- Watson Rev1
- Sherlock
- Watson Mini
- Columbo
- Homes
    
The SDK supports the following sequence
- Single flat finger
- Single rolled finger
- 2 flat fingers
- 10 single flat fingers
- 10 single rolled fingers
- 4 flat fingers
- 10 flat fingers with 4-finger scanner
    


## How to add SDK

the SDK comes in form of an .aar
- Copy the .aar file to the libs folder in your project
  - If you don't have a libs folder you can add one by creating a new one 
  - To create a new libs folder add a new directory in app directory and name it libs: (app/libs)
    
- Add a dependency to the module level gradle file i.e build.gradle(app)

     implementation(name: 'blusalt-biometric-sdk-release', ext: 'aar')
- Sync the project



## There are two basic ways to use the SDK
- ### FingerPrintScanFragment
   
   This is a fully implemented UI fragment that offers a complete Fingerprint Scanning functionality
   
   To use this:
   - Make your activity implement `FingerPrintCaptureListener`
   - Implement the following abstract methods
    
    ```@Override
           public void setBiometricResults(CaptureComponentData captureComponentData) {
               This callback captures each each Sequence capture one at a time.
               e.g if you are capturing CAPTURE_SEQ_10_FLAT_WITH_4_FINGER_SCANNER you should expect
                this method to be called 3 times delivering each capure Componet data
       
           }
       
           @Override
           public void onCaptureSequenceFinished(boolean b) {
           // this method is called when the Sequence is complete    
       
           }
       
           @Override
           public void removeBiometricFragment() {
              //This method is called when the fragment is removed. You can do some clean up here if you need to
           }```
  
  To use the fragment simply Call add or replace the current fragment with the  `FingerPrintScanFragment`
  e.g
              FingerPrintScanFragment fragment = new FingerPrintScanFragment();
              FragmentManager fragmentManager = getSupportFragmentManager();
              FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
              fragmentTransaction.add(R.id.fingerprint_frame, fragment, "FINGER_PRINT");
              fragmentTransaction.commitAllowingStateLoss();
  
- ### Use the API's directly

    This approach is usefull if you want to customize the UI. 
    
    To use this approach you need to understand the following classes

* [IBScan](#ibscan)
* [IBScanDevice](#ibscandevice)
* [Interface IBScanListener](#ibscanlistener)
* [Interface IBScanDeviceListener](#ibscandevicelistener)

    
  
<a name="ibscan"></a> IBScan
    
  The single instance of this class may be gotten with getInstance(). The application will typically register a IBScanListener   to receive notifications for events such as devic e count change and device communication failure. Device instances should     be obtai ned by either the blocking openDevice() method or non- blocking openDeviceAsync() method.
  On Android, the Activity accessing IB scanners must set the context for operations with setContext(). Several more Android-   specific functions are provided to manage USB devices.
  
 ### <a name="ibscandevice"> IBScandevice
    Principal class for interfacing with particular IB scanners.
    
 ### <a name="ibscanlistener"> IBScanListener
    Listener for device management events on an IBScan. This listener should be registered by an application with the             setScanListener() method.
    Special functions are provided for device attachment and detachment and permission status on Android, where the app may be     explicitly responsible for soliciting permission.
    
 ### <a name="ibscandevicelistener"> IBScanDeviceListener
    Listener for scan events on a IBScanDevice. This listener should be registered by an application using the.   
    setScanDeviceListener(IBScanDeviceListener) method.
    Most of these events occur after beginCaptureImage() has been called. If fingers are touching the platen when the capture 
    is begun, deviceImagePreviewAvailable() will be called immediately and again once no fingers are touching. Periodically,   
    until a final image is achieved, deviceImagePreviewAvailable() will return the current scanner image. Changes in the 
    quantity and quality of finger presses will result in deviceFingerCountChanged() or deviceFingerQualityChanged() calls. If 
    the selected scan type is a rolled finger scan, then deviceAcquisitionBegun() will be called when a flat finger scan has 
    been acquired and the user should begin rolling his or her finger to the left; when the left-roll is complete, 
    deviceAcquisitionCompleted() will be called, and the user should begin rolling back toward the right. When a quality scan 
    with the correct number of fingers (and a full finger roller, if applicable) is available or captureImageManually() is 
    called, deviceImageResultAvailable() or deviceImageResultExtendedAvailable() will supply a final scan image to the 
    application.
    
[Click for more Information about the classes/interfaces andtheir methods ](../blob/master/IBScanUltimate API Manual for Java (and Android).pdf)
    
    
