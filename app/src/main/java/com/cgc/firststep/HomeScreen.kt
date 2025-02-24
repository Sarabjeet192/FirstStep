package com.cgc.firststep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgc.firststep.databinding.ActivityHomeBinding
import com.cgc.firststep.model.CricketPlayer

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    val indianPlayersList = listOf(
        CricketPlayer("Virat Kohli", "India", 1, "Batsman", 183),
        CricketPlayer("MS Dhoni", "India", 2, "Wicketkeeper", 183),
        CricketPlayer("Sachin Tendulkar", "India", 3, "Batsman", 200),
        CricketPlayer("Rohit Sharma", "India", 4, "Batsman", 264),
        CricketPlayer("Shubman Gill", "India", 5, "Batsman", 208),
        CricketPlayer("KL Rahul", "India", 6, "Batsman", 112),
        CricketPlayer("Hardik Pandya", "India", 7, "All-Rounder", 92),
        CricketPlayer("Ravindra Jadeja", "India", 8, "All-Rounder", 87),
        CricketPlayer("Jasprit Bumrah", "India", 9, "Bowler", 0),
        CricketPlayer("Ravichandran Ashwin", "India", 10, "Bowler", 124)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeRecycle.layoutManager = LinearLayoutManager(this@HomeScreen)
        binding.homeRecycle.adapter = ItemAdapter()

//        binding.homeRecycle.layoutManager = LinearLayoutManager(this@HomeScreen, LinearLayoutManager.HORIZONTAL, false)
//        binding.homeRecycle.adapter = ItemAdapter()

//        binding.homeRecycle.layoutManager = GridLayoutManager(this@HomeScreen, 4) // 2 columns
//        binding.homeRecycle.adapter = ItemAdapter()

    }


    inner class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            holder.playerRank.text = indianPlayersList[position].playerRank.toString()
            holder.playerName.text = indianPlayersList[position].playerName
            holder.playerTeam.text = indianPlayersList[position].teamName
            holder.playerScore.text = indianPlayersList[position].highScore.toString()
            holder.playerRole.text = indianPlayersList[position].role

        }

        override fun getItemCount(): Int {
            return indianPlayersList.size
        }


        inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var playerRank: TextView = itemView.findViewById(R.id.liPlayerRank)
            var playerName: TextView = itemView.findViewById(R.id.liPlayerName)
            var playerTeam: TextView = itemView.findViewById(R.id.liTeamName)
            var playerScore: TextView = itemView.findViewById(R.id.liHighScore)
            var playerRole: TextView = itemView.findViewById(R.id.liRole)
        }
    }


}