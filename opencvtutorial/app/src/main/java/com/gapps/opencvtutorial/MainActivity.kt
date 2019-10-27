package com.gapps.opencvtutorial

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class MainActivity : AppCompatActivity() {

    private var imagePath: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OpenCVLoader.initDebug()
        setUpClickListeners()
    }

    private fun loadImageGallery() {
        GalleryHelper.requestGallery(this, GalleryHelper.GALLERY_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GalleryHelper.GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val selectedImageUri = data!!.data
                val selectedImagePath = GalleryHelper.getPath(this, selectedImageUri)
                if (selectedImagePath != null) {
                    imagePath = selectedImagePath
                    val bitmap = BitmapFactory.decodeFile(selectedImagePath)
                    iv_photo.setImageBitmap(bitmap)
                }
            }
            return
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }

    private fun setUpClickListeners() {
        fab_gallery.setOnClickListener {
            loadImageGallery()
        }

        btn_filter_black.setOnClickListener {
            if (!imagePath.isNullOrEmpty()) {
                val bitmap = BitmapFactory.decodeFile(imagePath)
                val mat = Mat()
                Utils.bitmapToMat(bitmap, mat)
                Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY)
                Utils.matToBitmap(mat, bitmap)
                iv_photo.setImageBitmap(bitmap)
            }
        }
    }
}
