package com.example.gurleensethi.barcodedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_barcode_generation.*
import net.glxn.qrgen.android.QRCode

class BarcodeGenerationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_generation)
        generateButton.setOnClickListener {
            val text = contentEditText.text.toString()

            if (text.isEmpty()) {
                Toast.makeText(this, "Enter something to create a barcode", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bitmap = QRCode.from(text).withSize(1000, 1000).bitmap()
            generationImageView.setImageBitmap(bitmap)
        }
    }
}
