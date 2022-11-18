package br.edu.infnet.meusgastos.models

import java.time.LocalDate

data class Despesa(
    val nome: String = "",
    val valor: Double = 0.0,
    val data: LocalDate,
    val descricao: String = "",
    val categoriaId: Int = 0
)