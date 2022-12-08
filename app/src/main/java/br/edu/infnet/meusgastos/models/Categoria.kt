package br.edu.infnet.meusgastos.models

import android.graphics.drawable.Drawable

data class Categoria(
    val id : Any,
    val nome : String,
    val imagem : Int
)

data class moeda(
    val ask: String,
    val bid: String,
    val code: String,
    val codein: String,
    val create_date: String,
    val high: String,
    val low: String,
    val name: String,
    val pctChange: String,
    val timestamp: String,
    val varBid: String
)
