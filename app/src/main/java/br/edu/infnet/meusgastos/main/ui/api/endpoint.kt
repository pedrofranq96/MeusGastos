package br.edu.infnet.meusgastos.main.ui.api

import br.edu.infnet.meusgastos.models.Moeda
import retrofit2.Call
import retrofit2.http.GET


interface Endpoint {

    @GET("USD-BRL")
    fun getUSDBRL() : Call<List<Moeda>>

    @GET("EUR-BRL")
    fun getEURBRL() : Call<List<Moeda>>

    @GET("BTC-EUR")
    fun getBTCEUR() : Call<List<Moeda>>
}