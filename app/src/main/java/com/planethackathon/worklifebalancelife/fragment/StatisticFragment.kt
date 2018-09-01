package com.planethackathon.worklifebalancelife.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.planethackathon.worklifebalancelife.R

class StatisticFragment : Fragment() {
    var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_statistic, container, false)
        }

        return mContainer
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticFragment()
    }
}
