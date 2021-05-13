package hu.bme.aut.android.summonerinfo.feature.details.fragment.data

import hu.bme.aut.android.summonerinfo.model.ChampionMasteryDto
import java.util.ArrayList

object MasteryContent {

    val ITEMS: ArrayList<MasteryItem> = ArrayList()

    private fun addItem(item: MasteryItem) {
        ITEMS.add(item)
    }

    fun clearMasteries() {
        ITEMS.clear()
    }

    fun displayMasteries(mastery: ChampionMasteryDto) {
        addItem(MasteryItem(mastery.championId, mastery.championLevel, mastery.championPoints, mastery.championPointsSinceLastLevel, mastery.championPointsUntilNextLevel))
        ITEMS.sortByDescending { it.championPoints }
    }


    data class MasteryItem(val championId: Int, val championLevel: Int, val championPoints: Int, val championPointsSinceLastLevel: Int, val championPointsUntilNextLevel: Int)
}