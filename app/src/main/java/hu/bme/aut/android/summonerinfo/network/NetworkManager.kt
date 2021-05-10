package hu.bme.aut.android.summonerinfo.network

import hu.bme.aut.android.summonerinfo.model.League
import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.model.Profile
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private var retrofit: Retrofit
    private val riotApi: RiotApi

    private var SERVICE_URL = "https://eun1.api.riotgames.com"
    private const val API_KEY = "RGAPI-d53e9404-0980-4cd7-b0c4-284f1bb87c1a"

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        riotApi = retrofit.create(RiotApi::class.java)
    }

    fun getSummoner(summonerName: String?): Call<Profile>? {
        return riotApi.getSummonerByName(summonerName, API_KEY)
    }

    fun getLeagues(encryptedSummonerId: String?): Call<List<League>>? {
        return riotApi.getLeaguesByEncryptedSummonerId(encryptedSummonerId, API_KEY)
    }

    fun getLastNMatches(puuid: String?): Call<List<String>>{
        return riotApi.getMatchesByPuuid(puuid, 15, API_KEY)
    }

    fun getMMatches(matchId: String?) :Call<MatchDto>?{
        return riotApi.getMatchByMatchId(matchId, API_KEY)
    }
}