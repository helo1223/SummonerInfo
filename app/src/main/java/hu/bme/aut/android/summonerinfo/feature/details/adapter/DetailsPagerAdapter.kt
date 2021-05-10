package hu.bme.aut.android.summonerinfo.feature.details.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.android.summonerinfo.feature.details.fragment.DetailsHistoryFragment
import hu.bme.aut.android.summonerinfo.feature.details.fragment.DetailsMainFragment

class DetailsPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    companion object{
        private const val NUM_PAGES: Int = 2
    }

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> DetailsMainFragment()
            1 -> DetailsHistoryFragment()
            else -> DetailsMainFragment()
        }
    }

}