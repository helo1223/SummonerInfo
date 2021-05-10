package hu.bme.aut.android.summonerinfo.feature.details.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.databinding.FragmentDetailsMainBinding
import hu.bme.aut.android.summonerinfo.feature.details.ProfileDataHolder
import hu.bme.aut.android.summonerinfo.model.League
import java.text.DecimalFormat

class DetailsMainFragment : Fragment() {

    private var _binding: FragmentDetailsMainBinding? = null
    private val binding get() = _binding!!

    private var profileDataHolder: ProfileDataHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileDataHolder = if (activity is ProfileDataHolder) {
            activity as ProfileDataHolder?
        } else {
            throw RuntimeException(
                    "Activity must implement ProfileDataHolder interface!"
            )
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsMainBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChart(binding.chartSOLO, "Solo/Duo")
        initChart(binding.chartFLEX, "Flex")

        if (profileDataHolder!!.getProfile() != null) {
            displayProfile()
            if(profileDataHolder!!.getLeagues() != null){
                displayLeagues()
            }
        }

    }

    private fun displayProfile() {
        val profile = profileDataHolder!!.getProfile()
        binding.sumLevel.text = getString(R.string.sumLevel, profile!!.summonerLevel)
        Glide.with(this)
                .load("https://ddragon.leagueoflegends.com/cdn/11.9.1/img/profileicon/" + profile.profileIconId + ".png")
                .transition(DrawableTransitionOptions().crossFade())
                .into(binding.sumIcon)
    }

    private fun displayLeagues() {
        val league = profileDataHolder!!.getLeagues()


        if(league!!.isNotEmpty()) {
            binding.leagueSOLOTEXT.text = getString(R.string.league, league[0].tier, league[0].rank, league[0].leaguePoints.toString()+"lp")
            binding.leagueSOLOWinLoss.text = getString(R.string.winloss, league[0].wins.toString(), league[0].losses.toString())

            val soloDrawable = getEmblem(league[0].tier!!)

            binding.leagueSOLO.setImageResource(soloDrawable)
            binding.chartSOLO.data = loadCharts(league[0])
            binding.chartSOLO.invalidate()


            if(league.size>1) {

                binding.leagueFLEXTEXT.text = getString(R.string.league, league[1].tier, league[1].rank, league[1].leaguePoints.toString()+"lp")
                binding.leagueFLEXWinLoss.text = getString(R.string.winloss, league[1].wins.toString(), league[1].losses.toString())

                val flexDrawable = getEmblem(league[1].tier!!)

                binding.leagueFLEX.setImageResource(flexDrawable)
                binding.chartFLEX.data = loadCharts(league[1])
                binding.chartFLEX.invalidate()
           }
        }else{
            binding.rankLayout.visibility = View.GONE
        }
    }

    private fun getEmblem(tier : String) : Int{
        when (tier) {
            "IRON" -> return R.drawable.emblem_iron
            "BRONZE" -> return R.drawable.emblem_bronze
            "SILVER" -> return R.drawable.emblem_silver
            "GOLD" -> return R.drawable.emblem_gold
            "PLATINUM" -> return R.drawable.emblem_platinum
            "DIAMOND" -> return R.drawable.emblem_diamond
            "MASTER" -> return R.drawable.emblem_master
            "GRANDMASTER" -> return R.drawable.emblem_grandmaster
            "CHALLENGER" -> return R.drawable.emblem_challenger
        }
        return 0
    }



    private fun loadCharts(league: League) : PieData {

        val entries: ArrayList<PieEntry> = ArrayList()

        entries.add(PieEntry(league.losses.toFloat(), "Losses"))
        entries.add(PieEntry(league.wins.toFloat(), "Wins"))

        val dataSet = PieDataSet(entries, "Win rate")
        dataSet.setColors(Color.rgb(255,68,68), Color.rgb(0,170,255))
        dataSet.valueTextSize = 10F
        dataSet.valueFormatter = MyValueFormatter()

        return PieData(dataSet)
    }

    private fun initChart(chart : PieChart, queue: String){
        chart.setTouchEnabled(false)
        chart.setBackgroundColor(Color.TRANSPARENT)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.setNoDataText("No $queue rank")
        chart.setNoDataTextColor(Color.GRAY)
        chart.isDrawHoleEnabled = false
    }

    inner class MyValueFormatter : ValueFormatter() {
        private val format = DecimalFormat("#")
        override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
            return format.format(pieEntry?.y)
        }
    }

}