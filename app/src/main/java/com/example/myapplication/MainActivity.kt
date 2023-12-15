package com.example.myapplication

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Api.AdNews
import com.example.myapplication.Api.ApiRec
import com.example.myapplication.Model.News
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), AdNews.Listener {
    private lateinit var binding: ActivityMainBinding
    private val TAG="MainActivity"
    private val adapterNews=AdNews(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        getDataNews()
    }
    private fun initNews(data: List<News>) {
        with(binding) {
            newsList.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL, false)
            newsList.adapter = adapterNews
            val listNews: List<News> = data
            if (listNews.isNotEmpty()) {
                for (element in listNews) {
                    adapterNews.addResult(element)
                }
            }
        }
    }
    private fun getDataNews() {
        val api = Retrofit.Builder()
            .baseUrl("https://iis.ngknn.ru/NGKNN/МамшеваЮС/MedicMadlab/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRec::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.GetNews().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    runOnUiThread {initNews(data)}
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, e.toString())
                }
            }
        }
    }
    override fun OnClick(news: News) {
        // Здесь будут обрабатываться действия при клике на объект
        val itemListDialogFragment = ItemInformationDialogFragment() //Создаем объект фрагмента
        val bundle = Bundle() //Bundle - представляет собой контейнер, который может содержать массивы или значения любого типа данных
        bundle.putSerializable("iteminformation", news)  // 1 параметр - уникальный ключ передачи данных, 2 параметр - данные, которые передаем
        itemListDialogFragment.arguments = bundle // Передаем данные
        itemListDialogFragment.show(supportFragmentManager, "pop") // Запускаем наш фрагмент
    }
}
