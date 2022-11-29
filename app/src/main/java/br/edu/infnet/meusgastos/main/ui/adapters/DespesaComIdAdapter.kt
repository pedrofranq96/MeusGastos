package br.edu.infnet.meusgastos.main.ui.adapters

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.meusgastos.databinding.DespesaListItemBinding
import br.edu.infnet.meusgastos.databinding.FragmentDashboardBinding
import br.edu.infnet.meusgastos.models.DespesaComId


class DespesaComIdAdapter(val listener: DespesaComIdListener) :
    ListAdapter<
            DespesaComId,
            DespesaComIdAdapter.ViewHolder
            >(DespesaComIdDiffCallback()) {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item, position)
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            return ViewHolder.from(parent, listener)
        }



        class ViewHolder private constructor(
            val binding: DespesaListItemBinding,
            val listener: DespesaComIdListener
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: DespesaComId, position: Int) {
                binding.apply {

                    tvRvNome.text = item.nome
                    tvRvValor.text = item.valor.toString()
                    tvRvData.text = item.data


//                    ivEdit.setOnClickListener {
//                        listener.onEditClick(item)
//                    }
//                    ivDelete.setOnClickListener {
//                        listener.onDeleteClick(item)
//                    }

                }
            }

            companion object {
                fun from(parent: ViewGroup, listener: DespesaComIdListener): ViewHolder {
                    val layoutInflater = LayoutInflater.from(parent.context)
                    val binding = DespesaListItemBinding.inflate(
                        layoutInflater, parent, false
                    )
                    return ViewHolder(binding, listener)
                }
            }
        }
    
}
class DespesaComIdDiffCallback : DiffUtil.ItemCallback<DespesaComId>() {

    override fun areItemsTheSame(oldItem: DespesaComId, newItem: DespesaComId): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DespesaComId, newItem: DespesaComId): Boolean {
        return oldItem == newItem
    }
}



    interface DespesaComIdListener {
        fun onEditClick(despesa: DespesaComId)
        fun onDeleteClick(despesa: DespesaComId)
    }    