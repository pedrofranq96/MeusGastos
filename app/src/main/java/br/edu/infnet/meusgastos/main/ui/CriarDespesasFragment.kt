package br.edu.infnet.meusgastos.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.edu.infnet.meusgastos.databinding.FragmentCriarDespesaBinding
import br.edu.infnet.meusgastos.models.Despesa
import br.edu.infnet.meusgastos.utils.*
import java.time.LocalDate

class CriarDespesasFragment : Fragment() {

    val viewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentCriarDespesaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCriarDespesaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnCriarDespesa.setOnClickListener {
            onCriarClick()
        }
    }

    private fun onCriarClick() {
        val despesa = getDespesaFromInputs()

        viewModel.criarDespesa(despesa)
            .addOnSuccessListener { documentReference ->
                toast("Criado com sucesso com id: ${documentReference.id}")
                navUp()
            }
            .addOnFailureListener { e ->
                toast("Falha ao criar Despesa")
            }
    }


    //Alteração da data: Date(Despesa.model) para LocalDate devido a essa fun
    //Testar se o create funciona assim


    @SuppressLint("NewApi")
    private fun getDespesaFromInputs(): Despesa {

        binding.apply {
            val data = LocalDate.parse(inputDataDespesa.toString())
            Log.d("Data Despesa: ", data.toString())
            return Despesa(
                nome = getTextInput(inputNomeDespesa) ,
                valor = getDoubleInput(inputValorDespesa),
                data = data,
                descricao =  getTextInput(inputDescricaoDespesa),
                categoriaId = getIntInput(inputCategoriaDespesa)
            )
        }
    }


    private fun setupViews() {
        activity?.setTitle("Criar Despesa")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}