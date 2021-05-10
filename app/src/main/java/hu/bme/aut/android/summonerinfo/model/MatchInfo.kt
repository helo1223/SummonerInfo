package hu.bme.aut.android.summonerinfo.model

class MatchInfo{
    var gameCreation: String? = null
    var gameDuration: Int = 0
    var gameMode: String? = null
    var mapId: Int = 0
    var participants: List<MatchParticipant>? = null

    fun findPlayerIndex(summonerId: String): MatchParticipant {
        for (participant in participants!!) {
            if(participant.summonerId == summonerId.toString()){
                return participant
            }
        }
        return participants!![0]
    }
}