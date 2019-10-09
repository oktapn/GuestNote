package id.oc.okta.guestnote.util

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.MediaStore
import java.io.File
import java.util.*

@SuppressLint("Registered")
class ImageCamera(private val context: Context)  {
    val pickImageChooserIntent: Intent
        get() {
            val outputFileUri = captureImageOutputUri
            val allIntents = ArrayList<Intent>()
            val packageManager = context.packageManager

            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val listCam = packageManager.queryIntentActivities(captureIntent, 0)
            for (res in listCam) {
                val intent = Intent(captureIntent)
                intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
                intent.`package` = res.activityInfo.packageName
                if (outputFileUri != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                }
                allIntents.add(intent)
            }

            val galleryIntent = Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.type = "image/*"
            val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
            for (res in listGallery) {
                val intent = Intent(galleryIntent)
                intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
                intent.`package` = res.activityInfo.packageName
                allIntents.add(intent)
            }

            var mainIntent = allIntents[allIntents.size - 1]
            for (intent in allIntents) {
                if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(intent.component).className == "com.android.documentsui.DocumentsActivity"
                    } else {
                        TODO("VERSION.SDK_INT < KITKAT")
                    }
                ) {
                    mainIntent = intent
                    break
                }
            }
            allIntents.remove(mainIntent)

            val chooserIntent = Intent.createChooser(mainIntent, "Select source")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())
            return chooserIntent
        }

    private val captureImageOutputUri: Uri?
        get() {
            var outputFileUri: Uri? = null
            val getImage = context.externalCacheDir
            if (getImage != null) {
                outputFileUri = Uri.fromFile(File(getImage.path, "profile.png"))
            }
            return outputFileUri
        }

    fun getResizedBitmap(image: Bitmap?, maxSize: Int): Bitmap {
        var width = image?.width
        var height = image?.height

        val bitmapRatio = (width?.toFloat() ?: 0F) / (height?.toFloat() ?: 0F)
        if (bitmapRatio > 0) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    fun getPickImageResultUri(data: Intent?): Uri? {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }
        return if (isCamera) captureImageOutputUri else data?.data
    }
}