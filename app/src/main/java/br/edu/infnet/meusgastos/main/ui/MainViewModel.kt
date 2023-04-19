package br.edu.infnet.meusgastos.main.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.models.Categoria
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

    private val _totalDespesas = MutableLiveData<Float>(0.0F)
    val totalDespesas : LiveData<Float> = _totalDespesas
    fun setTotalDespesas(value: Float){
        _totalDespesas.postValue(value)
    }

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
            categoriaNome = despesa.categoriaNome,
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

    fun deletarDespesa(){
        selectedDespesaComId.value?.let { repository.deleteDespesa(it.id) }
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
        setTotalDespesas(calculaValorTotal(value))
        totalComida = calculaValorTotalPorCategoria("Comida")
    }


    fun calculaValorTotal(lista: List<DespesaComId>): Float{

        var valorfinal = 0.0F
        lista.forEach { despesa ->
            valorfinal += despesa.valor
            Log.i(TAG, valorfinal.toString())
        }
        return valorfinal

    }

    fun setTotalPorCategoria(){

        val listaCategorias = listOf<String>("Comida", "Transporte", "Contas", "Lazer", "Compras", "Mercado", "Cartão", "Educação", "Pets", "Presente", "Roupas", "Saúde", "Viagem", "Outros")
        listaCategorias.forEach {
           // Log.i("ListaCategorias", it)
          //  calculaValorTotalPorCategoria()
        }
    }

    var totalComida = 0.0F
    var totalTransporte = 0.0F
    var totalContas = 0.0F
    var totalLazer = 0.0F
    var totalCompras = 0.0F
    var totalMercado = 0.0F
    var totalCartao = 0.0F
    var totalEducacao = 0.0F
    var totalPets = 0.0F
    var totalPresente = 0.0F
    var totalRoupas = 0.0F
    var totalSaude = 0.0F
    var totalViagem = 0.0F
    var totalOutros = 0.0F

//    fun calculaValorTotalPorCategoria(
//        lista: List<DespesaComId>,
//    categoriaInput: String): Float{
//
//        var valorfinal = 0.0F
//        lista.filter { it.categoriaNome == categoriaInput }
//            .forEach { despesa ->
//            valorfinal += despesa.valor
//            Log.i(TAG, valorfinal.toString())
//        }
//        return valorfinal
//
//    }

    fun calculaValorTotalPorCategoria(
        categoriaInput: String): Float{

        val mutableList = mutableListOf<DespesaComId>()

        listOf<DespesaComId>()
        despesasComId.value?.forEach {
            mutableList.add(it)
        }

        var valorfinal = 0.0F
        mutableList.filter { it.categoriaNome == categoriaInput }
            .forEach { despesa ->
                valorfinal += despesa.valor
                Log.i(TAG, valorfinal.toString())
            }
        return valorfinal

    }

    /////////////////////////////////////////////////////

    ////////Categorias//////////
     val categoriaLista = mutableListOf<Categoria>(
        Categoria(1, "Comida", R.drawable.cat_comida),
        Categoria(2, "Transporte", R.drawable.cat_transporte),
        Categoria(3, "Contas", R.drawable.cat_contas),
        Categoria(4, "Lazer", R.drawable.cat_lazer),
        Categoria(5, "Compras", R.drawable.cat_compras),
        Categoria(7, "Mercado", R.drawable.cat_mercado),
        Categoria(8, "Cartão", R.drawable.cat_cartao),
        Categoria(9, "Educação", R.drawable.cat_educacao),
        Categoria(10, "Pets", R.drawable.cat_pets),
        Categoria(11, "Presente", R.drawable.cat_presente),
        Categoria(12, "Roupas", R.drawable.cat_roupas),
        Categoria(13, "Saúde", R.drawable.cat_saude),
        Categoria(14, "Viagem", R.drawable.cat_viagem),
        Categoria(15, "Outros", R.drawable.cat_outros),

    )
    val categorias = MutableLiveData<List<Categoria>>()
    private val _textoCompartilhado = MutableLiveData<String>("")
   // val textoCompartilhado: LiveData<String> = _textoCompartilhado


    fun getListaPorCategoria(string: String): List<DespesaComId>{
        return despesasComId.value?.filter {
            it.categoriaNome.contains(string.capitalize())
        }?: emptyList()
    }

    fun somaValorTotalDespesas(){

        despesasComId.value?.forEach {
            Log.i(TAG, it.nome)
        }

    }

    init {
        observerColecaoDespesas()
        categorias.value = categoriaLista
        //somaValorTotalDespesas()

    }

}