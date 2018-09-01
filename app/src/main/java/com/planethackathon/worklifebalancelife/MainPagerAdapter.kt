package com.planethackathon.worklifebalancelife

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.planethackathon.worklifebalancelife.fragment.LifeFragment
import com.planethackathon.worklifebalancelife.fragment.StatisticFragment
import com.planethackathon.worklifebalancelife.fragment.WorkFragment

/**
 * Created by minseok on 2018. 9. 1..
 * hackathon_sns.
 */
class MainPagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = when (position) {
        0 -> WorkFragment.newInstance()
        1 -> LifeFragment.newInstance()
        2 -> StatisticFragment.newInstance()
        else -> WorkFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Work"
            1 -> "Life"
            2 -> "통계"
            else -> ""
        }
    }

    override fun getCount() = 3
}