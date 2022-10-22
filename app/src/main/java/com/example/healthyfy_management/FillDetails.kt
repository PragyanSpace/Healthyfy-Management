package com.example.healthyfy_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthyfy_management.databinding.ActivityFillDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class FillDetails : AppCompatActivity() {
    lateinit var binding:ActivityFillDetailsBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_fill_details)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        binding.fillDetailsSubmitBtn.setOnClickListener{
            val hospital_name = binding.hospitalNameInput.text.toString().trim()
            val hospital_id = binding.hospitalIdInput.text.toString().trim()
            val location = binding.locationInput.text.toString().trim()
            val landlineNumber = binding.landlineNumberInput.text.toString().trim()
            if (hospital_name.isEmpty() || hospital_id.isEmpty() || location.isEmpty()|| landlineNumber.isEmpty()){
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }else{
                val documentReference = firestore!!.collection("Managements").document(
                    firebaseUser!!.uid)
                val record: MutableMap<String, Any> = HashMap()
                record["id"] = firebaseUser!!.uid
                record["hospital_name"] = hospital_name
                record["hospital_id"] = hospital_id
                record["location"] = location
                record["contact"] = landlineNumber
                GlobalScope.launch(Dispatchers.IO)
                {
                    documentReference.set(record).addOnSuccessListener {
                        Toast.makeText(applicationContext, "Record added!", Toast.LENGTH_SHORT)
                            .show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            applicationContext,
                            "Failed to record data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                startActivity(Intent(this, HomePage::class.java))
            }
        }
    }
}