package com.example.estudomvvm.repository

import com.example.estudomvvm.rest.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService){

    fun getAllLives() = retrofitService.getAllLives()

}