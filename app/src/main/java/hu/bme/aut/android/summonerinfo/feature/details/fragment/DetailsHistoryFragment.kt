package hu.bme.aut.android.summonerinfo.feature.details.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.feature.details.ProfileDataHolder
import hu.bme.aut.android.summonerinfo.feature.details.adapter.DetailsHistoryAdapter
import hu.bme.aut.android.summonerinfo.feature.details.fragment.data.HistoryContent
import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.model.MatchParticipant
import hu.bme.aut.android.summonerinfo.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsHistoryFragment : Fragment() {

    private var columnCount = 1
    var summonerSpellsMap : HashMap<Int, String> = HashMap()
    private var profileDataHolder: ProfileDataHolder? = null

    private var vAdapter : DetailsHistoryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileDataHolder = if (activity is ProfileDataHolder) {
            activity as ProfileDataHolder?
        } else {
            throw RuntimeException("Activity must implement ProfileDataHolder interface!")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details_more, container, false)
        initSpellMap()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = DetailsHistoryAdapter(HistoryContent.ITEMS, getFragment())
                vAdapter = adapter as DetailsHistoryAdapter
            }
        }
        HistoryContent.clearMatches()

        profileDataHolder!!.getMatches().forEach { loadMatch(it); vAdapter!!.notifyDataSetChanged() }

        return view
    }


    fun loadImage(url: String, param: String, target: ImageView){
        Glide.with(this)
            .load("$url$param.png")
            .transition(DrawableTransitionOptions().crossFade())
            .into(target)
    }

    private fun initSpellMap(){
        summonerSpellsMap[1] = "SummonerCleanse"
        summonerSpellsMap[3] = "SummonerExhaust"
        summonerSpellsMap[4] = "SummonerFlash"
        summonerSpellsMap[6] = "SummonerHaste"
        summonerSpellsMap[7] = "SummonerHeal"
        summonerSpellsMap[11] = "SummonerSmite"
        summonerSpellsMap[12] = "SummonerTeleport"
        summonerSpellsMap[13] = "SummonerMana"
        summonerSpellsMap[14] = "SummonerDot"
        summonerSpellsMap[21] = "SummonerBarrier"
        summonerSpellsMap[32] = "SummonerSnowball"
    }

    private fun getFragment() : DetailsHistoryFragment{
        return this
    }

    private fun loadMatch(matchId: String) {
        NetworkManager.getMMatches(matchId).enqueue(object : Callback<MatchDto> {
            override fun onResponse(
                call: Call<MatchDto>,
                response: Response<MatchDto>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayMatch(response.body()!!)
                }
            }

            override fun onFailure(
                call: Call<MatchDto>,
                throwable: Throwable
            ) {

                throwable.printStackTrace()
                Toast.makeText(
                    this@DetailsHistoryFragment.context,
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun displayMatch(receivedMatchDto: MatchDto) {
        HistoryContent.displayMatches(receivedMatchDto, profileDataHolder!!.getProfile()!!.id!!)
        vAdapter!!.notifyDataSetChanged()
    }

}

