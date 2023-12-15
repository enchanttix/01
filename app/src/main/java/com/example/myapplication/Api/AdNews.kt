package com.example.myapplication.Api

import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.News
import com.example.myapplication.R
import com.example.myapplication.databinding.NewsBinding
import kotlinx.coroutines.newSingleThreadContext
import okhttp3.internal.notify


class AdNews(private val listener: AdNews.Listener): RecyclerView.Adapter<AdNews.NewsHolder>() {
    private val newsList = ArrayList<News>()

    class NewsHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = NewsBinding.bind(item)

        fun bind (result: News, listener: Listener)= with(binding) {
            zagolovok.text=result.name
            isl.text=result.description
            prise.text=result.price.toString()
            Glide.with(binding.root).load(result.image).into(image)
            newsForma.setOnClickListener() {
                listener.OnClick(result)
            }
        }
    }

    interface Listener {
        fun OnClick(result: News)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.news,parent, false)
        return NewsHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.bind(newsList[position],listener)
    }

    fun addResult(item: News){
        newsList.add(item)
        notifyDataSetChanged()
    }
}