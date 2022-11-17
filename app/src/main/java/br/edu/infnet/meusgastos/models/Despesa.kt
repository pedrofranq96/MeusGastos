package br.edu.infnet.meusgastos.models

import java.util.Date

data class Despesa(
    val nome : String = "",
    val valor : Number = 0,
    val data : Date,
    val descricao : String = "",
    val categoriaId : Int = 0
)