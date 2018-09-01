package com.planethackathon.worklifebalancelife.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.planethackathon.worklifebalancelife.R
import com.github.mikephil.charting.charts.LineChart
import com.planethackathon.worklifebalancelife.common.History
import java.util.Map


class StatisticFragment : Fragment() {
    var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_statistic, container, false)

            val chart = mContainer!!.findViewById(R.id.chart) as LineChart
            var historyList = mapOf("2018-08-27" to 32400,
                    "2018-08-28" to 43200,
                    "2018-08-29" to 43200,
                    "2018-08-30" to 32400);


            for (data in historyList) {

                // turn your data into Entry objects
                //entries.add(new Entry(data.getValueX(), data.getValueY()));
            }
        }

        return mContainer
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticFragment()
    }
}
