package hu.bme.aut.android.summonerinfo.feature.details.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.feature.details.DetailsActivity
import hu.bme.aut.android.summonerinfo.feature.details.ProfileDataHolder
import hu.bme.aut.android.summonerinfo.feature.details.adapter.DetailsHistoryAdapter
import hu.bme.aut.android.summonerinfo.feature.details.adapter.DetailsMasteryAdapter
import hu.bme.aut.android.summonerinfo.feature.details.fragment.data.HistoryContent
import hu.bme.aut.android.summonerinfo.feature.details.fragment.data.MasteryContent
import hu.bme.aut.android.summonerinfo.model.ChampionMasteryDto
import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsMasteryFragment : Fragment() {

    private var columnCount = 1


    private var profileDataHolder: ProfileDataHolder? = null

    private var vAdapter : DetailsMasteryAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileDataHolder = if (activity is ProfileDataHolder) {
            activity as ProfileDataHolder?
        } else {
            throw RuntimeException("Activity must implement ProfileDataHolder interface!")
        }

        vAdapter = DetailsMasteryAdapter(MasteryContent.ITEMS, this)

        MasteryContent.clearMasteries()

        if(profileDataHolder!!.getProfile()!=null)
        loadMasteries(profileDataHolder!!.getProfile()!!.id!!)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details_mastery, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = vAdapter
            }
        }
        return view
    }

    private fun loadMasteries(encryptedSummonerId: String) {
        NetworkManager.getSummonerMasteries(encryptedSummonerId).enqueue(object : Callback<List<ChampionMasteryDto>> {
            override fun onResponse(
                    call: Call<List<ChampionMasteryDto>>,
                    response: Response<List<ChampionMasteryDto>>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    response.body()!!.forEach { displayMastery(it); vAdapter!!.notifyDataSetChanged()}
                }
            }

            override fun onFailure(
                    call: Call<List<ChampionMasteryDto>>,
                    throwable: Throwable
            ) {

                throwable.printStackTrace()
                Toast.makeText(
                        this@DetailsMasteryFragment.context,
                        "Network request error occurred, check LOG",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun displayMastery(receivedMasteryDto: ChampionMasteryDto) {
        MasteryContent.displayMasteries(receivedMasteryDto)
        vAdapter!!.notifyDataSetChanged()
    }
}