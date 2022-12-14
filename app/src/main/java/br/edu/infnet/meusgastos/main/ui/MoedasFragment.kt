package br.edu.infnet.meusgastos.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.infnet.meusgastos.R
import br.edu.infnet.meusgastos.databinding.FragmentMoedasBinding
import br.edu.infnet.meusgastos.main.ui.api.Endpoint
import br.edu.infnet.meusgastos.models.Moeda
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://economia.awesomeapi.com.br/json/daily/"

class MoedasFragment : Fragment() {

    private lateinit var _binding: FragmentMoedasBinding

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoedasBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    getCotacaoEURBRL()
    getCotacaoUSBBRL()
   // getCotacaoBTCEUR()
    }

    fun getCotacaoUSBBRL(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Endpoint::class.java)

        GlobalScope.launch(Dispatchers.Main){
            val response = api.getUSDBRL().awaitResponse()
            if(response.isSuccessful){
                for(moeda in response.body()!!){
                    binding.tvMoedaNome.text = moeda.name
                    binding.tvMoedaAlta.text = moeda.high
                    binding.tvMoedaBaixa.text = moeda.low
                }
            }

        }
    }

    fun getCotacaoEURBRL(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Endpoint::class.java)

        GlobalScope.launch(Dispatchers.Main){
            val response = api.getEURBRL().awaitResponse()
            if(response.isSuccessful){
                for(moeda in response.body()!!){
                    binding.tvMoedaNome2.text = moeda.name
                    binding.tvMoedaAlta2.text = moeda.high
                    binding.tvMoedaBaixa2.text = moeda.low
                }
            }

        }
    }
    fun getCotacaoBTCEUR(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Endpoint::class.java)

        GlobalScope.launch(Dispatchers.Main){
            val response = api.getBTCEUR().awaitResponse()
            if(response.isSuccessful){
                for(moeda in response.body()!!){
                    binding.tvMoedaNome.text = moeda.name
                    binding.tvMoedaAlta.text = moeda.high
                    binding.tvMoedaBaixa.text = moeda.low
                }
            }

        }
    }




}