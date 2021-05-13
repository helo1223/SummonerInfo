package hu.bme.aut.android.summonerinfo.feature.details.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.feature.details.DetailsActivity
import hu.bme.aut.android.summonerinfo.feature.details.Helper
import hu.bme.aut.android.summonerinfo.feature.details.fragment.DetailsMasteryFragment
import hu.bme.aut.android.summonerinfo.feature.details.fragment.data.MasteryContent

class DetailsMasteryAdapter(
        private val values: List<MasteryContent.MasteryItem>, private val fragment: DetailsMasteryFragment)
    : RecyclerView.Adapter<DetailsMasteryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_mastery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val cNameReg = "[^A-Za-z0-9]".toRegex()

        holder.nameView.text = Helper.championNamesMap[item.championId]

        var cName = holder.nameView.text.toString()

        when (cName) {
            "Cho'Gath" -> cName = "Chogath"
            "Vel'Koz" -> cName = "Velkoz"
            "Kha'Zix" -> cName = "Khazix"
            "Kai'Sa" -> cName = "Kaisa"
            "Nunu & Willump" -> cName = "Nunu"
            "LeBlanc" -> cName = "Leblanc"
            "Wukong" -> cName = "MonkeyKing"
        }

        Helper.loadImage(fragment, "https://ddragon.leagueoflegends.com/cdn/11.9.1/img/champion/", cNameReg.replace(cName, ""), holder.iconView)
        holder.levelView.text = item.championLevel.toString()
        var maxPoints = item.championPointsUntilNextLevel + item.championPointsSinceLastLevel

        var toNextLevelString = " (" + item.championPointsSinceLastLevel + "/" + maxPoints + ")"

        holder.pointsView.text = fragment.getString(R.string.masteryLevel, item.championPoints.toString() + (if (item.championLevel < 5) toNextLevelString else ""))
        var progress = (item.championPointsSinceLastLevel.toFloat() / maxPoints.toFloat()) * 100


        holder.progressBar.progress = progress.toInt()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.championNameTextView)
        val iconView: ImageView = view.findViewById(R.id.championImageView)
        val levelView: TextView = view.findViewById(R.id.levelTextView)
        val pointsView: TextView = view.findViewById(R.id.pointsTextView)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }
}