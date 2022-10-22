package com.example.healthyfy


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthyfy_management.MainActivity
import com.example.healthyfy_management.R
import com.example.healthyfy_management.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var firestore:FirebaseFirestore
    lateinit var firebaseUser:FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
//        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth=FirebaseAuth.getInstance()
        firestore=FirebaseFirestore.getInstance()
        firebaseUser= firebaseAuth.currentUser!!








        binding.button.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent (activity, MainActivity::class.java)
            activity?.startActivity(intent)
            activity?.finish()
        }

        return binding.root
    }
}