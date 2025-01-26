package com.alpha.eventplanner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alpha.eventplanner.CalendarFragment
import com.alpha.eventplanner.HomeFragment
import com.alpha.eventplanner.MapFragment
import com.alpha.eventplanner.SettingsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // List of fragments to be displayed in ViewPager
    private val fragments = listOf(
        HomeFragment(),
        CalendarFragment(),
        MapFragment(),
        SettingsFragment()
    )

    override fun getItemCount(): Int {
        // Returns the number of fragments in the list
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        // Returns the fragment for the given position
        return fragments[position]
    }
}
