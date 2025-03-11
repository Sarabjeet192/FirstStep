package com.cgc.firststep.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgc.firststep.databinding.ActivityMatchScheduleScreenBinding
import com.cgc.firststep.databinding.MatchItemBinding
import com.cgc.firststep.model.MatchList
import com.cgc.firststep.model.MatchResponse
import com.cgc.firststep.network.RemoteCallback
import com.cgc.firststep.network.WebAPIManager

class MatchScheduleScreen : AppCompatActivity() {

    private lateinit var binding: ActivityMatchScheduleScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchScheduleScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMatchSchedule()

    }

    private fun getMatchSchedule() {

        WebAPIManager.instance.getSchedule().enqueue(object : RemoteCallback<MatchResponse>() {
            override fun onSuccess(response: MatchResponse?) {

                val adapter = MatchAdapter(response?.results!!)
                binding.ssRecyclerView.layoutManager = LinearLayoutManager(this@MatchScheduleScreen)
                binding.ssRecyclerView.adapter = adapter

            }

            override fun onUnauthorized(throwable: Throwable) {

                Toast.makeText(this@MatchScheduleScreen, throwable.message, Toast.LENGTH_LONG)
                    .show()
            }

            override fun onFailed(throwable: Throwable) {

                Toast.makeText(this@MatchScheduleScreen, throwable.message, Toast.LENGTH_LONG)
                    .show()
            }

            override fun onInternetFailed() {

                Toast.makeText(
                    this@MatchScheduleScreen,
                    "Please Check Your internet..",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onEmptyResponse(message: String) {

                Toast.makeText(this@MatchScheduleScreen, message, Toast.LENGTH_LONG).show()
            }
        })
    }


    inner class MatchAdapter(var matchList: MutableList<MatchList>) : RecyclerView.Adapter<MatchAdapter.ProductViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val binding = MatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ProductViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.bind(matchList[position])

        }

        override fun getItemCount() = matchList.size

        inner class ProductViewHolder(private val binding: MatchItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(mResult: MatchList) {
                binding.miName.text = mResult.matchTitle
                binding.miHomeTeam.text = mResult.home?.name
                binding.miAwayTeam.text = mResult.away?.name
                binding.miVanue.text = mResult.venue
            }
        }
    }

}