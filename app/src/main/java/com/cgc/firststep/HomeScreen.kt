package com.cgc.firststep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgc.firststep.databinding.ActivityHomeBinding

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeRecycle.layoutManager = LinearLayoutManager(this@HomeScreen)
        binding.homeRecycle.adapter = ItemAdapter()

    }


    inner class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            holder.employeeId.text = "${position+1}"

        }

        override fun getItemCount(): Int {
            return 15
        }


        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var employeeId: TextView = itemView.findViewById(R.id.liNumber)
        }
    }


}