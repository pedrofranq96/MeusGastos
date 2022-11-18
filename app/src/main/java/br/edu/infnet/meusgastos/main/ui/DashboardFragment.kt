package br.edu.infnet.meusgastos.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.databinding.FragmentDashboardBinding
import br.edu.infnet.meusgastos.utils.nav

class DashboardFragment : Fragment(){

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

        setup()
        return view
    }

    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        activity?.setTitle("Dashboard")
    }

    private fun setupClickListeners() {
        binding.apply {
            btnRedirectCriarDespesa.setOnClickListener {
                nav(R.id.action_dashboardFragment_to_criarDespesasFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}