package com.example.appproto

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

class camera2 : AppCompatActivity() {

    private lateinit var imageView2: ImageView
    private lateinit var btnChoose: Button
    private lateinit var btnTakePic: Button
    private lateinit var btnUpload: Button

    // Firebase variables
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    private val firestore = FirebaseFirestore.getInstance()

    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 22
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera2)

        imageView2 = findViewById(R.id.imageView2)
        btnChoose = findViewById(R.id.btnChoose)
        btnTakePic = findViewById(R.id.btnTakePic)
        btnUpload = findViewById(R.id.btnUpload)

        btnChoose.setOnClickListener { selectImage() }
        btnTakePic.setOnClickListener { dispatchTakePictureIntent() }
        btnUpload.setOnClickListener { uploadImage() }
    }

    // Select Image from Gallery
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    // Take Picture using Camera
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // Handle Activity Results (Gallery Image Selection or Camera Picture Capture)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView2.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView2.setImageBitmap(imageBitmap)
            saveImageToFirebase(imageBitmap)
        }
    }

    // Upload Image to Firebase Storage
    private fun uploadImage() {
        filePath?.let { filePath ->
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            val ref = storageReference.child("images/${UUID.randomUUID()}.jpg")
            ref.putFile(filePath)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Image uploaded!", Toast.LENGTH_SHORT).show()
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        saveImageUrlToFirestore(imageUrl)
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed to get image URL", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressDialog.setMessage("Uploaded ${progress.toInt()}%")
                }
        } ?: run {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Save Image URL to Firestore
    private fun saveImageUrlToFirestore(imageUrl: String) {
        val imageMap = hashMapOf("imageUrl" to imageUrl)
        firestore.collection("images")
            .add(imageMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Image URL saved to Firestore", Toast.LENGTH_SHORT).show()
                // Load Image from Storage and Display
                loadImageFromStorage(imageUrl)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save image URL to Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Save Image to Firebase Storage
    private fun saveImageToFirebase(imageBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageName = "${UUID.randomUUID()}.jpg"
        val imageRef = storageReference.child("images/$imageName")

        imageRef.putBytes(data)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    saveImageUrlToFirestore(imageUrl)
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to get image URL: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Load Image from Firebase Storage and Display
    private fun loadImageFromStorage(imageUrl: String) {
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
        storageReference.downloadUrl
            .addOnSuccessListener { uri ->

                Glide.with(this /* context */)
                    .load(uri)
                    .into(imageView2)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
