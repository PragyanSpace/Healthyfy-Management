package com.example.healthyfy_management

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthyfy_management.databinding.FragmentAppoinmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppoinmentFragment : Fragment() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseUser: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentAppoinmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_appoinment, container, false)
        // Inflate the layout for this fragment
//        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseAuth= FirebaseAuth.getInstance()
        firestore= FirebaseFirestore.getInstance()
        firebaseUser= firebaseAuth.currentUser!!


        val hos=ArrayList<aptData>()
        GlobalScope.launch(Dispatchers.IO)
        {
            val collectionRef = firestore!!.collection("Managements").document(firebaseUser.uid).collection("appointments")
                .get().addOnSuccessListener {
                    for(appointments in it)
                    {
                        val patId=appointments.get("pat_id").toString()
                        val status=appointments.get("status") as Int
                        val data=aptData(patId,status)
                        hos.add(data)
                    }
                }
        }


        binding.recView.layoutManager= LinearLayoutManager(activity)
        val adapter= MyAdapter(hos)
        binding.recView.adapter=adapter
        adapter.setOnItemClickListener(object: MyAdapter.onItemClickListener {
            override fun onItemClicked(position: Int) {
                Toast.makeText(activity,"Clicked",Toast.LENGTH_SHORT).show()
            }
        })




        return binding.root
    }
}