package hu.bme.aut.android.summonerinfo.feature.details

import hu.bme.aut.android.summonerinfo.model.League
import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.model.Profile

interface ProfileDataHolder {
    fun getProfile(): Profile?
    fun getLeagues(): List<League>?
    fun getMatches(): List<String>?
    fun getMatchDtos(): List<MatchDto?>?
}