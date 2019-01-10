package com.example.gurleensethi.barcodedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.widget.Toast
import com.google.gson.Gson
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.nio.charset.StandardCharsets

class BarcodeScanningActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {
    /*
    * Scanner View that will create the layout for scanning a barcode.
    * If you want a custom layout above the scanner layout, then implement
    * the scanning code in a fragment and use the fragment inside the activity,
    * add callbacks to obtain result from the fragment
    * */
    private lateinit var mScannerView: ZBarScannerView
    var client: Request? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZBarScannerView(this)
        setContentView(mScannerView)
        client = getReClient()

    }

    /*
    * It is required to start and stop camera in lifecycle methods
    * (onResume and onPause)
    * */
    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    /*
    * Barcode scanning result is displayed here.
    * (For demo purposes only toast is shown here)
    * For understanding what more can be done with the result,
    * visit the GitHub README(https://github.com/dm77/barcodescanner)
    * */
    override fun handleResult(result: Result?) {
        val ecoded = Base64.decode(result!!.contents, Base64.DEFAULT)
        val decoded = String(ecoded, StandardCharsets.UTF_8)
       // Toast.makeText(this, decoded, Toast.LENGTH_SHORT).show()
        val article: ScanData? = Gson().fromJson(decoded, ScanData::class.java)
        article ?: ScanData(result!!.contents, "NA", "NA", "NA", "na")
        article.let {
            val call = client?.performAction(it!!.getUrl())
            call?.enqueue {
                onResponse = {
                    Toast.makeText(this@BarcodeScanningActivity, it.body(), Toast.LENGTH_LONG).show()
                }

                onFailure = {
                    Toast.makeText(this@BarcodeScanningActivity, it?.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        finish()
        mScannerView.resumeCameraPreview(this)
    }


    fun getReClient(): Request? {
        val client = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://ws.audioscrobbler.com")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val lastFmService = retrofit.create(Request::class.java)
        return lastFmService;

    }
}
