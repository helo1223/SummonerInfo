package hu.bme.aut.android.summonerinfo.feature.details.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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


/*class DetailsHistoryFragment : Fragment() {

    private lateinit var binding : FragmentDetailsMoreBinding


    private var profileDataHolder: ProfileDataHolder? = null

    private lateinit var  adapter: DetailsHistoryAdapter


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
        adapter = DetailsHistoryAdapter()
        adapter.setFragment(this)
        binding.mainRecyclerView1.adapter = adapter
        initSpellMap()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayMatches()
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

    fun loadImage(url: String, param: String, target: ImageView){
        Glide.with(this)
            .load("$url$param.png")
            .transition(DrawableTransitionOptions().crossFade())
            .into(target)
    }


}*/

class DetailsHistoryFragment : Fragment() {

    private var columnCount = 1
    var summonerSpellsMap : HashMap<Int, String> = HashMap()
    private var profileDataHolder: ProfileDataHolder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileDataHolder = if (activity is ProfileDataHolder) {
            activity as ProfileDataHolder?
        } else {
            throw RuntimeException("Activity must implement ProfileDataHolder interface!")
        }

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
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
            }
        }

        HistoryContent.displayMatches(profileDataHolder!!)

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



    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            DetailsHistoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

