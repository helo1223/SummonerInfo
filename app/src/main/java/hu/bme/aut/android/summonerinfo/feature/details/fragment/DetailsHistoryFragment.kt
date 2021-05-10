package hu.bme.aut.android.summonerinfo.feature.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.summonerinfo.databinding.FragmentDetailsMoreBinding
import hu.bme.aut.android.summonerinfo.feature.details.ProfileDataHolder
import hu.bme.aut.android.summonerinfo.feature.details.adapter.DetailHistoryAdapter
import hu.bme.aut.android.summonerinfo.model.MatchDto
import hu.bme.aut.android.summonerinfo.model.MatchParticipant


class DetailsHistoryFragment : Fragment() {

    private lateinit var binding : FragmentDetailsMoreBinding

    public var summonerSpellsMap : HashMap<Int, String> = HashMap()

    private var profileDataHolder: ProfileDataHolder? = null

    private lateinit var  adapter: DetailHistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileDataHolder = if (activity is ProfileDataHolder) {
            activity as ProfileDataHolder?
        } else {
            throw RuntimeException("Activity must implement ProfileDataHolder interface!")
        }
        binding = FragmentDetailsMoreBinding.inflate(layoutInflater)

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        displayMatches()
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        return binding.root
    }


    private fun initRecyclerView() {
        binding.mainRecyclerView1.layoutManager = LinearLayoutManager(this.context)
        adapter = DetailHistoryAdapter()
        adapter.setFragment(this)
        binding.mainRecyclerView1.adapter = adapter
        initSpellMap()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayMatches()
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

    private fun displayMatches() {
        adapter.clearHistory()
        val mm = profileDataHolder!!.getMatchDtos()
        if(mm!!.isNotEmpty()) {
            for (matchdto : MatchDto? in mm){
                adapter.addHistory(matchdto!!)
            }
        }
    }

    fun findPlayerIndex(matchDto: MatchDto): MatchParticipant {
        for (participant in matchDto.info!!.participants!!) {
            if(participant.summonerId == profileDataHolder!!.getProfile()!!.id){
                return participant
            }
        }
        return matchDto.info!!.participants!![0]
    }


}

