package hu.bme.aut.android.summonerinfo.feature.details.fragment.data

import hu.bme.aut.android.summonerinfo.feature.details.ProfileDataHolder
import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.model.MatchParticipant
import java.util.ArrayList
import java.util.HashMap


object HistoryContent {


    val ITEMS: ArrayList<HistoryItem> = ArrayList()

    private fun addItem(item: HistoryItem) {
        ITEMS.add(item)
    }

    fun displayMatches(profileDataHolder: ProfileDataHolder) {
        val mm = profileDataHolder.getMatchDtos()
        if(mm!!.isNotEmpty()) {
            for (matchdto : MatchDto? in mm){
                var historyItem = HistoryItem(
                    matchdto!!.info!!.findPlayerIndex(profileDataHolder.getProfile()!!.id!!), matchdto)
                addItem(historyItem)
            }
        }
    }

    data class HistoryItem(val player: MatchParticipant, val matchDto: MatchDto) {
    }
}