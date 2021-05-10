package hu.bme.aut.android.summonerinfo.network

import hu.bme.aut.android.summonerinfo.model.League
import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.model.Profile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RiotApi {

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    fun getSummonerByName(
            @Path("summonerName") summonerName: String,
            @Query("api_key") api_key: String

    ): Call<Profile>

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getLeaguesByEncryptedSummonerId(
            @Path("encryptedSummonerId") encryptedSummonerId: String,
            @Query("api_key") api_key: String
    ): Call<List<League>>

    @GET("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/{puuid}/ids")
    fun getMatchesByPuuid(
            @Path("puuid") puuid: String,
            @Query("count") count: Int,
            @Query("api_key") api_key: String

    ): Call<List<String>>

    @GET("https://europe.api.riotgames.com/lol/match/v5/matches/{matchId}")
    fun getMatchByMatchId(
            @Path("matchId") matchId: String,
            @Query("api_key") api_key: String
    ): Call<MatchDto>
}