package com.example.bobi

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.provider.MediaStore.getMediaScannerUri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult


private const val REQUEST_IMAGE_CAPTURE = 100

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var untilFinished = 10000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
          //  openNativeCamera()
            openDetailsActivity()
        }
    }

    override fun onResume() {
        super.onResume()

        startCountDownTimer(untilFinished)
    }

    override fun onPause() {
        super.onPause()

        timer.cancel()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            findViewById<ImageView>(R.id.imageView).setImageBitmap(imageBitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



    private fun openNativeCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }


    private fun openDetailsActivity(){
        val intent = Intent(this,DetailsActivity::class.java)
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE)
    }

    private fun startCountDownTimer(time: Long){
        timer = object: CountDownTimer(time, 10000){
            override fun onTick(millisUnitFinished: Long) {
                untilFinished = millisUnitFinished
                findViewById<TextView>(R.id.countdown).text = "Second remaining: ${millisUnitFinished/1000}"
            }

            override fun onFinish() {
               findViewById<TextView>(R.id.countdown).text = "Done"
            }

        }
        timer.start()

    }

}