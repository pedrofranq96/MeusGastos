package br.edu.infnet.meusgastos.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.infnet.meusgastos.databinding.FragmentResumoBinding


private val TAG = "MOEDAS"

private lateinit var _binding: FragmentResumoBinding

private val binding get() = _binding!!

class ResumoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResumoBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.i(TAG, "ResumoFragment iniciado!")
        return view
    }

}