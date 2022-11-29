package br.edu.infnet.meusgastos.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.meusgastos.models.Despesa
import br.edu.infnet.meusgastos.models.DespesaComId
import br.edu.infnet.meusgastos.repository.DespesasRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.toObject

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

    fun deleteDespesa(id: String){
        repository.deleteDespesa(id)
    }

    /////Ouvir documentos em uma coleção do firebase/////

    fun observerColecaoDespesas(){

        repository.getDespesasColecao()
            .addSnapshotListener{ snapshots, e ->
                if(e != null){
                    Log.w(TAG, "listen:error ",e)
                    return@addSnapshotListener
                }

                val listaInput = mutableListOf<DespesaComId>()

                val listaRemocao = mutableListOf<String>()

                val listaModificacao = mutableListOf<DespesaComId>()

                for (dc in snapshots!!.documentChanges){
                    when(dc.type){

                        DocumentChange.Type.ADDED -> {

                            val despesa = dc.document.toObject<Despesa>()
                            val id = dc.document.id
                            val despesaComId = despesaToDespesaComId(despesa, id)

                            Log.i(TAG,"DespesaComId: ${despesaComId}")
                            listaInput.add(despesaComId)

                        }

                        DocumentChange.Type.MODIFIED -> {
                            val despesa = dc.document.toObject<Despesa>()
                            val id = dc.document.id
                            val despesaComId = despesaToDespesaComId(despesa, id)

                            Log.i(TAG,"Modificação - DespesaComId: ${despesaComId}")
                            listaModificacao.add(despesaComId)
                        }

                        DocumentChange.Type.REMOVED -> {
                            val id = dc.document.id
                            Log.i(TAG, "Id removido: ${id}")
                            listaRemocao.add(dc.document.id)
                        }

                    }

                }

                addListaToDespesasComId(listaInput)
                removeFromDespesasComId(listaRemocao)
                modifyInDespesasComId(listaModificacao)


            }


    }

    private fun modifyInDespesasComId(listaModificacao: List<DespesaComId>) {
        Log.i(TAG, "listaModificacao: ${listaModificacao}")
        if (listaModificacao.isNotEmpty()){
            for(itemModificado in listaModificacao){
                modifyItemInListaDespesasComId(itemModificado)
            }
        }
    }

    fun modifyItemInListaDespesasComId(itemModificado: DespesaComId) {
        val listaAntiga = despesasComId.value
        val listaNova = mutableListOf<DespesaComId>()

        listaAntiga?.forEach { itemDaLista ->
            if (itemModificado.id == itemDaLista.id) {
                listaNova.add(itemModificado)
            } else {
                listaNova.add(itemDaLista)
            }
        }
        setDespesaComId(listaNova)
    }

    private fun removeFromDespesasComId(listaRemocao: MutableList<String>) {

        val listaAntiga = despesasComId.value

        val listaNova = mutableListOf<DespesaComId>()

        Log.i(TAG, "listaRemocao: ${listaRemocao}")

        if (listaRemocao.isNotEmpty()) {
            listaAntiga?.forEach {
                Log.i(TAG, "item da lista Antiga: ${it.id}")
                if (it.id in listaRemocao) {
                    Log.i(TAG, "item ${it.id} está dentro da listaRemocao")

                    //listaNova.add(it)
                } else {
                    Log.i(TAG, "item ${it.id} _NÃO_ está dentro da listaRemocao")

                    listaNova.add(it)
                }
            }
            setDespesaComId(listaNova)
        }


    }

    private fun addListaToDespesasComId(listaInput: MutableList<DespesaComId>) {

        val listaAntiga = despesasComId.value

        val listaNova = mutableListOf<DespesaComId>()

        listaAntiga?.forEach {
            listaNova.add(it)
        }

        listaInput.forEach {
            listaNova.add(it)
        }

        setDespesaComId(listaNova)

    }

    fun despesaToDespesaComId(despesa: Despesa, id: String): DespesaComId {
        return DespesaComId(
            nome = despesa.nome,
            valor = despesa.valor,
            data = despesa.data,
            descricao = despesa.descricao,
            categoriaId = despesa.categoriaId,
            id = id
        )
    }

    private val _selectedDespesaComId = MutableLiveData<DespesaComId>()
    val selectedDespesaComId: LiveData<DespesaComId> = _selectedDespesaComId
    fun setSelectedDespesaComId(value: DespesaComId) {
        _selectedDespesaComId.postValue(value)
    }

    fun atualizaDespesa(despesa: Despesa) {
        repository.atualizaDespesa(selectedDespesaComId.value?.id, despesa)
    }


//    private val _despesas = MutableLiveData<List<Despesa>>()
//    val despesas: LiveData<List<Despesa>> = _despesas
//    fun setDespesas(value: List<Despesa>){
//        _despesas.postValue(value)
//    }

    private val _despesasComId = MutableLiveData<List<DespesaComId>>()
    val despesasComId : LiveData<List<DespesaComId>> = _despesasComId
    fun setDespesaComId(value : List<DespesaComId>){
        _despesasComId.postValue(value)
    }



    /////////////////////////////////////////////////////

    init {
        observerColecaoDespesas()
    }

}