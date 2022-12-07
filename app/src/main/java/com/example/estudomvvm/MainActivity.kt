package com.example.estudomvvm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.estudomvvm.databinding.ActivityMainBinding
import com.example.estudomvvm.repository.MainRepository
import com.example.estudomvvm.rest.RetrofitService
import com.example.estudomvvm.viewmodel.main.MainViewModel
import com.example.estudomvvm.viewmodel.main.MainViewModelFactory
import com.ocanha.mvvmrecyclerviewcomretrofitemkotlin.adapters.MainAdapter

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var viewModel : MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val adapter = MainAdapter{
        openLink(it.link)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =
            ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService)))[MainViewModel::class.java]

        binding.recyclerview.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.liveList.observe(this, Observer{lives ->
                Log.i("Wesley","onStart")
                adapter.setLiveList(lives)
        })
        viewModel.errorMessage.observe(this, Observer{message ->
            Toast.makeText( this, message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLives()
    }

    private fun openLink(link: String) {

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)

    }
}