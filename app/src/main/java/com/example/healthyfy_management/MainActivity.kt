package com.example.healthyfy_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthyfy_management.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        val firebaseUser= firebaseAuth.currentUser
        if (firebaseUser != null) {

            startActivity(Intent(this@MainActivity, HomePage::class.java))
            finish()
        }
        binding.signinBtn.setOnClickListener{
            val intent = Intent(this@MainActivity, SignUp::class.java)
            startActivity(intent)

        }
        binding.forgotpassword.setOnClickListener{
            val intent = Intent(this@MainActivity,ForgotPassword::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener{
            val mail = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()
            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //firebase work
                firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        checkMailVerification()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Account doesn't exist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    private fun checkMailVerification() {
        val firebaseUser= firebaseAuth.currentUser
        val documentReference = firestore!!.collection("Managements").document(firebaseUser!!.uid)
        if (firebaseUser != null) {
            if (firebaseUser.isEmailVerified) {
                Toast.makeText(applicationContext, "Logged in", Toast.LENGTH_SHORT).show()
                documentReference.addSnapshotListener { snapshot, e ->
                    if (snapshot != null && snapshot.exists()) {
                        startActivity(Intent(this@MainActivity, HomePage::class.java))
                    }
                    else {
                        startActivity(Intent(this@MainActivity, FillDetails::class.java))
                    }
                }
                finish()
            } else {
                Toast.makeText(applicationContext, "verify your mail first.", Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
            }
        }
    }
}