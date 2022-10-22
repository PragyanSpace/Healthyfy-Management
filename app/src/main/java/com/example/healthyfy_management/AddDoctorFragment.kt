package com.example.healthyfy_management


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthyfy_management.databinding.FragmentAddDoctorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.HashMap
import java.util.UUID


class AddDoctorFragment : Fragment() {
    private  lateinit var firebaseAuth:FirebaseAuth
    private lateinit var firestore:FirebaseFirestore
    private lateinit var firebaseUser:FirebaseUser
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddDoctorBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_doctor, container, false)
        // Inflate the layout for this fragment
//        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth=FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        firebaseUser= firebaseAuth.currentUser!!


        binding.fillDetailsOfDoctorSubmitBtn.setOnClickListener{
            val id= UUID.randomUUID().toString().trim()
            val name =binding.doctorNameInput.text.toString().trim()
            val license_id =  binding.doctorIdInput.text.toString().trim()
            val department = binding.departmentInput.text.toString().trim()
            val phone_number = binding.phoneNumberInput.text.toString().trim()
            if (name.isEmpty() || license_id.isEmpty() || department.isEmpty()|| phone_number.isEmpty()){
                Toast.makeText(activity, "All fields are required", Toast.LENGTH_SHORT).show()
            }else{
                val documentReference = firestore!!.collection("Managements").document(firebaseUser!!.uid).collection("doctors").document(id)

                    val doctRecord: MutableMap<String, Any> = HashMap()
                    doctRecord["name"] = name
                    doctRecord["license_id"] = license_id
                    doctRecord["id"] = id
                    doctRecord["department"] = department
                    doctRecord["phone_number"] = phone_number
                    GlobalScope.launch(Dispatchers.IO)
                    {
                        documentReference.set(doctRecord).addOnSuccessListener {
                            Toast.makeText(activity, "Record added!", Toast.LENGTH_SHORT)
                                .show()
                        }.addOnFailureListener {
                            Toast.makeText(
                                activity,
                                "Failed to record data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                startActivity(Intent(activity, HomePage::class.java))

            }
        }

        return binding.root
    }
}