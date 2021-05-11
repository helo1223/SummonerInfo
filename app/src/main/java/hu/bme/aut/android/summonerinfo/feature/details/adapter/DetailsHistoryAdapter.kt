package hu.bme.aut.android.summonerinfo.feature.details.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.feature.details.Helper
import hu.bme.aut.android.summonerinfo.feature.details.fragment.DetailsHistoryFragment
import hu.bme.aut.android.summonerinfo.feature.details.fragment.data.HistoryContent

class DetailsHistoryAdapter(
    private val historyEntries: List<HistoryContent.HistoryItem>,
    private val fragment: DetailsHistoryFragment)
    : RecyclerView.Adapter<DetailsHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyEntries[position]
        holder.championNameTextView.text = fragment.getString(R.string.champ, Helper.championNamesMap[item.player.championId], item.player.champLevel)
        holder.gameModeTextView.text = item.matchDto.info!!.gameMode
        holder.kdaTextView.text = fragment.getString(R.string.kda,item.player.kills,item.player.deaths,item.player.assists)
        if(item.player.win!!){
            holder.winTextView.text = fragment.getString(R.string.result,"Victory")
            holder.winTextView.setTextColor(Color.rgb(30,130,30))
            holder.historyHorizontalLayout.setBackgroundColor(Color.rgb(0,170,255))
        }else{
            holder.winTextView.text = fragment.getString(R.string.result,"Defeat")
            holder.winTextView.setTextColor(Color.rgb(0,0,0))
            holder.historyHorizontalLayout.setBackgroundColor(Color.rgb(255,68,68))
        }

        if(item.player.championName=="FiddleSticks") item.player.championName="Fiddlesticks"


        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/champion/", item.player.championName!!, holder.championImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/spell/", fragment.summonerSpellsMap[item.player.summoner1Id]!!, holder.spell1ImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/spell/", fragment.summonerSpellsMap[item.player.summoner2Id]!!, holder.spell2ImageView)

        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", item.player.item0.toString(), holder.item0ImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", item.player.item1.toString(), holder.item1ImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", item.player.item2.toString(), holder.item2ImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", item.player.item3.toString(), holder.item3ImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", item.player.item4.toString(), holder.item4ImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", item.player.item5.toString(), holder.item5ImageView)
        Helper.loadImage(fragment,"https://ddragon.leagueoflegends.com/cdn/11.9.1/img/item/", item.player.item6.toString(), holder.item6ImageView)

    }

    override fun getItemCount(): Int = historyEntries.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val championImageView: ImageView = view.findViewById(R.id.championImageView)
        val championNameTextView: TextView = view.findViewById(R.id.championNameTextView)
        val spell1ImageView: ImageView = view.findViewById(R.id.spell1ImageView)
        val spell2ImageView: ImageView = view.findViewById(R.id.spell2ImageView)
        val winTextView: TextView = view.findViewById(R.id.winTextView)
        val gameModeTextView: TextView = view.findViewById(R.id.gameModeTextView)
        val kdaTextView: TextView = view.findViewById(R.id.kdaTextView)
        val item0ImageView: ImageView = view.findViewById(R.id.item0ImageView)
        val item1ImageView: ImageView = view.findViewById(R.id.item1ImageView)
        val item2ImageView: ImageView = view.findViewById(R.id.item2ImageView)
        val item3ImageView: ImageView = view.findViewById(R.id.item3ImageView)
        val item4ImageView: ImageView = view.findViewById(R.id.item4ImageView)
        val item5ImageView: ImageView = view.findViewById(R.id.item5ImageView)
        val item6ImageView: ImageView = view.findViewById(R.id.item6ImageView)
        val historyHorizontalLayout: LinearLayout = view.findViewById(R.id.historyHorizontalLayout)
    }
}