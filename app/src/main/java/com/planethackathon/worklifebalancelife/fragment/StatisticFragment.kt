package com.planethackathon.worklifebalancelife.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.planethackathon.worklifebalancelife.R
import com.planethackathon.worklifebalancelife.common.History
import java.text.SimpleDateFormat
import java.util.*


class StatisticFragment : Fragment() {
    var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val db = FirebaseFirestore.getInstance()

        val logsRef = db.collection("users").document("Y3YGpTFg0Sb0kQb0BHfP").collection("logs")

        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_statistic, container, false)

            val btnTest = mContainer!!.findViewById<Button>(R.id.btn_test)

            val workIntervalTextView = mContainer!!.findViewById<View>(R.id.workIntervalTextView) as TextView
            val lifeIntervalTextView = mContainer!!.findViewById<View>(R.id.lifeIntervalTextView) as TextView
            btnTest.performClick()
            btnTest?.setOnClickListener{
                val calendar = Calendar.getInstance()
                val weekStart = Calendar.getInstance()
                val weekEnd = Calendar.getInstance()
                weekStart.set(Calendar.DATE, calendar.firstDayOfWeek)

                weekEnd.time = weekStart.time
                weekEnd.add(Calendar.DATE, 6)

                if (weekEnd.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)) {
                    weekEnd.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE))
                    weekEnd.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH))
                    weekEnd.set(Calendar.YEAR, calendar.getActualMaximum(Calendar.YEAR))
                }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd")

                val dayQuery = logsRef.whereGreaterThanOrEqualTo("date", dateFormat.format(weekStart.time))
                        .whereLessThanOrEqualTo("date", dateFormat.format(weekEnd.time))
                        .whereEqualTo("tag", "work")


                dayQuery.get().addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        var result: Long = 0L
                        val historyList = ArrayList<History>()
                        for (document in task.result) {
                            val history = History(document.get("date")!!.toString(), document.get("startTime")!!.toString(), document.get("endTime")!!.toString(), document.get("tag")!!.toString(), document.get("interval") as Long?)
                            historyList.add(history)
                            result  += history.interval
                            Log.d("WEEK RESULT", history.toString())
                        }

                        Log.d("WEEK", result!!.toString())

                        activity!!.runOnUiThread(Runnable {
                            var workInterval: Long? = result
                            val hour = workInterval!! / 3600
                            workInterval -= hour * 3600
                            val min = workInterval / 60
                            workInterval -= min * 60

                            workIntervalTextView.setText(hour.toString() + "\"" + min + "\'" + workInterval + ".")
//                            workIntervalTextView.setText(GlobalUtils.millisToString(workInterval!!))

                            var lifeInterval: Long? = historyList.size.toLong() * 3600L * 24 - result
                            val hour2 = lifeInterval!! / 3600
                            lifeInterval -= hour2 * 3600
                            val min2 = lifeInterval / 60
                            lifeInterval -= min2 * 60

                            lifeIntervalTextView.setText(hour2.toString() + "\"" + min2 + "\'" + lifeInterval + ".")
//                            lifeIntervalTextView.setText(GlobalUtils.millisToString(lifeInterval!!))
                        })


                    } else {
                        Log.e("STATISTICS RESULT", "Error getting documents: ", task.exception)
                    }
                })
            }
        }

        return mContainer
    }

    companion object {
        @JvmStatic
        fun newInstance() = StatisticFragment()
    }
}
