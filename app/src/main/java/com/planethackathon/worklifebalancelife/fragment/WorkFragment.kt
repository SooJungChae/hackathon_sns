package com.planethackathon.worklifebalancelife.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.planethackathon.worklifebalancelife.LoginActivity
import com.planethackathon.worklifebalancelife.R
import com.planethackathon.worklifebalancelife.TestActivity
import com.planethackathon.worklifebalancelife.common.FiftyTwoHoursApplication
import com.planethackathon.worklifebalancelife.common.GlobalUtils
import com.planethackathon.worklifebalancelife.common.History
import kotlinx.android.synthetic.main.fragment_work.*
import java.util.*

class WorkFragment : Fragment() {
    var mContainer: View? = null
    var countSecond: TimerTask? = null
    var timer: Timer? = null

    var elapsedTime = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_work, container, false)
            bindElement()
        }

        getDataFromDB()

        return mContainer
    }

    private fun getDataFromDB() {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val logsRef = db.collection("users").document("Y3YGpTFg0Sb0kQb0BHfP").collection("logs")

        //READ
        val dayQuery = logsRef.whereEqualTo("date", "2018-09-01")
        dayQuery.get()
                .addOnCompleteListener({ task ->
                    if (task.isSuccessful) {
                        val historyList = ArrayList<History>()
                        var milliseconds = 0L
                        for (document in task.result) {
                            val history = History(document.get("date")!!.toString(), document.get("startTime")!!.toString(), document.get("endTime")!!.toString(), document.get("tag")!!.toString(), document.get("interval") as Long?)
                            historyList.add(history)
                            milliseconds += history.interval
                        }

                        elapsedTime = milliseconds
                        val setting = FiftyTwoHoursApplication.getSettingManager()
                        setting.userElapsedTime = elapsedTime

                        val resultTime = GlobalUtils.millisToString(milliseconds)

                        txt_work_time.text = resultTime
                    } else {
                        Log.e("FAIL", "Error getting documents: ", task.exception)
                    }
                })
    }

    private fun startWorkRecord() {
        countSecond = object : TimerTask() {
            override fun run() {
                elapsedTime += 1000L
                val resultTime = GlobalUtils.millisToString(elapsedTime)
                activity?.runOnUiThread { txt_work_time.text = resultTime }
            }
        }

        timer?.schedule(countSecond, 0, 1000L)
    }

    private fun bindElement() {
        val btnTest = mContainer?.findViewById<Button>(R.id.btn_test)
        btnTest?.setOnClickListener {
            val intent = Intent(activity, TestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val btnWorkStart = mContainer?.findViewById<FloatingActionButton>(R.id.btn_work_start)
        btnWorkStart?.setOnClickListener {
            if (timer == null) {
                btnWorkStart.setImageDrawable(ContextCompat.getDrawable(this@WorkFragment.context!!, R.drawable.rectangle_6))
                Toast.makeText(activity, "기록 시작", Toast.LENGTH_SHORT).show()
                timer = Timer()

                startWorkRecord()
            } else {
                btnWorkStart.setImageDrawable(ContextCompat.getDrawable(this@WorkFragment.context!!, R.drawable.play_button))
                Toast.makeText(activity, "기록 중지", Toast.LENGTH_SHORT).show()

                val setting = FiftyTwoHoursApplication.getSettingManager()
                setting.userElapsedTime = elapsedTime

                timer?.cancel()
                timer = null
            }
        }

        val btnLogOut = mContainer?.findViewById<ImageView>(R.id.btn_logout)
        btnLogOut?.setOnClickListener {
            val setting = FiftyTwoHoursApplication.getSettingManager()
            setting.userId = ""

            Toast.makeText(activity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        val setting = FiftyTwoHoursApplication.getSettingManager()
        val txtWorkInfoDetail = mContainer?.findViewById<TextView>(R.id.txt_work_info_detail)
        txtWorkInfoDetail?.text = String.format(getString(R.string.txt_work_info), setting.userName, "")
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkFragment()
    }
}
