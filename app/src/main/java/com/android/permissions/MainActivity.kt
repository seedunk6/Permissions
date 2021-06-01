package com.android.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.permissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val PERMISSIONS_REQUEST_CODE = 0
    private val PERMISSIONS: Array<String> = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPermissions.setOnClickListener { checkPermissions() }
    }

    private fun checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            //Permiso no aceptado
            requestCameraPermission()
        } else {
            //Permiso aceptado. Aqui se agrega el codigo a ejecutar luego de aceptar el permiso
            permissionAccepted()
        }
    }

    private fun permissionAccepted(){
        Toast.makeText(this, R.string.permissions_accepted, Toast.LENGTH_SHORT).show()
    }

    private fun permissionRejected(){
        Toast.makeText(this, R.string.permissions_rejected, Toast.LENGTH_SHORT).show()
    }

    private fun permissionRejectedFirstTime(){
        Toast.makeText(this, R.string.permissions_rejected_first, Toast.LENGTH_SHORT).show()
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
            && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Permiso rechazado, debemos informarle que vaya a ajustes
            permissionRejected()
        } else {
            //Nunca se ha aceptado ni rechazado el permiso, así que le pedimos que acepte o rechace el permiso.
            ActivityCompat.requestPermissions(this,
                PERMISSIONS,
                PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //Permiso aceptado
                    permissionAccepted()
                } else {
                    //Permiso rechazado
                    permissionRejectedFirstTime()                }
                return
            }

        }
    }
}