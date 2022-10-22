package com.example.healthyfy_management

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthyfy.ProfileFragment
import com.example.healthyfy_management.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home_page)
        firebaseAuth = FirebaseAuth.getInstance()
        replaceFragment(AppoinmentFragment())
        binding.bottomNav.setOnItemSelectedListener {

            when(it.itemId){

                R.id.appoinments ->  replaceFragment(AppoinmentFragment())
                R.id.scan_id -> replaceFragment(ScanIdFragment())
                R.id.add_doctor -> replaceFragment(AddDoctorFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else -> {}

            }
            true


        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutForFragment,fragment)
        fragmentTransaction.commit()
    }
}