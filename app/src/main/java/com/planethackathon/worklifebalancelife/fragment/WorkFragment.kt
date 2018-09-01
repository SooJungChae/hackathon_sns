package com.planethackathon.worklifebalancelife.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.planethackathon.worklifebalancelife.R
import com.planethackathon.worklifebalancelife.TestActivity
import android.widget.ImageView
import android.widget.Toast
import com.planethackathon.worklifebalancelife.LoginActivity
import com.planethackathon.worklifebalancelife.common.FiftyTwoHoursApplication

class WorkFragment : Fragment() {
    var mContainer: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_work, container, false)
            bindElement()
        }

        return mContainer
    }

    private fun bindElement() {
        val btnTest = mContainer?.findViewById<Button>(R.id.btn_test)
        btnTest?.setOnClickListener { val intent = Intent(activity, TestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent) }

        val btnWorkStart = mContainer?.findViewById<FloatingActionButton>(R.id.btn_work_start)
        btnWorkStart?.setOnClickListener {
            Toast.makeText(activity, "기록 시작", Toast.LENGTH_SHORT).show()
        }

        val btnLogOut = mContainer?.findViewById<ImageView>(R.id.btn_logout)
        btnLogOut?.setOnClickListener {
            val setting = FiftyTwoHoursApplication.getSettingManager()
            setting.userId = ""

            Toast.makeText(activity, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()

            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WorkFragment()
    }
}
