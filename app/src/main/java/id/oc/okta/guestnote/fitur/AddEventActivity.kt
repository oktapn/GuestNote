package id.oc.okta.guestnote.fitur

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import android.widget.Toast
import com.bumptech.glide.Glide
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.database.EventModel
import id.oc.okta.guestnote.database.database
import id.oc.okta.guestnote.util.ImageCamera
import kotlinx.android.synthetic.main.activity_add_event.*
import org.jetbrains.anko.db.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AlertDialog
import android.util.Base64
import android.view.LayoutInflater
import android.widget.DatePicker
import kotlinx.android.synthetic.main.custom_alert_dialog_success.view.*
import java.text.SimpleDateFormat


class AddEventActivity : AppCompatActivity() {

    var Image: String? = null
    private var streamFoto = ByteArrayOutputStream()
    private var picUriFoto: Uri? = null
    private var bmpFoto: Bitmap? = null
    private lateinit var imageCamera: ImageCamera
    private val permissions = ArrayList<String>()
    private var permissionsToRequest = ArrayList<String>()

    val REQUEST_ID_MULTIPLE_PERMISSIONS = 101
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        imageCamera = ImageCamera(applicationContext)
        permissions.add(Manifest.permission.CAMERA)
        permissionsToRequest = findUnAskedPermissions(permissions)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Add Event"
        ETTanggalEvent.setText("--/--/----")

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        BtnAddEvent.setOnClickListener {
            actionaddEvent()
        }

        IVImageEvent.setOnClickListener {
            if (checkAndRequestPermissions(this)) {
                streamFoto.reset()
                getImage()
            }
        }
        ETTanggalEvent.setOnClickListener {
            DatePickerDialog(this@AddEventActivity,
                    R.style.AppThemesPickerDialog, dateSetListener, cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
//            DatePickerDialog(
//                this@AddEventActivity,
//                dateSetListener,
//                set DatePickerDialog to point to today's date when it loads up
//                cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH)
//            ).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        ETTanggalEvent!!.setText(sdf.format(cal.time))
    }

    private fun actionaddEvent() {
        when {
            ETNamaEvent.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Nama EventModel Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            ETTanggalEvent.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Tanggal EventModel Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            ETAlamatEvent.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Alamat EventModel Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            ETKontakEvent.text.isEmpty() -> Toast.makeText(
                    applicationContext,
                    "Kontak EventModel Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            SpnAddEvent.selectedItem.toString().equals("Pilih Kategori...") -> Toast.makeText(
                    applicationContext,
                    "Pilih Kategori EventModel",
                    Toast.LENGTH_LONG
            ).show()
            Image.isNullOrEmpty() -> Toast.makeText(
                    applicationContext,
                    "Foto EventModel Masih Kosong",
                    Toast.LENGTH_LONG
            ).show()
            else -> {
                insertData()
            }
        }
    }

    private fun insertData() {
        database.use {
            insert(
                    EventModel.TABLE_EVENT,
                    EventModel.NAMA_EVENT to ETNamaEvent.text.toString(),
                    EventModel.ALAMAT_EVENT to ETAlamatEvent.text.toString(),
                    EventModel.KATEGORI_EVENT to SpnAddEvent.selectedItem.toString(),
                    EventModel.ALAMAT_EVENT to ETAlamatEvent.text.toString(),
                    EventModel.KONTAK_EVENT to ETKontakEvent.text.toString(),
                    EventModel.IMAGE_EVENT to Image,
                    EventModel.TANGGAL_EVENT to ETTanggalEvent.text.toString()
            )
            showDialogs()
        }
    }

    private fun showDialogs() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog_success, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Notification")
        //show dialog
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mAlertDialog.setCancelable(false)
        //login button click of custom layout
        mDialogView.BtnDialog.setOnClickListener {
            mAlertDialog.dismiss()
            finish()
        }
    }

    private fun checkAndRequestPermissions(context: Activity): Boolean {
        val ExtstorePermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val WExtstorePermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val cameraPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
        )
        val listPermissionsNeeded = ArrayList<String>()
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (WExtstorePermission !== PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (ExtstorePermission !== PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                    context, listPermissionsNeeded
                    .toTypedArray(),
                    REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

    private fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String> {
        val result = ArrayList<String>()
        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }
        return result
    }

    private fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }


    private fun getImage() {
        startActivityForResult(imageCamera.pickImageChooserIntent, 200)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> when {
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                ) !== PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(
                            applicationContext,
                            "FlagUp Requires Access to Camara.", Toast.LENGTH_SHORT
                    )
                            .show()
                    finish()
                }
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(
                            applicationContext,
                            "FlagUp Requires Access to Your Storage.",
                            Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                else -> {
                    Image = null
                    streamFoto.reset()
                    getImage()
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (resultCode != 0) {
                if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
                    if (imageCamera.getPickImageResultUri(data) != null) {
                        picUriFoto = imageCamera.getPickImageResultUri(data)
                        try {
                            bmpFoto = MediaStore.Images.Media.getBitmap(this.contentResolver, picUriFoto)
                            bmpFoto = imageCamera.getResizedBitmap(bmpFoto, 600)
                            picUriFoto = getImageUri(applicationContext, bmpFoto)

                            Glide.with(applicationContext)
                                    .load(picUriFoto)
                                    .into(IVImageEvent)

                            bmpFoto?.compress(Bitmap.CompressFormat.JPEG, 100, streamFoto)
                            Image = bitMapToString(bmpFoto)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            bmpFoto = Objects.requireNonNull(data?.extras)?.get("data") as Bitmap
                        }
                    }
                }
            }
        }
    }

    private fun bitMapToString(bitmap: Bitmap?): String {
        val bitmapToString = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 95, bitmapToString)
        val byteArray = bitmapToString.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }


    private fun getImageUri(inContext: Context, inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
}
