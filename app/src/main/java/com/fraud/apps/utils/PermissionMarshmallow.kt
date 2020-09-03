package com.fraud.apps.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created by AndreHF on 9/27/2016.
 */
class PermissionMarshmallow {
    var activity: Activity? = null

    fun checkPermissionCall(): Boolean {
        return ActivityCompat.checkSelfPermission(activity!!,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForWrite(): Boolean {
        val result = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionGPS(): Boolean {
        val fineLoc = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLoc = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
        return fineLoc == PackageManager.PERMISSION_GRANTED && coarseLoc == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionForIMEI(): Boolean {
        val result = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_PHONE_STATE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionFineLoc() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(activity, "fine location permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    FINE_LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    fun requestPermissionCoarseLoc() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Toast.makeText(activity, "coarse location permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    COARSE_LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    fun requestPermissionForWrite() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "Write permission needed. " +
                    "Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_PERMISSION_REQUEST_CODE)
        }
    }

    fun requestPermissionCall() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CALL_PHONE)) {
            Toast.makeText(activity, "Call permission needed. " +
                    "Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.CALL_PHONE), CALL_REQUEST_CODE)
        }
    }

    fun requestPermissionForStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, "external storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE)
        }
    }

    fun checkPermissionGetAccount(): Boolean {
        val result = ActivityCompat.checkSelfPermission(activity!!,
                Manifest.permission.GET_ACCOUNTS)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissionGetIMEI() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.READ_PHONE_STATE)) {
            Toast.makeText(activity, "Error IMEI", Toast.LENGTH_LONG).show()
        } else {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.READ_PHONE_STATE), IMEI_REQUEST_CODE)
        }
    }

    fun requestPermissionGPS() {
        ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), COARSE_LOCATION_PERMISSION_REQUEST_CODE)
    }

    fun checkPermissionForRead(): Boolean {
        val result = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val GET_ACCOUNTS_PERMISSION_REQUEST_CODE = 1
        const val CAMERA_PERMISSION_REQUEST_CODE = 2
        const val READ_EXTERNAL_STORAGE = 3
        const val WRITE_PERMISSION_REQUEST_CODE = 4
        const val IMEI_REQUEST_CODE = 5
        const val CALL_REQUEST_CODE = 6
        const val COARSE_LOCATION_PERMISSION_REQUEST_CODE = 7
        const val FINE_LOCATION_PERMISSION_REQUEST_CODE = 8

        fun checkPermissionForCamera(activity: Activity): Boolean {
            val result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            return result == PackageManager.PERMISSION_GRANTED
        }

        fun requestPermissionForCamera(activity: Activity) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show()
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
            }
        }

        fun askForPermission(activity: Activity?, permission: String, requestCode: Int?): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        //This is called if user has denied the permission before
                        //In this case I am just asking the permission again
                        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode!!)
                        false
                    } else {
                        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode!!)
                        false
                    }
                } else {
                    //Toast.makeText(activity, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
                    true
                }
            } else true
        }
    }
}