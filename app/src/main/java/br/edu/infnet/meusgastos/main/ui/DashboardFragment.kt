package br.edu.infnet.meusgastos.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.databinding.FragmentDashboardBinding
import br.edu.infnet.meusgastos.main.ui.adapters.DespesaComIdAdapter
import br.edu.infnet.meusgastos.main.ui.adapters.DespesaComIdListener
import br.edu.infnet.meusgastos.models.DespesaComId
import br.edu.infnet.meusgastos.utils.nav


class DashboardFragment : Fragment(){

    val TAG = "Dashboard"
    val viewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentDashboardBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        val names = listOf<String>("Comida","Transporte","Contas","Compras","Lazer","Cartão","Mercado", "Educação", "Pets", "Presente", "Roupas", "Saúde", "Viagem", "Outros")
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, names)
        binding.etText.setAdapter(adapter)

        setup()
        return view
    }



    private fun setup() {
        setupViews()
        setupClickListeners()
        setupRecyclerView()
        setupObservers()
    }

    val adapter = DespesaComIdAdapter(
       object : DespesaComIdListener{
           override fun onEditClick(despesa: DespesaComId) {
               viewModel.setSelectedDespesaComId(despesa)
               nav(R.id.action_dashboardFragment_to_editarDespesaFragment)
           }

           override fun onDeleteClick(despesa: DespesaComId) {

               //viewModel.setSelectedDespesaComId(despesa)
               viewModel.deleteDespesa(despesa.id)
               Log.d(TAG, "Delete Clicado")
           }
       }
    )

    private fun setupRecyclerView() {
        binding.rvDashboardDespesas.adapter = adapter
        binding.rvDashboardDespesas.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupObservers(){
        viewModel.despesasComId.observe(viewLifecycleOwner){
            atualizaRecyclerView(it)
        }
    }

    fun atualizaRecyclerView(lista: List<DespesaComId>?) {
            //adapter.submitList(lista)
            val nomeCategoria = binding.etText.text.toString()
            adapter.submitList(viewModel.getListaPorCategoria(nomeCategoria))
            binding.rvDashboardDespesas.adapter = adapter
    }

    fun atualizaRecyclerViewBusca(nome: String) {
        //adapter.submitList(lista)
        //val nomeCategoria = binding.etText.text.toString()
        adapter.submitList(viewModel.getListaPorCategoria(nome))
        binding.rvDashboardDespesas.adapter = adapter
    }

    private fun setupViews() {
        activity?.setTitle("Dashboard")
        binding.tvTotalDespesas.setText(viewModel.totalDespesas.toString())

    }

    private fun setupClickListeners() {
        binding.apply {
            btnRedirectCriarDespesa.setOnClickListener {
                nav(R.id.action_dashboardFragment_to_criarDespesasFragment)
            }
            imbBuscar.setOnClickListener{
                atualizaRecyclerViewBusca(binding.etText.text.toString())
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}