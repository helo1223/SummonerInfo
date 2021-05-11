package hu.bme.aut.android.summonerinfo.feature.summoner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.summonerinfo.databinding.ItemSummonerBinding

class SummonerAdapter(private val listener: OnSummonerSelectedListener) : RecyclerView.Adapter<SummonerAdapter.SummonerViewHolder>() {
    private var summoners: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SummonerViewHolder(
        ItemSummonerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SummonerViewHolder, position: Int) {
        val item = summoners[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = summoners.size

    fun addSummoner(newSummoner: String) : Boolean{
        if(getSummonerPosition(newSummoner)==-1 && newSummoner.length in 3..16) {
            summoners.add(newSummoner)
            notifyItemInserted(summoners.size - 1)
            return true
        }
        return false
    }

    fun removeSummoner(position: Int) {
        summoners.removeAt(position)
        notifyItemRemoved(position)
        if (position < summoners.size) {
            notifyItemRangeChanged(position, summoners.size - position)
        }
    }

    fun renameSummonerEntry(position : Int, newName : String){
        summoners[position] = newName
        notifyItemChanged(position)
    }

    fun getSummonerPosition(summoner: String) : Int{
        var index = -1
        for(name : String in summoners){
            if(name.equals(summoner, ignoreCase = true)){
                index = summoners.indexOf(name)
            }
        }
        return index
    }

    inner class SummonerViewHolder(private val binding: ItemSummonerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var item: String? = null

        init {
            binding.root.setOnClickListener {
                listener.onSummonerSelected(item)
            }

            binding.summonerItemRemoveButton.setOnClickListener{
                removeSummoner(summoners.indexOf(item))
            }
        }

        fun bind(newSummoner: String?) {
            item = newSummoner
            binding.summonerItemNameTextView.text = item
        }
    }

    interface OnSummonerSelectedListener {
        fun onSummonerSelected(summoner: String?)
    }

}