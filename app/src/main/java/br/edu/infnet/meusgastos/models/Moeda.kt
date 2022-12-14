package br.edu.infnet.meusgastos.models

import com.google.gson.annotations.SerializedName


data class Moeda(
    @SerializedName("Ask")
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