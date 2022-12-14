package br.edu.infnet.meusgastos.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import br.edu.infnet.meusgastos.databinding.FragmentSignOnBinding
import br.edu.infnet.meusgastos.main.ui.MainActivity
import br.edu.infnet.meusgastos.utils.getTextInput
import br.edu.infnet.meusgastos.utils.toast

class SignOnFragment : Fragment(){

    val viewModel by activityViewModels<LoginViewModel> ()

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Usar a vinculação de visualizações em fragmentos
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments

    private var _binding: FragmentSignOnBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignOnBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Setup ///////////////////////////////////////////////////////////////////////////////////////

    private fun setup(){
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {
            btnCadastrar.setOnClickListener {

                onSignOnClick()


            }
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // Eventos de clique ///////////////////////////////////////////////////////////////////////////
    private fun onSignOnClick() {

        binding.apply {
            val email = getTextInput(inputEmail2)
            val password = getTextInput(inputPassword2)
            val confirmPassword = getTextInput(inputConfirmPassword2)

            if ( (password == confirmPassword) && password.length > 5){
                signOn(email, password)
            }
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////


    fun signOn(email: String, password: String){
        viewModel.signOn(email, password)
            .addOnSuccessListener {
                toast("Cadastrado com Sucesso")
                startMainActivity()
            }
            .addOnFailureListener {
                toast("Falha ao cadastrar\n${it.message}")
            }
    }

    fun startMainActivity(){
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }
}