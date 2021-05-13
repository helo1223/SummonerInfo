package hu.bme.aut.android.summonerinfo.feature.summoner

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.summonerinfo.databinding.ActivitySummonerBinding
import hu.bme.aut.android.summonerinfo.feature.details.DetailsActivity
import hu.bme.aut.android.summonerinfo.feature.summoner.adapter.SummonerAdapter
import hu.bme.aut.android.summonerinfo.feature.summoner.fragment.AddSummonerDialogFragment


class SummonerActivity : AppCompatActivity(), SummonerAdapter.OnSummonerSelectedListener,
        AddSummonerDialogFragment.AddSummonerDialogListener {

    private lateinit var binding: ActivitySummonerBinding
    private lateinit var adapter: SummonerAdapter
    private var showDetailsIntent = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        super.onCreate(savedInstanceState)
        binding = ActivitySummonerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFab()
        initRecyclerView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            val correctName = data!!.extras!!.get("SummonerName").toString()
            val oldName = data.extras!!.get("oldName").toString()
            adapter.renameSummonerEntry(adapter.getSummonerPosition(oldName), correctName)
        } else {
            adapter.removeSummoner(adapter.getSummonerPosition(data!!.extras?.get("SummonerName").toString()))
        }
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            AddSummonerDialogFragment()
                    .show(supportFragmentManager, AddSummonerDialogFragment::class.java.simpleName)
        }
    }

    private fun initRecyclerView() {
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SummonerAdapter(this)
        adapter.addSummoner("Twisted Treeline")
        binding.mainRecyclerView.adapter = adapter
    }

    override fun onSummonerSelected(summoner: String?) {
        showDetailsIntent.setClass(this@SummonerActivity, DetailsActivity::class.java)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_SUMMONER_NAME, summoner)
        startActivityForResult(showDetailsIntent, 1)
    }

    override fun onSummonerAdded(summoner: String?) {
        if (!adapter.addSummoner(summoner!!)) {
            Toast.makeText(
                    this@SummonerActivity,
                    "Invalid Summoner Name!",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
}