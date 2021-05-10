package hu.bme.aut.android.summonerinfo.feature.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.databinding.ActivityDetailsBinding
import hu.bme.aut.android.summonerinfo.feature.details.adapter.DetailsPagerAdapter
import hu.bme.aut.android.summonerinfo.model.League
import hu.bme.aut.android.summonerinfo.model.Profile
import hu.bme.aut.android.summonerinfo.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity(), ProfileDataHolder {


    private var profile: Profile? = null
    private var leagues: List<League> = ArrayList()
    private var matchIds: List<String> = ArrayList()


    private lateinit var binding: ActivityDetailsBinding

    companion object {
        private const val TAG = "DetailsActivity"
        const val EXTRA_SUMMONER_NAME = "extra.summoner_name"
    }

    private var summoner: String? = null

    val data = Intent()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        summoner = intent.getStringExtra(EXTRA_SUMMONER_NAME)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        loadProfile()
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        val detailsPagerAdapter =
                DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> getString(R.string.main)
                1 -> getString(R.string.details)
                2 -> getString(R.string.mastery)
                else -> ""
            }
        }.attach()


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getProfile(): Profile? = profile
    override fun getLeagues(): List<League> = leagues
    override fun getMatches(): List<String> = matchIds


    private fun loadProfile(){

        NetworkManager.getSummoner(summoner!!).enqueue(object : Callback<Profile?> {

            override fun onResponse(
                call: Call<Profile?>,
                response: Response<Profile?>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayProfile(response.body())
                    loadLeagues()
                    loadMatches()

                    supportActionBar!!.title = getString(R.string.profile, profile!!.name)
                    data.putExtra("SummonerName", profile!!.name)
                    data.putExtra("oldName", summoner)
                    setResult(1,data)


                } else {
                    Toast.makeText(
                        this@DetailsActivity,
                        "Profile error: " + response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                    data.putExtra("SummonerName", summoner)
                    setResult(-1,data)
                    finish()

                }
            }

            override fun onFailure(
                call: Call<Profile?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(
                    this@DetailsActivity,
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        })
    }

    private fun displayProfile(receivedProfile: Profile?) {
        profile = receivedProfile

        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter
    }


    private fun loadLeagues(){
        NetworkManager.getLeagues(profile!!.id!!).enqueue(object : Callback<List<League>> {

            override fun onResponse(
                call: Call<List<League>>,
                response: Response<List<League>>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayLeagues(response.body()!!)
                } else {
                    Toast.makeText(
                        this@DetailsActivity,
                        "League Error: " + response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(
                call: Call<List<League>>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(
                    this@DetailsActivity,
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun displayLeagues(receivedLeagues: List<League>) {

        leagues = receivedLeagues

        if(leagues.isNotEmpty() && leagues[0].queueType!="RANKED_SOLO_5x5"){
            leagues = leagues.reversed()
        }

        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter
    }

    private fun loadMatches() {

        NetworkManager.getLastNMatches(profile!!.puuid!!).enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                Log.d(TAG, "onResponse: " + response.code())
                if (response.isSuccessful) {
                    displayMatches(response.body()!!)
                }
            }

            override fun onFailure(
                call: Call<List<String>>,
                throwable: Throwable
            ) {

                throwable.printStackTrace()
                Toast.makeText(
                    this@DetailsActivity,
                    "Network request error occurred, check LOG",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun displayMatches(receivedMatches: List<String>) {
        matchIds = receivedMatches

        val detailsPagerAdapter = DetailsPagerAdapter(this)
        binding.mainViewPager.adapter = detailsPagerAdapter
    }






}