package com.cgc.firststep.ui

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.cgc.firststep.R
import com.cgc.firststep.databinding.ActivityPostApiExampleBinding
import com.cgc.firststep.model.FileUploadResponse
import com.cgc.firststep.model.MatchResponse
import com.cgc.firststep.model.PostApiModel
import com.cgc.firststep.model.PostDataModel
import com.cgc.firststep.network.RemoteCallback
import com.cgc.firststep.network.WebAPIManager
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PostApiExample : AppCompatActivity() {

    private lateinit var binding: ActivityPostApiExampleBinding
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var cropActivityLauncher: ActivityResultLauncher<Intent>

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    private var mImage: File? = null
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostApiExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Handle the result of permission request
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("Notification", "Notification permission granted")
                } else {
                    Log.d("Notification", "Notification permission denied")
                }
            }

        requestNotificationPermission()


        binding.pieBtn.setOnClickListener {

           // throw RuntimeException("Test Crash")

            // hitPostApi()
           // imagePickerLauncher.launch("image/*")
           // showImagePickerDialog()

        }

        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    startCrop(it)
                }
            }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri?.let { uri ->
                    startCrop(uri) // Start cropping or use the image
                }
            }
        }


        cropActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.data != null) {
                    val resultUri = UCrop.getOutput(result.data!!)
                    resultUri?.let {
                        Glide.with(this@PostApiExample).load(it.path).into(binding.pieImage)

                        mImage = getFileFromUri(this@PostApiExample, it)

                        // Get File Name
                        val fileName = mImage?.name

                        // Get File Size in MB
                        val fileSizeInMB =
                            mImage?.length()?.div((1024.0 * 1024.0)) // Convert bytes to MB

                         // Get File Extension
                        val fileExtension = fileName?.substringAfterLast(".", "Unknown") // Extract extension

                        // Update UI
                        binding.pieInfo.text = "Name: $fileName \nSize: %.2f MB \nExtension: $fileExtension".format(fileSizeInMB)

                        uploadFileApi()

                    }
                }
            }

    }

    private fun openCamera() {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.TITLE, "New Image")
            put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
        }

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            cameraLauncher.launch(uri) // Open the camera
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                Log.d("Notification", "Permission already granted")
            }
        }
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(this)
            .setTitle("Select Image From")
            .setItems(options) { _, index ->
                when (index) {
                    0 -> openCamera() // Open Camera
                    1 -> imagePickerLauncher.launch("image/*") // Open Gallery
                }
            }
            .show()
    }

    private fun uploadFileApi() {


        val mBusinessImage: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            mImage!!.name,
            mImage!!.asRequestBody("image/*".toMediaTypeOrNull())
        )

        val request = WebAPIManager.instance.fileUpload(mBusinessImage)

        request.enqueue(object : RemoteCallback<FileUploadResponse>() {
            override fun onSuccess(response: FileUploadResponse?) {

                Glide.with(this@PostApiExample).load(response?.location).into(binding.pieUploadedImage)

            }

            override fun onUnauthorized(throwable: Throwable) {
                Toast.makeText(this@PostApiExample, throwable.message, Toast.LENGTH_LONG).show()
            }

            override fun onFailed(throwable: Throwable) {
                Toast.makeText(this@PostApiExample, throwable.message, Toast.LENGTH_LONG).show()
            }

            override fun onInternetFailed() {
                Toast.makeText(
                    this@PostApiExample,
                    "Please Check Your internet..",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onEmptyResponse(message: String) {
                Toast.makeText(this@PostApiExample, message, Toast.LENGTH_LONG).show()
            }
        })


    }


    private fun startCrop(uri: Uri) {
        val fileName = System.currentTimeMillis().toString()
        val destinationUri = Uri.fromFile(File(cacheDir, "${fileName}.jpg"))
        val options = UCrop.Options().apply {
            setCompressionQuality(50)
            setHideBottomControls(true)
            setFreeStyleCropEnabled(false)
        }

        val cropIntent = UCrop.of(uri, destinationUri)
            .withOptions(options)
            .withAspectRatio(1f, 1f) // Adjust aspect ratio as needed
            .getIntent(this)

        cropActivityLauncher.launch(cropIntent)
    }

    private fun hitPostApi() {

        val mRequest = PostApiModel()
        val mData = PostDataModel()
        mRequest.name = "Macbook m3"

        mData.year = 2027
        mData.price = 230000f
        mData.cPUModel = "Macbook M series"
        mData.hardDiskSize = "4 TB"

        mRequest.data = mData

        WebAPIManager.instance.postApiExample(mRequest)
            .enqueue(object : RemoteCallback<PostApiModel>() {
                override fun onSuccess(response: PostApiModel?) {

                    if (response?.id.toString().isNotEmpty()) {
                        Toast.makeText(
                            this@PostApiExample,
                            "Data Post Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }

                override fun onUnauthorized(throwable: Throwable) {

                    Toast.makeText(this@PostApiExample, throwable.message, Toast.LENGTH_LONG)
                        .show()
                }

                override fun onFailed(throwable: Throwable) {

                    Toast.makeText(this@PostApiExample, throwable.message, Toast.LENGTH_LONG)
                        .show()
                }

                override fun onInternetFailed() {

                    Toast.makeText(
                        this@PostApiExample,
                        "Please Check Your internet..",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onEmptyResponse(message: String) {

                    Toast.makeText(this@PostApiExample, message, Toast.LENGTH_LONG).show()
                }
            })
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        return when {
            uri.scheme.equals("content") -> {
                // Handle content scheme URIs (e.g., content://)
                saveFileFromContentUri(context, uri)
            }

            uri.scheme.equals("file") -> {
                // Handle file scheme URIs (e.g., file://)
                File(uri.path!!)
            }

            else -> null
        }
    }

    private fun saveFileFromContentUri(context: Context, uri: Uri): File? {
        val fileName = getFileName(context, uri)
        val tempFile = File(context.cacheDir, fileName)

        try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            val outputStream = FileOutputStream(tempFile)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            return tempFile
        } catch (e: IOException) {
            Log.e("FileUtils", "Failed to get file from Uri", e)
            return null
        }
    }

    private fun getFileName(context: Context, uri: Uri): String {
        var fileName = System.currentTimeMillis().toString()
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1 && it.moveToFirst()) {
                fileName = it.getString(nameIndex)
            }
        }
        return fileName
    }
}