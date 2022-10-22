package com.example.healthyfy_management

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val TheDatas:ArrayList<aptData>):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    private lateinit var mListner:onItemClickListener
    interface onItemClickListener{
        fun onItemClicked(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListner=listener
    }
    inner class MyViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView)
    {
        val hName:TextView=itemView.findViewById(R.id.patName)

        init {
            itemView.setOnClickListener{
                listener.onItemClicked(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.apt_card, parent, false)
        return MyViewHolder(view,mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.apply {
            val current=TheDatas[position]
            holder.hName.text=current.patId
        }
    }

    override fun getItemCount(): Int {
        return TheDatas.size
    }
}