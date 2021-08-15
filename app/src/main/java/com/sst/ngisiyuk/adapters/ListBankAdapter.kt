package com.sst.ngisiyuk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sst.ngisiyuk.databinding.ListBankLayoutBinding
import com.sst.ngisiyuk.models.ngisiyuk.DataXXXXXXXXXX
import com.sst.ngisiyuk.viewmodels.TopUpViewModel
import net.cachapa.expandablelayout.ExpandableLayout

class ListBankAdapter(
    val data: List<DataXXXXXXXXXX>,
    val topUpModel: TopUpViewModel,
    val expandableHeader: ExpandableLayout
) :RecyclerView.Adapter<ListBankAdapter.ListBankViewHolder> (){
    class ListBankViewHolder (val binding: ListBankLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBankViewHolder {
        val binding = ListBankLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListBankViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListBankViewHolder, position: Int) {
        with(holder){
            with(data[position]){
                binding.root.setOnClickListener {
                    val chosenBank = data.find {
                        it.nama_bank == nama_bank
                    }


                    if (chosenBank != null) {
                        topUpModel.setChosenBank(chosenBank)
                        expandableHeader.isExpanded = !expandableHeader.isExpanded
                    }
                }

                binding.listBankRvLayout.text = nama_bank
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }



}
