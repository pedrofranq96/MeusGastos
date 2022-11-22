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
import java.time.format.DateTimeFormatter


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
        setup()
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

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        binding.apply {
            Log.e("Nome: ", inputNomeDespesa.text.toString())
            Log.e("Valor: ", inputValorDespesa.text.toString())
            Log.e("Descricao: ", inputDescricaoDespesa.text.toString())
            Log.e("Data Despesa(input): ", inputDataDespesa.text.toString())
            val data = LocalDate.parse(inputDataDespesa.text.toString(), formatter)
            //val data = LocalDate.parse(inputDataDespesa.toString(), formatter)

            Log.e("Data Despesa: ", data.toString())

            return Despesa(
                nome = getTextInput(inputNomeDespesa) ,
                valor = getFloatInput(inputValorDespesa),
                data = data.toString(),
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