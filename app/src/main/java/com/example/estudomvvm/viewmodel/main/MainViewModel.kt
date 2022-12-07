package com.example.estudomvvm.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estudomvvm.models.Live
import com.example.estudomvvm.repository.MainRepository
import com.example.estudomvvm.rest.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val liveList = MutableLiveData<List<Live>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllLives() {

        val request = repository.getAllLives()
        request.enqueue(object : Callback<List<Live>> {
            override fun onResponse(call: Call<List<Live>>, response: Response<List<Live>>) {
            //Quando houver resposta
                liveList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Live>>, t: Throwable) {
            //Quando der falha
                errorMessage.postValue(t.message)
            }

        })

    }
}