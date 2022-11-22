package br.edu.infnet.meusgastos.main.ui

import androidx.lifecycle.ViewModel
import br.edu.infnet.meusgastos.models.Despesa
import br.edu.infnet.meusgastos.repository.DespesasRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

class MainViewModel : ViewModel() {

    val TAG = "ViewModel"
    val repository = DespesasRepository.get()

    fun getCurrentUserEmail(): String {
        return repository.getCurrentUser()?.email ?: "Email não encontrado"
    }

    fun logout() {
        repository.logout()
    }

    fun criarDespesa(despesa: Despesa): Task<DocumentReference>{
        return repository.criarDespesa(despesa)
    }



}