package com.example.gurleensethi.barcodedemo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)
        }

        scanBarcodeButton.setOnClickListener {
            val intent = Intent(this@MainActivity, BarcodeScanningActivity::class.java)
            startActivity(intent)
        }

        createBarcodeButton.setOnClickListener {
            val intent = Intent(this@MainActivity, BarcodeGenerationActivity::class.java)
            startActivity(intent)
        }
    }
}
