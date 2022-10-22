package com.example.healthyfy_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.healthyfy_management.databinding.ActivityDetailsFromQrBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

class DetailsFromQR : AppCompatActivity() {

    lateinit var firebaseAuth:FirebaseAuth
    lateinit var firestore:FirebaseFirestore
    lateinit var firebaseUser: FirebaseUser
    lateinit var binding:ActivityDetailsFromQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_from_qr)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        firebaseUser = firebaseAuth.currentUser!!


        val code=intent.getStringExtra("code").toString()
        val documentReference = firestore!!.collection("Patients").document(code)
        documentReference.addSnapshotListener { snapshot, e ->
            if (snapshot != null && snapshot.exists()) {
                val code: String = snapshot?.get("id") as String
                binding.bloodGrp = snapshot?.get("blood") as String
                binding.dob = snapshot?.get("Dob") as String
                binding.contact = snapshot?.get("contact") as String
                binding.preD = snapshot?.get("preD") as String
                binding.nameFull = snapshot?.get("name") as String
                var writer = MultiFormatWriter()
                try {
                    val matrix: BitMatrix = writer.encode(code, BarcodeFormat.QR_CODE, 512, 512)
                    val encoder = BarcodeEncoder()
                    val bmp = encoder.createBitmap(matrix)
                    binding.QR.setImageBitmap(bmp)
                } catch (e: Exception) {

                }
            }
        }
    }
}