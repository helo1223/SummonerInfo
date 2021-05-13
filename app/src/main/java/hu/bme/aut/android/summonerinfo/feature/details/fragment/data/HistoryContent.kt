package hu.bme.aut.android.summonerinfo.feature.details.fragment.data

import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.model.MatchParticipant
import java.util.ArrayList


object HistoryContent {

    val ITEMS: ArrayList<HistoryItem> = ArrayList()

    private fun addItem(item: HistoryItem) {
        ITEMS.add(item)
    }

    fun clearMatches() {
        ITEMS.clear()
    }

    fun displayMatches(mm: MatchDto, summoner: String) {
        addItem(HistoryItem(mm.info!!.findPlayerIndex(summoner), mm))
        ITEMS.sortByDescending { it.matchDto.info!!.gameCreation }
    }

    data class HistoryItem(val player: MatchParticipant, val matchDto: MatchDto)
}