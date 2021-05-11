package hu.bme.aut.android.summonerinfo.model

class MatchInfo{
    var gameCreation: String? = null
    var gameMode: String? = null
    var participants: ArrayList<MatchParticipant>? = null

    fun findPlayerIndex(summonerId: String): MatchParticipant {
        for (participant in participants!!) {
            if(participant.summonerId == summonerId){
                return participant
            }
        }
        return participants!![0]
    }
}