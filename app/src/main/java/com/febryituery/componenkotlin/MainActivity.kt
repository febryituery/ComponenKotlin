package com.febryituery.componenkotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.febryituery.componenkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private var appType = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnCamera.setOnClickListener { openCamera() }
        viewBinding.btnVideo.setOnClickListener { openVideo() }
        viewBinding.btnFileManager.setOnClickListener { openFileManager() }

    }
    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT).show()
            } else {
                if (appType == 0) {
                    openCamera()
                } else if (appType == 1) {
                    openVideo()
                } else if (appType == 2) {
                    openFileManager()
                }
            }
        }
    private fun openCamera() {
        if (allPermissionsGranted()) {
            startActivity(Intent(this, CameraActivity::class.java))
        } else {
            appType = 0
            requestPermissions()
        }
    }
    private fun openVideo() {
        if (allPermissionsGranted()) {
            startActivity(Intent(this, VideoActivity::class.java))
        } else {
            appType = 1
            requestPermissions()
        }
    }
    private fun openFileManager() {
        appType = 2
        startActivity(Intent(this, InternalStorageActivity::class.java))
    }

    companion object {
        private const val TAG = "Miscom"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}