package eu.anifantakis.elections.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import eu.anifantakis.elections.ElectionResult
import eu.anifantakis.elections.databinding.RowItemBinding

class ElectionAdapter(val clickListener: ElectionClickListener): ListAdapter<ElectionResult, ElectionAdapter.AsteroidAdapterViewHolder>(DiffCallback){
    companion object DiffCallback: DiffUtil.ItemCallback<ElectionResult>() {
        // Same item if the items share the same id
        override fun areItemsTheSame(oldItem: ElectionResult, newItem: ElectionResult)    = (oldItem.idparty == newItem.idparty)

        // Same contents if dataclasses' equality is the same
        override fun areContentsTheSame(oldItem: ElectionResult, newItem: ElectionResult) = (oldItem == newItem)
    }

    class ElectionClickListener(val clickListener: (electionResult: ElectionResult) -> Unit) {
        fun onClick(party_id: ElectionResult) = clickListener(party_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionAdapter.AsteroidAdapterViewHolder {
        return AsteroidAdapterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionAdapter.AsteroidAdapterViewHolder, position: Int) {
        val electionResult = getItem(position)
        holder.bind(clickListener, electionResult)
    }

    class AsteroidAdapterViewHolder private constructor (private val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: ElectionClickListener, electionResult: ElectionResult){
            binding.electionResult = electionResult
            binding.clickListener = clickListener

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidAdapterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowItemBinding.inflate(layoutInflater, parent, false)
                return AsteroidAdapterViewHolder(binding)
            }
        }
    }
}
