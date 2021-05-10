package hu.bme.aut.android.summonerinfo.feature.details.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.databinding.ItemHistoryBinding
import hu.bme.aut.android.summonerinfo.feature.details.fragment.DetailsHistoryFragment
import hu.bme.aut.android.summonerinfo.model.MatchDto

class DetailHistoryAdapter : RecyclerView.Adapter<DetailHistoryAdapter.HistoryViewHolder>() {
    private var history: MutableList<MatchDto> = ArrayList()

    private lateinit var fragment: DetailsHistoryFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = history[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = history.size

    fun addHistory(newHistory: MatchDto) {
        history.add(newHistory)
        notifyItemInserted(history.size - 1)
    }

    fun clearHistory(){
        history.clear()
        notifyDataSetChanged()
    }


    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(newHistory: MatchDto) {
            val player = fragment.findPlayerIndex(newHistory)
            binding.championNameTextView.text = fragment.getString(R.string.champ,player.championName, player.champLevel)
            binding.gameModeTextView.text = newHistory.info!!.gameMode
            binding.kdaTextView.text = fragment.getString(R.string.kda,player.kills,player.deaths,player.assists)
            if(player.win == true){
                binding.winTextView.text = fragment.getString(R.string.result,"Victory")
                binding.winTextView.setTextColor(Color.rgb(30,130,30))
                binding.historyHorizontalLayout.setBackgroundColor(Color.rgb(0,170,255))
            }else{
                binding.winTextView.text = fragment.getString(R.string.result,"Defeat")
                binding.winTextView.setTextColor(Color.rgb(0,0,0))
                binding.historyHorizontalLayout.setBackgroundColor(Color.rgb(255,68,68))
            }

            if(player.championName=="FiddleSticks") player.championName="Fiddlesticks"

            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/champion/", player.championName!!, binding.championImageViewText)
            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/spell/", fragment.summonerSpellsMap[player.summoner1Id]!!, binding.spell1ImageView)
            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/spell/", fragment.summonerSpellsMap[player.summoner2Id]!!, binding.spell2ImageView)

            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", player.item0.toString(), binding.item0ImageView)
            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", player.item1.toString(), binding.item1ImageView)
            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", player.item2.toString(), binding.item2ImageView)
            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", player.item3.toString(), binding.item3ImageView)
            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", player.item4.toString(), binding.item4ImageView)
            loadImage("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", player.item5.toString(), binding.item5ImageView)


        }
    }

    private fun loadImage(url: String, param: String, target: ImageView){
       Glide.with(fragment)
                .load("$url$param.png")
                .transition(DrawableTransitionOptions().crossFade())
                .into(target)
    }

    fun setFragment(fragment: DetailsHistoryFragment){
        this.fragment = fragment
    }



}