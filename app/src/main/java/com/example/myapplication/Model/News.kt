package com.example.myapplication.Model

import java.io.Serializable

data class News(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val image: String
) : Serializable

/*
:java.io.Serializable*/
//это наследование при всплывающей странички при нажатии
