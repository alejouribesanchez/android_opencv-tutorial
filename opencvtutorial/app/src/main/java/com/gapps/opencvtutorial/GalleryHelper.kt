package com.gapps.opencvtutorial

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.Fragment

object GalleryHelper {

    const val GALLERY_CODE = 101

    fun requestGallery(fragment: Fragment, requestId: Int) {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        fragment.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), requestId)
    }

    fun requestGallery(activity: Activity, requestId: Int) {
        var intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestId)
    }


    fun getPath(activity: Activity, uri: Uri?): String? {
        // just some safety built in
        if (uri == null) {
            return null
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity.contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            val columnIndex = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val stringColumn = cursor.getString(columnIndex)
            cursor.close()
            return stringColumn
        }
        // this is our fallback here
        return uri.path
    }

}
